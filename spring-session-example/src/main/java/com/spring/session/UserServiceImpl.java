package com.spring.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/8/4
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 简单起见，直接内部校验
        String uname = "admin";
        String passwd = "1234qwer";

        // 如果是正式项目，我们需要从数据库数据数据，然后再校验，形式如下：
        // User user = userDAO.query(username);

        if (!username.equals(uname)) {
            throw new UsernameNotFoundException(username);
        }
        // 封装成 Spring security 定义的 User 对象
        return User.builder()
                .username(username)
                .passwordEncoder(s -> passwordEncoder.encode(passwd))
                .authorities(new SimpleGrantedAuthority("user"))
                .build();
    }
}
