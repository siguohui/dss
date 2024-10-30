<template>
  <div>
    状态：<div id="status"></div>
    <uploader ref="uploader"
              :options="options"
              :autoStart="true"
              @file-added="onFileAdded"
              @file-success="onFileSuccess"
              @file-removed="filesRemove"
              @file-progress="onFileProgress"
              @file-error="onFileError" class="uploader-example">
      <uploader-unsupport></uploader-unsupport>
      <uploader-drop>
        <uploader-btn :single="true" >选择文件</uploader-btn>
<!--        <p>Drop files here to upload or</p>
        <uploader-btn>select files</uploader-btn>
        <uploader-btn :attrs="attrs">select images</uploader-btn>
        <uploader-btn :directory="true">select folder</uploader-btn>-->
      </uploader-drop>
      <uploader-list v-show="panelShow">
<!--        <template #default="props">
          &lt;!&ndash; 原已上传的文件列表: 将原来已经上传的文件展示出来 &ndash;&gt;
          <div v-for="file in uploadFileList" :key="file.fileId" :file="file" class="file-item">
            <div class="file-progress success" style="width: 100%"></div>
            <SimpleUploaderIcon :file="file" />
            <div class="file-title van-ellipsis">{{ file.name }}</div>
            <div class="file-status">已上传</div>
            <van-button size="mini" type="danger" @click="onDelete(uploadFileList, file)">删除文件</van-button>
          </div>
          &lt;!&ndash; 新上传的文件列表: 本次通过组件上传的操作区域 &ndash;&gt;
          <uploader-file v-for="file in props.fileList" :key="file.fileId" :file="file" :list="true">
            <template #default="fileProps">
              <div class="file-item">
                <div class="file-progress" :class="{ success: fileProps.status === 'success', error: fileProps.status === 'error' }" :style="{ width: fileProps.progressStyle.progress }"></div>
                <SimpleUploaderIcon :file="fileProps" />
                <div class="file-title van-ellipsis">{{ file.name }}</div>
                <div v-show="fileProps.status === 'success'" class="file-status">上传成功</div>
                <div v-show="fileProps.status === 'error'" class="file-status">上传失败</div>
                <div v-show="fileProps.status === 'uploading'" class="file-status">正在上传({{ fileProps.progressStyle.progress }})</div>
                <van-button v-show="fileProps.status === 'success'" type="danger" @click="onDelete(props.fileList, file)">删除文件</van-button>
                <van-button v-show="fileProps.status === 'error'" type="warning" @click="onCancel(file)">移除文件</van-button>
                <van-button v-show="fileProps.status === 'uploading'" type="danger" @click="onCancel">取消上传</van-button>
              </div>
            </template>
          </uploader-file>
        </template>-->
      </uploader-list>
    </uploader>
  </div>
</template>

<script>
import SparkMD5 from 'spark-md5';
import axios from 'axios';
import $ from 'jquery'

export default {
  name: 'Upload',
  data() {
    return {
      options: {
        target: 'http://127.0.0.1:8081/file/upload',
        chunkSize: 5 * 1024 * 1000,
        fileParameterName: 'file',
        maxChunkRetries: 2,
        testChunks: true,   //是否开启服务器分片校验
        checkChunkUploadedByResponse: function (chunk, message) {
          // 服务器分片校验函数，秒传及断点续传基础
          let objMessage = JSON.parse(message);
          if (objMessage.skipUpload) {
            return true;
          }
          return (objMessage.uploaded || []).indexOf(chunk.offset + 1) >= 0
        },
        headers: {
          Authorization: ''
        },
        query() {
        }
      },
      panelShow: false
    }
  },
  computed: {
    //Uploader实例
    uploader() {
      return this.$refs.uploader.uploader;
    }
  },
  methods: {
    onFileAdded(file) {
      this.panelShow = true
      this.computeMD5(file);
    },
    filesRemove(file) {

    },
    onFileProgress(rootFile, file, chunk) {
      console.log("... onFileProgress")
    },
    onFileSuccess(rootFile, file, response, chunk) {
      let res = JSON.parse(response);
      // 如果服务端返回需要合并
      if (res.needMerge) {
        // 文件状态设为“合并中”
        this.statusSet(file.id, 'merging');
        let param = {
          'filename': rootFile.name,
          'identifier': rootFile.uniqueIdentifier,
          'totalSize': rootFile.size
        }
        axios({
          method: 'post',
          url: "http://127.0.0.1:8081/file/merge",
          data: param
        }).then(res => {
          this.statusRemove(file.id);
        }).catch(e => {
          console.log("合并异常,重新发起请求,文件名为:", file.name)
          file.retry();
        });
      }
    },
    onFileError(rootFile, file, response, chunk) {
      console.log("... onFileError")
    },
    computeMD5(file) {
      let fileReader = new FileReader();
      let time = new Date().getTime();
      let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
      let currentChunk = 0;
      const chunkSize = 10 * 1024 * 1000;
      let chunks = Math.ceil(file.size / chunkSize);
      let spark = new SparkMD5.ArrayBuffer();

      // 文件状态设为"计算MD5"
      this.statusSet(file.id, 'md5');
      file.pause();

      loadNext();

      fileReader.onload = (e => {

        spark.append(e.target.result);

        if (currentChunk < chunks) {
          currentChunk++;
          loadNext();

          // 实时展示MD5的计算进度
          this.$nextTick(() => {
            $(`.myStatus_${file.id}`).text('校验MD5 ' + ((currentChunk / chunks) * 100).toFixed(0) + '%')
          })
        } else {
          let md5 = spark.end();
          this.computeMD5Success(md5, file);
          console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`);
        }
      });

      fileReader.onerror = function () {
        this.error(`文件${file.name}读取出错，请检查该文件`)
        file.cancel();
      };

      function loadNext() {
        let start = currentChunk * chunkSize;
        let end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;

        fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end));
      }
    },
    statusSet(id, status) {
      let statusMap = {
        md5: {
          text: '校验MD5',
          bgc: '#fff'
        },
        merging: {
          text: '合并中',
          bgc: '#e2eeff'
        },
        transcoding: {
          text: '转码中',
          bgc: '#e2eeff'
        },
        failed: {
          text: '上传失败',
          bgc: '#e2eeff'
        }
      }

      console.log(".....", status, "...:", statusMap[status].text)
      this.$nextTick(() => {
        // $(`<p class="myStatus_${id}"></p>`).appendTo(`.file_${id} .uploader-file-status`).css({
        $(`<p class="myStatus_${id}"></p>`).appendTo(`#status`).css({
          'position': 'absolute',
          'top': '0',
          'left': '0',
          'right': '0',
          'bottom': '0',
          'zIndex': '1',
          'line-height': 'initial',
          'backgroundColor': statusMap[status].bgc
        }).text(statusMap[status].text);
      })
    },
    computeMD5Success(md5, file) {
      Object.assign(this.uploader.opts, {
        query: {
          ...this.params,
        }
      })

      file.uniqueIdentifier = md5;
      file.resume();
      this.statusRemove(file.id);
    },
    statusRemove(id) {
      this.$nextTick(() => {
        $(`.myStatus_${id}`).remove();
      })
    },
  }
}
</script>

<style scoped>
.uploader-example {
  width: 880px;
  padding: 15px;
  margin: 40px auto 0;
  font-size: 12px;
  box-shadow: 0 0 10px rgba(0, 0, 0, .4);
}
.uploader-example .uploader-btn {
  margin-right: 4px;
}
.uploader-example .uploader-list {
  max-height: 440px;
  overflow: auto;
  overflow-x: hidden;
  overflow-y: auto;
}
</style>
