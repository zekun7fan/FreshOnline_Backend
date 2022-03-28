package com.example.freshonline.service;


import com.example.freshonline.dao.PaymentMethodMapper;
import com.example.freshonline.model.PaymentMethod;
import com.example.freshonline.model.PaymentMethodExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    public boolean addPaymentMethod(PaymentMethod paymentMethod){
        return paymentMethodMapper.insertSelective(paymentMethod) == 1;
    }

    public boolean delPaymentMethod(Integer pid){
        return paymentMethodMapper.deleteByPrimaryKey(pid) == 1;
    }

    public List<PaymentMethod> getPaymentMethodByUserId(Integer userId){
        PaymentMethodExample example = new PaymentMethodExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return paymentMethodMapper.selectByExample(example);
    }
}
