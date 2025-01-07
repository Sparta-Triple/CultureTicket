package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.domain.model.Category;
import com.culture_ticket.client.performance.domain.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // 카테고리 생성
    @Transactional
    public void createCategory(String role, String username, CategoryRequestDto categoryRequestDto) {
        validateRoleADMIN(role);

        Optional<Category> existCategoryOpt = categoryRepository.findByNameAndIsDeletedFalse(categoryRequestDto.getName());
        Category category;

        if (existCategoryOpt.isPresent()) {
            Category existCategory = existCategoryOpt.get();

            if (existCategory.getIsDeleted()) {
                category = Category.createCategory(categoryRequestDto);
            } else {
                throw new CustomException(ErrorType.CATEGORY_DUPLICATE);
            }
        } else {
            category = Category.createCategory(categoryRequestDto);
        }
        category.setCreatedBy(username);
        categoryRepository.save(category);
    }

    // 카테고리 단일 조회
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategory(String role, UUID categoryId) {
        validateRoleADMIN(role);
        Category category = findCategoryById(categoryId);
        return new CategoryResponseDto(category);
    }

    // 카테고리 목록 조회 & 검색
    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getCategories(String role, String keyword, Pageable pageable) {
        validateRoleADMIN(role);
        Page<Category> categories;

        if (keyword == null || keyword.trim().isEmpty()) {
            categories = categoryRepository.findByIsDeletedFalse(pageable);
        } else {
            categories = categoryRepository.findByNameContainingAndIsDeletedFalse(keyword, pageable);
        }
        return categories.map(CategoryResponseDto::new);
    }

    // 카테고리 수정
    @Transactional
    public void updateCategory(String role, String username, UUID categoryId, CategoryRequestDto categoryRequestDto) {
        validateRoleADMIN(role);
        Category category = findCategoryById(categoryId);
        category.setUpdateBy(categoryRequestDto.getName(), username);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(String role, String username, UUID categoryId) {
        validateRoleADMIN(role);
        Category category = findCategoryById(categoryId);
        category.setDeletedBy(username);
    }


    private Category findCategoryById(UUID categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));

        if (category.getIsDeleted()) {
            throw new CustomException(ErrorType.CATEGORY_NOT_FOUND);
        }
        return category;
    }

    private void validateRoleADMIN(String role) {
        if (!role.equals("ADMIN")) {
            throw new CustomException(ErrorType.FORBIDDEN);
        }
    }
}
