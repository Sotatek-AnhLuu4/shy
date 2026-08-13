package com.example.demo.strategy;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class VipCustomerStrategy implements PromotionStrategy {
    @Override
    public boolean isApplicable(OrderRequest request) {
        return "VIP".equalsIgnoreCase(request.getCustomerType());
    }

    @Override
    public DiscountDto apply(OrderRequest request, BigDecimal subtotal) {
        BigDecimal discount = subtotal.multiply(new BigDecimal("0.05"));
        return new DiscountDto("VIP_DISCOUNT", discount);
    }
}