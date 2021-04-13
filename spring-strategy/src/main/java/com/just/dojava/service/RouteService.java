package com.just.dojava.service;

import com.just.dojava.service.domain.PayRequest;
import com.just.dojava.service.domain.PayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/22
 */
@Service
public class RouteService {

    @Autowired
    Set<PayService> payServiceSet;

    Map<String, PayService> payServiceMap;

    public PayResult epay(PayRequest payRequest) {
        PayService payService = payServiceMap.get(payRequest.getChannelNo());
        return  payService.epay(payRequest);
    }

    @PostConstruct
    public void init() {
        for (PayService payService : payServiceSet) {
            payServiceMap = new HashMap<>();
            payServiceMap.put(payService.channel(), payService);
        }
    }
}
