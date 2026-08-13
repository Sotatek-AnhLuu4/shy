package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController //dieu khien du lieu thanh json 
@RequestMapping("/api/v1/orders") //huong duong dan api
@RequiredArgsConstructor 
public class OrderController {

    private final PricingService pricingService; //uy quyen cho service tinh gia

    @PostMapping("/calculate") 
    public ApiResponse<OrderResponse> calculatePrice(@RequestBody OrderRequest request) { 
        OrderResponse response = pricingService.calculatePrice(request);
        return ApiResponse.success(response);
    }
}   