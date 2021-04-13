package com.just.done;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/11/19 13:58
 * @since
 */
@ControllerAdvice
public class CustomResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        System.out.println("In supports() method of " + getClass().getSimpleName());
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("In beforeBodyWrite() method of " + getClass().getSimpleName());
        Map<String, String> map = Maps.newHashMap();
        map.put("body", JSON.toJSONString(o));
        map.put("retCode", "000000");
        map.put("retMsg", "成功");
        return map;
    }
}
