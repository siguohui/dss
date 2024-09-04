package com.xiaosi.doc.controller;

import com.xiaosi.doc.entity.Stu;
import com.xiaosi.doc.service.StuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sgh
 * @since 2024-06-25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class StuController {

    private final StuService stuService;

    @GetMapping("/index")
    public String index(){
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("北京市大兴区兴政街15号", new Point(116.34159, 39.72684)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("北京市大兴区德茂街", new Point(116.43919, 39.777976)));
        Distance distance = redisTemplate.opsForGeo().distance("geo", "北京市大兴区兴政街15号", "北京市大兴区德茂街");
//        Distance distance2 = redisTemplate.opsForGeo().distance("geo", "五丁小学", "那屋网客", RedisGeoCommands.DistanceUnit.KILOMETERS);
//         stuService.testTransactional();

        // 913.8254 m
        System.out.println(distance.getValue() + " " + distance.getUnit());
         return "success";
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/test")
    public String test() {
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("九里堤农贸", new Point(104.064726, 30.699334)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("西北桥", new Point(104.064007, 30.692037)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("城北派出所", new Point(104.073386, 30.702136)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("人民北路", new Point(104.078345, 30.692168)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("哈啰酒店-成都西南交大店", new Point(104.052828, 30.699939)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("矮冬瓜红茶火锅", new Point(104.052936, 30.698899)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("沈跃全中医", new Point(104.052199, 30.698231)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("五丁小学", new Point(104.067169, 30.695763)));
        redisTemplate.opsForGeo().add("geo", new RedisGeoCommands.GeoLocation<>("那屋网客", new Point(104.061456, 30.700576)));


        Distance distance = redisTemplate.opsForGeo().distance("geo", "九里堤", "北站西");
        Distance distance2 = redisTemplate.opsForGeo().distance("geo", "九里堤", "北站西", RedisGeoCommands.DistanceUnit.KILOMETERS);
// 913.8254 m
        System.out.println(distance.getValue() + " " + distance.getUnit());
// 0.9138 km
        System.out.println(distance2.getValue() + " " + distance2.getUnit());
        return "1";
    }



}
