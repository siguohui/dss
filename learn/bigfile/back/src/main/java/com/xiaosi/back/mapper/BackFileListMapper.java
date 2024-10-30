package com.xiaosi.back.mapper;

import com.xiaosi.back.entity.BackFileList;

import java.util.List;

/**
 * 已上传文件列表Mapper接口
 */
public interface BackFileListMapper {
    /**
     * 查询已上传文件列表
     *
     * @param id 已上传文件列表ID
     * @return 已上传文件列表
     */
    public BackFileList selectBackFileListById(Long id);

    /**
     * 功能描述: 查询单条已上传文件记录
     *
     * @param: BackFileList 已上传文件列表
     */
    Integer selectSingleBackFileList(BackFileList BackFileList);

    /**
     * 查询已上传文件列表列表
     *
     * @param BackFileList 已上传文件列表
     * @return 已上传文件列表集合
     */
    public List<BackFileList> selectBackFileListList(BackFileList BackFileList);

    /**
     * 新增已上传文件列表
     *
     * @param BackFileList 已上传文件列表
     * @return 结果
     */
    public int insertBackFileList(BackFileList BackFileList);

    /**
     * 修改已上传文件列表
     *
     * @param BackFileList 已上传文件列表
     * @return 结果
     */
    public int updateBackFileList(BackFileList BackFileList);

    /**
     * 删除已上传文件列表
     *
     * @param id 已上传文件列表ID
     * @return 结果
     */
    public int deleteBackFileListById(Long id);

    /**
     * 批量删除已上传文件列表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBackFileListByIds(Long[] ids);
}
