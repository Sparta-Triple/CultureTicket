package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponseDto;
import com.culture_ticket.client.performance.application.service.CategoryService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @RequestBody CategoryRequestDto categoryRequestDto,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestHeader(value = "X-User-Name") String username) {
        categoryService.createCategory(role, username, categoryRequestDto);
        return new ResponseMessageDto(ResponseStatus.CREATE_CATEGORY_SUCCESS);
    }

    // 카테고리 단건 조회
    @GetMapping("/info/{categoryId}")
    public ResponseDataDto<CategoryResponseDto> getCategory(
            @PathVariable UUID categoryId
    ) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategory(categoryId);
        return new ResponseDataDto<>(ResponseStatus.GET_CATEGORY_SUCCESS, categoryResponseDto);
    }

    // 카테고리 목록 조회 & 검색
    @GetMapping("/info")
    public ResponseDataDto<Page<CategoryResponseDto>> getCategories(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault Pageable pageable
    ) {
        Page<CategoryResponseDto> categoryResponses = categoryService.getCategories(keyword, pageable);
        return new ResponseDataDto<>(ResponseStatus.GET_CATEGORY_SUCCESS, categoryResponses);
    }

    // 카테고리 수정
    @PatchMapping("/{categoryId}")
    public ResponseMessageDto updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody CategoryRequestDto categoryRequestDto,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestHeader(value = "X-User-Name") String username
    ) {
        categoryService.updateCategory(role, username, categoryId, categoryRequestDto);
        return new ResponseMessageDto(ResponseStatus.UPDATE_CATEGORY_SUCCESS);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseMessageDto deleteCategory(
            @PathVariable UUID categoryId,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestHeader(value = "X-User-Name") String username
    ) {
        categoryService.deleteCategory(role, username, categoryId);
        return new ResponseMessageDto(ResponseStatus.DELETE_CATEGORY_SUCCESS);
    }
}
