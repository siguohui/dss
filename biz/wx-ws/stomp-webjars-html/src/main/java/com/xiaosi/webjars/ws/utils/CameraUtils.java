package com.xiaosi.webjars.ws.utils;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 相机测试
 */
public class CameraUtils {

    public static void main(String[] args) {


        CameraUtils.openCamera();
    }

    public static final Logger logger = LoggerFactory.getLogger(CameraUtils.class);

    public static void openCamera() {


        //默认获取一个Webcam对象
        final Webcam webcam = Webcam.getDefault();
        //设置窗口大小
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        //获取画板
        WebcamPanel panel = new WebcamPanel(webcam);
        //设置FPS显示
        panel.setFPSDisplayed(true);
        //设置debug日志
        panel.setDisplayDebugInfo(true);
        //设置显示图片尺寸
        panel.setImageSizeDisplayed(true);
        //设置镜像
        panel.setMirrored(true);

        //获取框架jdk
        JFrame window = new JFrame("照相机拍照");

        //框架中加入画板
        window.add(panel);
        //设置此帧是否可由用户调整大小
        window.setResizable(true);
        //调用任意已注册 WindowListener 的对象后自动隐藏并释放该窗体
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //自适应窗口
        window.pack();
        //设置窗口可见
        window.setVisible(true);
        //设置监听事件
        window.addWindowListener(new WindowListener() {



            @Override
            public void windowOpened(WindowEvent e) {



            }

            @Override
            public void windowClosing(WindowEvent e) {



            }

            @Override
            public void windowClosed(WindowEvent e) {


                //关闭摄像头
                webcam.close();
            }

            @Override
            public void windowIconified(WindowEvent e) {



            }

            @Override
            public void windowDeiconified(WindowEvent e) {



            }

            @Override
            public void windowActivated(WindowEvent e) {



            }

            @Override
            public void windowDeactivated(WindowEvent e) {



            }


        });

        final JButton button = new JButton("拍照");
        window.add(panel, BorderLayout.CENTER);
        window.add(button, BorderLayout.SOUTH);
        window.setResizable(true);
        window.pack();
        window.setVisible(true);
        button.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e)
            {



                button.setEnabled(false);  //设置按钮不可点击

                //实现拍照保存-------start
                String fileName = "D://" + System.currentTimeMillis();       //保存路径即图片名称（不用加后缀）
                WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
                SwingUtilities.invokeLater(new Runnable() {



                    @Override
                    public void run()
                    {


                        JOptionPane.showMessageDialog(null, "拍照成功");
                        button.setEnabled(true);    //设置按钮可点击

                        return;
                    }
                });
                //实现拍照保存-------end

            }
        });
    }
}
