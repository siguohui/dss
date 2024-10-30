package com.xiaosi.back.service;


import com.xiaosi.back.commons.reps.AjaxResult;
import com.xiaosi.back.commons.vo.CheckChunkVO;
import com.xiaosi.back.entity.BackChunk;
import com.xiaosi.back.entity.BackFileList;
import jakarta.servlet.http.HttpServletResponse;

public interface IBackFileService {
    int postFileUpload(BackChunk chunk, HttpServletResponse response);
    CheckChunkVO getFileUpload(BackChunk chunk, HttpServletResponse response);
    int deleteBackFileByIds(Long id);
    int mergeFile(BackFileList fileInfo);
}
