package com.xiaosi.back.controller;

import com.xiaosi.back.commons.reps.AjaxResult;
import com.xiaosi.back.commons.CommonConstant;
import com.xiaosi.back.entity.BackChunk;
import com.xiaosi.back.entity.BackFileList;
import com.xiaosi.back.commons.vo.CheckChunkVO;
import com.xiaosi.back.service.IBackFileService;
import com.xiaosi.back.utils.ApiResult;
import com.xiaosi.back.utils.AppException;
import com.xiaosi.back.utils.MinioUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IBackFileService backFileService;

    @GetMapping("/test")
    public String test() {
        return "Hello Wolfe.";
    }


    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public AjaxResult postFileUpload(@ModelAttribute BackChunk chunk, HttpServletResponse response) {
        int i = backFileService.postFileUpload(chunk, response);
        return AjaxResult.success(i);
    }

    /**
     * 检查文件上传状态
     */
    @GetMapping("/upload")
    public CheckChunkVO getFileUpload(@ModelAttribute BackChunk chunk, HttpServletResponse response) {
        //查询根据md5查询文件是否存在
        CheckChunkVO fileUpload = backFileService.getFileUpload(chunk, response);
        return fileUpload;
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        return AjaxResult.success(backFileService.deleteBackFileByIds(id));
    }

    /**
     * 检查文件上传状态
     */
    @PostMapping("/merge")
    public AjaxResult merge(@RequestBody BackFileList backFileList) {
        int i = backFileService.mergeFile(backFileList);
        if (i == CommonConstant.UPDATE_EXISTS.intValue()) {
            //应对合并时断线导致的无法重新申请合并的问题
            return new AjaxResult(200, "已合并,无需再次提交");
        }
        return AjaxResult.success(i);
    }

    @Resource
    private MinioUtils minioUtils;

    @PostMapping("/upload1")
    public ApiResult upload(MultipartFile file) throws AppException {
        return minioUtils.uploadFile(file);
    }

    @GetMapping("/getPreviewUrl")
    public ApiResult getPreviewUrl(String fileName) throws AppException {
        return minioUtils.getPreviewUrl(fileName);
    }
}
