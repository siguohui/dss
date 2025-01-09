package com.xiaosi.design.observe;

/**
 * JDK 中的应用：
 *
 * java.util.Observer 和 java.util.Observable
 * javax.swing.event.ChangeListener
 * Spring 中的应用：
 *
 * ApplicationEvent 和 ApplicationListener 是典型实现。
 */
public interface Observer {

    void update(String message);
}
