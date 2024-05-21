package com.xiaosi.gongzhonghao.baseservice.kcaco.daily.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Description: 日常推送体
 *
 * @author kcaco
 * @since 2022-08-20 16:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DailyPush {

    private String date;
    private String city;
    private String weather;
    private String windScale;
    private String tempMin;
    private String tempMax;
    private String sport;
    private String dress;
    private String dressColor;
    private String sunscreen;
    private String sunscreenColor;
    private String loveDay;
    private String girlBirthday;
    private String boyBirthday;
    private String oneWord;
    private String rainbowFart;

}
