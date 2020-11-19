package com.just.done;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/11/19 15:24
 * @since
 */
@Component
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("CustomFilter Execute cost=" + (System.currentTimeMillis() - start));
    }
}
