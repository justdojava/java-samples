package com.just.done;

import com.just.done.domain.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/11/19 14:07
 * @since
 */
@RestController
public class AppController {


    @RequestMapping("query-user")
    public User queryUser() {
        User user = new User();
        user.setId("1");
        user.setName("test");
        return user;
    }
}
