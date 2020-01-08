package com.lizhi.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: lizhi
 * @Date: 2020/1/8 16:05
 * @Description: 生成图形验证码
 */
@RestController
public class CaptchaController {
    @Autowired
    private Producer captchaproducer;

    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置内容类型
        response.setContentType("image/jpeg");
        // 创建验证码文本
        String capText = captchaproducer.createText();

        request.getSession().setAttribute("captcha", capText);

//        创建验证码图片
        BufferedImage bi = captchaproducer.createImage(capText);

//        获取响应输出流
        ServletOutputStream out = response.getOutputStream();
//      将图片验证码数据写到响应输出刘
        ImageIO.write(bi, "jpg", out);
        // 推送并关闭响应输出流
        try {
            out.flush();
        }finally {
            out.close();
        }


    }
}
