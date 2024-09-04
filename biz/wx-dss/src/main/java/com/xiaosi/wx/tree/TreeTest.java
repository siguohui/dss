package com.xiaosi.wx.tree;

import com.xiaosi.wx.utils.JSONUtil;
import com.xiaosi.wx.utils.TreeUtil;

import java.util.Arrays;
import java.util.List;

public class TreeTest {

    public static void main(String[] args) {
        MenuVo menu0 = new MenuVo(0L, -1L);
        MenuVo menu1 = new MenuVo(1L, 0L);
        MenuVo menu2 = new MenuVo(2L, 0L);
        MenuVo menu3 = new MenuVo(3L, 1L);
        MenuVo menu4 = new MenuVo(4L, 1L);
        MenuVo menu5 = new MenuVo(5L, 2L);
        MenuVo menu6 = new MenuVo(6L, 2L);
        MenuVo menu7 = new MenuVo(7L, 3L);
        MenuVo menu8 = new MenuVo(8L, 3L);
        MenuVo menu9 = new MenuVo(9L, 4L);
//基本数据
        List<MenuVo> menuList = Arrays.asList(menu0,menu1, menu2,menu3,menu4,menu5,menu6,menu7,menu8,menu9);
//合成树
        List<MenuVo> tree= TreeUtil.makeTree(menuList, x->x.getPId()==-1L,(x, y)->x.getId().equals(y.getPId()), MenuVo::setSubMenus);
        System.out.println(JSONUtil.listToJSONArr(tree));
    }
}
