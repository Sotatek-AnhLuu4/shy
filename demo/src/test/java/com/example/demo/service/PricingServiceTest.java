package com.example.demo.service;

import com.example.demo.dto.DiscountDto;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.exception.BusinessException;
import com.example.demo.strategy.PromotionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    @Mock
    private PromotionStrategy strategy1;

    @Mock
    private PromotionStrategy strategy2;

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService(List.of(strategy1, strategy2));
    }

    private OrderRequest buildRequest() {
        OrderRequest request = new OrderRequest();
        request.setCustomerType("VIP");
        request.setItems(List.of(
                new OrderRequest.OrderItem("A100", new BigDecimal("100"), 2)
        ));
        return request;
    }

    @Test
    void khongCoStrategyNaoApDung_thiKhongGiamGia() {
        OrderRequest request = buildRequest();
        when(strategy1.isApplicable(request)).thenReturn(false);
        when(strategy2.isApplicable(request)).thenReturn(false);

        OrderResponse response = pricingService.calculatePrice(request);

        assertEquals(new BigDecimal("200"), response.getSubtotal());
        assertEquals(BigDecimal.ZERO, response.getTotalDiscount());
        assertEquals(new BigDecimal("200"), response.getFinalPrice());
        assertTrue(response.getDiscounts().isEmpty());
    }

    @Test
    void motStrategyApDung_thiTruDungSoTien() {
        OrderRequest request = buildRequest();
        when(strategy1.isApplicable(request)).thenReturn(true);
        when(strategy1.apply(eq(request), any())).thenReturn(new DiscountDto("PERCENTAGE_DISCOUNT", new BigDecimal("20")));
        when(strategy2.isApplicable(request)).thenReturn(false);

        OrderResponse response = pricingService.calculatePrice(request);

        assertEquals(new BigDecimal("200"), response.getSubtotal());
        assertEquals(new BigDecimal("20"), response.getTotalDiscount());
        assertEquals(new BigDecimal("180"), response.getFinalPrice());
        assertEquals(1, response.getDiscounts().size());
    }

    @Test
    void nhieuStrategyApDungCungLuc_thiCongDonTatCaDiscount() {
        OrderRequest request = buildRequest();
        when(strategy1.isApplicable(request)).thenReturn(true);
        when(strategy1.apply(eq(request), any())).thenReturn(new DiscountDto("PERCENTAGE_DISCOUNT", new BigDecimal("20")));
        when(strategy2.isApplicable(request)).thenReturn(true);
        when(strategy2.apply(eq(request), any())).thenReturn(new DiscountDto("VIP_DISCOUNT", new BigDecimal("10")));

        OrderResponse response = pricingService.calculatePrice(request);

        assertEquals(new BigDecimal("200"), response.getSubtotal());
        assertEquals(new BigDecimal("30"), response.getTotalDiscount());
        assertEquals(new BigDecimal("170"), response.getFinalPrice());
        assertEquals(2, response.getDiscounts().size());
    }

    @Test
    void requestKhongCoItem_thiNemBusinessException() {
        OrderRequest request = new OrderRequest();
        request.setItems(List.of());

        BusinessException ex = assertThrows(BusinessException.class,
                () -> pricingService.calculatePrice(request));

        assertEquals("INVALID_REQUEST", ex.getCode());
    }

    @Test
    void requestItemsNull_thiNemBusinessException() {
        OrderRequest request = new OrderRequest();
        request.setItems(null);

        assertThrows(BusinessException.class, () -> pricingService.calculatePrice(request));
    }
}