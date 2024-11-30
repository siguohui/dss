package com.xiaosi.rd.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.common.collect.Lists;
import com.xiaosi.rd.entity.Stu;
import com.xiaosi.rd.entity.User;
import com.xiaosi.rd.mapper.StuMapper;
import com.xiaosi.rd.mapper.UserMapper;
import com.xiaosi.rd.repository.StuRepository;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class IndexController {

    @Resource
    private DataSource dataSource;
    @Resource
    private UserMapper userMapper;

    @Resource
    private StuRepository stuRepository;

    @Resource
    private StuMapper stuMapper;

    @GetMapping("/index")
    public List<Stu> index() {

       return new LambdaQueryChainWrapper<>(stuMapper)
               .in(Stu::getAge, Lists.newArrayList(23,25))
               .in(Stu::getName,Lists.newArrayList("张三","李四"))
               .list();


//        return stuRepository.findAll();
    }


    public static void main(String[] args) {
//        CollectionUtil.unionAll();
//        String s = "20.12.2";
//        String pattern = "^(\\d+(\\.\\d+)?)$";
//
//        String p = "^([-+])?\\d+(\\.\\d+)?$";
//        boolean flag = s.matches(p);
//        System.out.println(flag);
//
//
//        BigDecimal res = new BigDecimal("13");
//        BigDecimal up = new BigDecimal("10");
//        BigDecimal low = new BigDecimal("1");
//
//
//        int i = res.compareTo(up);
//        System.out.println(i);
//        if (Objects.nonNull(up)) {
//            System.out.println(i <= 0);
//        }
//
//        int i1 = res.compareTo(low);
//        System.out.println(i1);
//
//        if (Objects.nonNull(low)) {
//            System.out.println(i1 >= 0);
//        }

        List<String> str = Lists.newArrayList();
        str.add("张三");
        str.add("李四");
        str.add("王五");
        str.add("李四");
        str.add("张三");

        List<String> list = str.stream().distinct().collect(Collectors.toList());

        System.out.println(list);

        String myObject = new String("Hello");
        SoftReference<String> soft = new SoftReference<>(myObject);
        myObject = null;
        String s = soft.get();
        System.out.println(s);


        String s2 = soft.get();
        System.out.println(s2);


        String myObject1 = new String("Hello");
        WeakReference<String> weak = new WeakReference<>(myObject1);
        myObject1 = null;
        String s3 = weak.get();
        System.out.println(s3);
    }

    @Test
    public void weakTest(){
        A a = new A();
        a.setStr("Hello, reference");
        WeakReference<A> weak = new WeakReference<A>(a);
        a = null;
        int i = 0;
        while (weak.get() != null) {
            System.out.println(String.format("Get str from object of WeakReference: %s, count: %d", weak.get().getStr(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("object a was cleared by JVM!");
    }

    @Test
    public void softTest(){
        A a = new A();
        a.setStr("Hello, reference");
        SoftReference<A> sr = new SoftReference<A>(a);
        a = null;
        int i = 0;
        while (sr.get() != null) {
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", sr.get().getStr(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }
        System.out.println("object a was cleared by JVM!");
    }

}

@Data
class A{

    private String str;

}
