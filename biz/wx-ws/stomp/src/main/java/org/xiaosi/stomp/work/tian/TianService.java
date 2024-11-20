package org.xiaosi.stomp.work.tian;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xiaosi.stomp.work.qweather.okhttp.GeneralOkhttpService;
import org.xiaosi.stomp.work.tian.config.TinProperties;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 13:26
 */
@Service
@RequiredArgsConstructor
public class TianService {

    private final TinProperties tinProperties;

    private final GeneralOkhttpService generalOkhttpService;

    /**
     * 彩虹屁
     */
    public TianRes getRainbowFart() {
        return generalOkhttpService.generalGetMethod(TianApiConstant.RAINBOW_FART + tinProperties.getKey(),
                TianRes.class,
                "获取彩虹屁失败!");
    }

    /**
     * 舔狗
     */
    public TianRes getLickingDog() {
        return generalOkhttpService.generalGetMethod(TianApiConstant.LICKING_DOG + tinProperties.getKey(),
                TianRes.class,
                "获取彩虹屁失败!");
    }

}
