package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
// du lieu tra ve
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse { 
    private BigDecimal subtotal; //
    private List<DiscountDto> discounts = new ArrayList<>();//ds giam gia , khoi tao 
    private BigDecimal totalDiscount = BigDecimal.ZERO; 
    private BigDecimal finalPrice;
}