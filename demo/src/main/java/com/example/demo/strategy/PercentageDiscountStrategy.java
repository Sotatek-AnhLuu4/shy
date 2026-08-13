package com.example.demo.strategy;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component 
public class PercentageDiscountStrategy implements PromotionStrategy {
    @Override
    public boolean isApplicable(OrderRequest request) {
        return true; // Ap dung moi don
    }

    @Override
    public DiscountDto apply(OrderRequest request, BigDecimal subtotal) {
        BigDecimal discount = subtotal.multiply(new BigDecimal("0.10"));
        return new DiscountDto("PERCENTAGE_DISCOUNT", discount);
    }
}