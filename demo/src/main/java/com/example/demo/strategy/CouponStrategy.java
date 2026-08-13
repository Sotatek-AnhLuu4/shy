package com.example.demo.strategy;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.exception.BusinessException;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class CouponStrategy implements PromotionStrategy {
    @Override
    public boolean isApplicable(OrderRequest request) {
        return request.getCouponCode() != null && !request.getCouponCode().isEmpty();
    }

    @Override
    public DiscountDto apply(OrderRequest request, BigDecimal subtotal) {
        String code = request.getCouponCode().toUpperCase();

        BigDecimal discountAmount = switch (code) {
            case "SUMMER10" -> new BigDecimal("10.00");
            case "SAVE20" -> new BigDecimal("20.00");
            default -> throw new BusinessException(
                    "INVALID_COUPON",
                    "Coupon code " + code + " không hợp lệ hoặc đã hết hạn"
            );
        };

        return new DiscountDto("COUPON_" + code, discountAmount);
    }
}