package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.Promotion;
import com.example.demo.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionRepository promotionRepository; //uy quyen cho repository

    @GetMapping // lay ds khuyen mai
    public ApiResponse<List<Promotion>> getActivePromotions() { 
        return ApiResponse.success(promotionRepository.findByActiveTrue());
    }

    @PostMapping //tao khuyen mai moi
    public ApiResponse<Promotion> createPromotion(@RequestBody Promotion promotion) {
        return ApiResponse.success(promotionRepository.save(promotion));
    }
}