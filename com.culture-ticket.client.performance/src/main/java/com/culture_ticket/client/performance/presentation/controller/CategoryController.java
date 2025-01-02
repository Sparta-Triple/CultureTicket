package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequest;
import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponse;
import com.culture_ticket.client.performance.application.service.CategoryService;
import com.culture_ticket.client.performance.common.*;
import com.culture_ticket.client.performance.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseMessageDto createCategory(
            @RequestBody CategoryRequest categoryRequest){

            categoryService.createCategory(categoryRequest);
            return new ResponseMessageDto(ResponseStatus.CREATE_CATEGORY_SUCCESS);
    }

    // 카테고리 단건 조회
    @GetMapping("/{categoryId}")
    public ResponseDataDto<CategoryResponse> getCategory(@PathVariable UUID categoryId){
        CategoryResponse categoryResponse = categoryService.getCategory(categoryId);
        return new ResponseDataDto<>(ResponseStatus.GET_CATEGORY_SUCCESS, categoryResponse);
    }

    // 카테고리 목록 조회

    // 카테고리 수정
    @PatchMapping("/{categoryId}")
    public ResponseMessageDto updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequest categoryRequest){
        categoryService.updateCategory(categoryId, categoryRequest);
        return new ResponseMessageDto(ResponseStatus.UPDATE_CATEGORY_SUCCESS);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseMessageDto deleteCategory(@PathVariable UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseMessageDto(ResponseStatus.DELETE_CATEGORY_SUCCESS);
    }
}
