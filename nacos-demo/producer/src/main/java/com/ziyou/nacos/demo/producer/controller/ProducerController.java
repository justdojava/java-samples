package com.ziyou.nacos.demo.producer.controller;

import com.ziyou.nacos.demo.producer.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author ziyou<br>
 * <b>Date：</b>2021-04-11 19:59<br>
 * <b>Desc：</b>无<br>
 */
@RestController
@RequestMapping(value = "producer")
public class ProducerController {

    private UserConfig userConfig;

    @GetMapping("/getUsername")
    private String getUsername() {
        String result = userConfig.getUsername() + "-" + userConfig.getPassword();
        System.out.println(result);
        return result;
    }

    @Autowired
    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }
}
