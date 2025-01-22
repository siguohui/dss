package com.xiaosi.back.controller;


import com.google.common.collect.Lists;
import com.xiaosi.back.entity.*;
import com.xiaosi.back.mapper.FileInfoMapper;
import com.xiaosi.back.mapper.StockMapper;
import com.xiaosi.back.repository.FileInfoRepository;
import com.xiaosi.back.repository.UserRepository;
import com.xiaosi.back.service.BankAccountService;
import com.xiaosi.back.service.StockService;
import com.xiaosi.back.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final StockMapper stockMapper;
    private final StockService stockService;
    private final FileInfoRepository fileInfoRepository;
    private final FileInfoMapper fileInfoMapper;
    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;


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
//        fileInfoRepository.save(file);
        fileInfoMapper.insert(file);
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

    public static void main(String[] args) {
        List<User> list = Lists.newArrayList();
        list.add(User.of("张三","123456"));
        list.add(User.of("李四","123456"));
        list.add(User.of("张三","123456"));
        list.add(User.of("王五","123456"));
        list.add(User.of("刘六","123456"));
        list.add(User.of("李四","123456"));

        HashMap<String, String> collect1 = list.stream().collect(HashMap::new, (m, v) -> m.put(v.getUsername(), v.getPassword()), HashMap::putAll);

        System.out.println(collect1);

        Map<String, List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getUsername));
    }

    @PutMapping("/{id}/deposit")
    public BankAccount deposit(@PathVariable Long id, @RequestParam BigDecimal amount) throws InterruptedException {
        return bankAccountService.deposit(id, amount);
    }

    @PutMapping("/{id}/deposit1")
    public BankAccount deposit1(@PathVariable Long id, @RequestParam BigDecimal amount) {
        return bankAccountService.deposit1(id, amount);
    }
}
