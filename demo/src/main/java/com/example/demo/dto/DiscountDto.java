package com.example.demo.dto; // cac khoan giam

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DiscountDto {
    private String type;//loai giam 
    private BigDecimal amount;//so tiem giam
}