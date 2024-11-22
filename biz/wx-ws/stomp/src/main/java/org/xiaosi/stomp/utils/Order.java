package org.xiaosi.stomp.utils;

import cn.hutool.core.util.BooleanUtil;
import lombok.Data;

@Data
public class Order {

    private Boolean flag;

    public static void main(String[] args) {
        Order order = new Order();
        order.setFlag(Boolean.TRUE);
        if(BooleanUtil.isTrue(order.flag)) {
            System.out.println("1");
        }
    }
}
