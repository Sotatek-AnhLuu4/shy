package com.example.demo.service;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.exception.BusinessException;
import com.example.demo.strategy.PromotionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService { //

    private final List<PromotionStrategy> strategies; // khai bao cac strategy khuyen maiư      

    @Autowired
    public PricingService(List<PromotionStrategy> strategies) {
        this.strategies = strategies;
    }

    public OrderResponse calculatePrice(OrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("INVALID_REQUEST", "Đơn hàng phải có ít nhất 1 item");
        }

        BigDecimal subtotal = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderResponse response = new OrderResponse();
        response.setSubtotal(subtotal);

        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (PromotionStrategy strategy : strategies) { //duyet khuyen mai
            if (strategy.isApplicable(request)) {
                DiscountDto discount = strategy.apply(request, subtotal);
                if (discount != null && discount.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    response.getDiscounts().add(discount);
                    totalDiscount = totalDiscount.add(discount.getAmount());
                }
            }
        }
       
        response.setTotalDiscount(totalDiscount);
        response.setFinalPrice(subtotal.subtract(totalDiscount));

        return response;
    }
}