package com.xiaosi.wx.test;

import com.xiaosi.wx.test.annotation.SingletonComponent;

/**
 * 单例，被扫描到并被容器实例化
 */
@SingletonComponent("simpleService")
public class SimpleSingletonService {
}
