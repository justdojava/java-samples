package com.spring.session;

import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/3
 */
@Controller
public class HelloController {

    @RequestMapping("/setValue")
    public String setValue(@RequestParam(name = "key", required = false) String key,
                           @RequestParam(name = "value", required = false) String value, HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            request.getSession().setAttribute(key, value);
        }
        return "home";
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
