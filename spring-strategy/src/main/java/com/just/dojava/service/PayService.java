package com.just.dojava.service;

import com.just.dojava.service.domain.PayRequest;
import com.just.dojava.service.domain.PayResult;
import com.just.dojava.service.enums.ChannelEnum;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/22
 */
public interface PayService {

    PayResult epay(PayRequest request);


    ChannelEnum channel();

}
