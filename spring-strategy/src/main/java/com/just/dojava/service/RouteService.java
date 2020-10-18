package com.just.dojava.service;

import com.just.dojava.service.domain.PayRequest;
import com.just.dojava.service.domain.PayResult;
import com.just.dojava.service.enums.ChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Autowired
    Map<String, PayService> payServiceMap;

    Map<ChannelEnum, PayService> channelPayServiceMap;

    public PayResult epay(PayRequest payRequest) {
        return new PayResult();
    }

    @PostConstruct
    public void init() {
        for (PayService payService : payServiceSet) {
            channelPayServiceMap.put(payService.channel(), payService);
        }
    }
}
