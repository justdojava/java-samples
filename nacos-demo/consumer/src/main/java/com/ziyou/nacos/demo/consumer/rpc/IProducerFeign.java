package com.ziyou.nacos.demo.consumer.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author Silence<br>
 * <b>Date：</b>2021-04-11 20:01<br>
 * <b>Desc：</b>无<br>
 */
@FeignClient(value = "producer")
@Component
public interface IProducerFeign {
    /**
     * 获取生产者名称接口
     *
     * @return
     */
    @GetMapping("/producer/getUsername")
    String getUsername();

}
