package com.xiaosi.wx.easyexcel.demo1;

/***
 *
 * 获取动态值
 *
 * **/
public class RoleNameExplicitConstraint implements ExplicitInterface {

    @Override
    public String[] source(String type) {
//        QueryWrapper<DataDictionary> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("type", type);
//        queryWrapper.orderByAsc("sort_num");
//        List<DataDictionary> list= dataDictionaryMapper.selectList(queryWrapper);
       /* List<String> names = list.stream().map(dataDictionary->{
                    return dataDictionary.getName()+"@"+dataDictionary.getCode();
                }
        ).collect(Collectors.toList());*/
        String[] str={"1","2","3"};
        return str;
    }
}
