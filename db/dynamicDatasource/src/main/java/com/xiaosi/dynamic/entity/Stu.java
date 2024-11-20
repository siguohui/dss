package com.xiaosi.dynamic.entity;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class Stu {

    private Long id;

    private Integer age;

    private String stuName;


    public static void main(String[] args) {
        List<Stu> list = Lists.newArrayList();
        list.add(Stu.builder().id(1L).age(1).stuName("1").build());
        list.add(Stu.builder().id(3L).age(2).stuName("2").build());
        list.add(Stu.builder().id(3L).age(2).stuName("3").build());
        list.add(Stu.builder().id(3L).age(4).stuName("4").build());
        list.add(Stu.builder().id(5L).age(6).stuName("5").build());
        list.add(Stu.builder().id(6L).age(6).stuName("6").build());
        Map<Integer, String> collect = list.stream().collect(Collectors.toMap(Stu::getAge, Stu::getStuName));
        System.out.println(collect);
    }
}
