package com.ziyou.nacos.demo.consumer.controller;

import com.ziyou.nacos.demo.consumer.rpc.IProducerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author Silence<br>
 * <b>Date：</b>2021-04-11 19:59<br>
 * <b>Desc：</b>无<br>
 */
@RestController
@RequestMapping(value = "consumer")
public class TestNacosController {

    private IProducerFeign iProducerFeign;

    @GetMapping("/testNacos")
    private String testNacos() {
        return iProducerFeign.getUsername();
    }

    @Autowired
    public void setiProducerFeign(IProducerFeign iProducerFeign) {
        this.iProducerFeign = iProducerFeign;
    }
}
