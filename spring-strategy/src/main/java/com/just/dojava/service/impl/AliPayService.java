package com.just.dojava.service.impl;

import com.just.dojava.service.PayService;
import com.just.dojava.service.domain.PayRequest;
import com.just.dojava.service.domain.PayResult;
import com.just.dojava.service.enums.ChannelEnum;
import org.springframework.stereotype.Service;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/22
 */
@Service("aliPayService")
public class AliPayService implements PayService {

    @Override
    public PayResult epay(PayRequest request) {
        return new PayResult();
    }

    @Override
    public ChannelEnum channel() {
        return ChannelEnum.ALIPAY;
    }
}
