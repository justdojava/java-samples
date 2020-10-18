package com.just.dojava;

import com.google.common.collect.Lists;
import com.just.dojava.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/22
 */
@SpringBootApplication
public class Application {

    @Autowired
    List<PayService> payServices;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] names = ctx.getBeanNamesForType(PayService.class);
        List<PayService> anotherPayService = Lists.newArrayList();
        for (String beanName : names) {
            anotherPayService.add(ctx.getBean(beanName, PayService.class));
        }
        // 或者
        Map<String, PayService> beansOfType = ctx.getBeansOfType(PayService.class);
        for (Map.Entry<String, PayService> entry : beansOfType.entrySet()) {
            anotherPayService.add(entry.getValue());
        }

        Application application = ctx.getBean(Application.class);

        System.out.println(application.payServices);

    }
}
