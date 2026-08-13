package com.example.demo.strategy;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class BogoStrategy implements PromotionStrategy {
    @Override
    public boolean isApplicable(OrderRequest request) {
        // ap dung mua 3 ca
        return request.getItems().stream().anyMatch(item -> item.getQuantity() >= 3);
    }

    @Override
    public DiscountDto apply(OrderRequest request, BigDecimal subtotal) {
        BigDecimal discountAmount = BigDecimal.ZERO;
        for (OrderRequest.OrderItem item : request.getItems()) {
            if (item.getQuantity() >= 3) {
                // 3 free 1
                int freeItems = item.getQuantity() / 3;
                discountAmount = discountAmount.add(item.getPrice().multiply(BigDecimal.valueOf(freeItems)));
            }
        }
        return new DiscountDto("BUY2_GET1_FREE", discountAmount);
    }
}