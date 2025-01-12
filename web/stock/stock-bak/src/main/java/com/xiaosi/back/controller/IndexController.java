package com.xiaosi.back.controller;


import com.xiaosi.back.entity.FileInfo;
import com.xiaosi.back.entity.Result;
import com.xiaosi.back.entity.Stock;
import com.xiaosi.back.entity.User;
import com.xiaosi.back.mapper.FileInfoMapper;
import com.xiaosi.back.mapper.StockMapper;
import com.xiaosi.back.repository.FileInfoRepository;
import com.xiaosi.back.repository.UserRepository;
import com.xiaosi.back.service.StockService;
import com.xiaosi.back.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final StockMapper stockMapper;
    private final StockService stockService;
    private final FileInfoRepository fileInfoRepository;
    private final FileInfoMapper fileInfoMapper;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/index")
    public String index(){
        List<Stock> userList = new ArrayList<>();
        int batchSize = 50; // 每批次插入的数据量
        for(int i = 0; i < 99; i++){
            Stock user = new Stock();
            user.setName("张三");
            userList.add(user);
            // 达到批次大小时进行插入
            if(userList.size() == batchSize){
                stockService.saveBatch(userList);
                userList.clear(); // 清空列表，准备下一批数据
            }
        }
        // 插入剩余数据
        if(!userList.isEmpty()){
            stockService.saveBatch(userList);
        }
        return "index";
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        System.out.println(user);
        return Result.build(true, 200, "登录成功", Map.of("token","token"));
    }

    @SneakyThrows
    @PostMapping("/upload")
    public Result<FileInfo> upload(@RequestPart MultipartFile image){
        FileInfo file = FileUtils.uploadImage(image);
        fileInfoRepository.save(file);
        return Result.success(file);
    }

    @GetMapping("/image/list")
    public Result<List<FileInfo>> getList(){
        List<FileInfo> list = fileInfoRepository.findAll();
//        List<FileInfo> list = fileInfoMapper.selectList(null);
        return Result.success(list);
    }

    @DeleteMapping("/image/del/{id}")
    public Result del(@PathVariable("id") Long id){
        fileInfoRepository.deleteById(id);
        return Result.success(null);
    }

    @GetMapping("/add")
    public String addUser(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        userRepository.save(user);
        return "success";
    }

    @GetMapping("/del")
    public String delUser(){
        userRepository.logicDelete(1L);
        return "success";
    }
}
