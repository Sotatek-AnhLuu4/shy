package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
//du lieu dat hang
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String customerType; //vip,normal
    private List<OrderItem> items; //dsmua
    private String couponCode; //magiamgia

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private String sku; //mahang
        private BigDecimal price;//dongia
        private int quantity;//soluong
    }
}