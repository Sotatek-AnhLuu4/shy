package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    private String code; // VD: "SUMMER10", "SAVE20"

    private BigDecimal discountAmount;

    private Boolean active;

    private LocalDate expiryDate;
}