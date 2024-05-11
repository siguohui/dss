package com.xiaosi.wx.utils.tree;
import java.util.ArrayList;
import java.util.List;

/**
 * *解析树形数据工具类
 */
public class TreeParser {
    /**
     * * 解析树形数据
     *
     * @param topId
     * @param entityList
     * @return
     */
    public static <E extends TreeEntity<E>> List<E> getTreeList(String topId, List<E> entityList) {
        List<E> resultList = new ArrayList<>();
        // 获取顶层元素集合
        String parentId;
        for (E entity : entityList) {
            parentId = entity.getParentId();
            if (parentId == null || topId.equals(parentId)) {
                resultList.add(entity);
            }
        }
        // 获取每个顶层元素的子数据集合
        for (E entity : resultList) {
//            entity.setChildList(getSubList(entity.getId(), entityList));
            entity.setChildList(getSubList(entity, entityList));
        }
        return resultList;
    }


    /**
     * 获取子数据集合
     *
     * @param e
     * @param entityList
     * @param <E>
     * @return
     */
    private static <E extends TreeEntity<E>> List<E> getSubList(E e, List<E> entityList) {
        List<E> childList = new ArrayList<>();
        String parentId;
        // 子集的直接子对象
        for (E entity : entityList) {
            parentId = entity.getParentId();
            if (e.getId().equals(parentId)) {
                childList.add(entity);
            }
        }
        // 子集的间接子对象
        for (E entity : childList) {
            entity.setChildList(getSubList(entity, entityList));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
