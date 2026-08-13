package com.example.demo.strategy;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.DiscountDto;
import java.math.BigDecimal;

public interface PromotionStrategy {
    // check don hang co du dieu kien k
    boolean isApplicable(OrderRequest request);
    
    // neu co tinh so tien giam
    DiscountDto apply(OrderRequest request, BigDecimal subtotal);
}