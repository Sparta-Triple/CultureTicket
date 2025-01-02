package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequest;
import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponse;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.domain.model.Category;
import com.culture_ticket.client.performance.domain.repository.CategoryRepository;
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
    public void createCategory(CategoryRequest categoryRequest) {

        Optional<Category> existCategoryOpt = categoryRepository.findCategoryByName(categoryRequest.getName());
        Category category;

        if(existCategoryOpt.isPresent()) {
            Category existCategory = existCategoryOpt.get();

            // isDeleted=true면, 생성가능
            if(existCategory.getIsDeleted()){
                category = Category.createCategory(categoryRequest);
            }
            else{
                throw new CustomException(ErrorType.CATEGORY_DUPLICATE);
            }
        }
        else {
            category = Category.createCategory(categoryRequest);
        }

        category.setCreatedBy("test@email.com");
        categoryRepository.save(category);
    }

    // 카테고리 단일 조회
    @Transactional(readOnly = true)
    public CategoryResponse getCategory(UUID categoryId) {
        Category category = findCategoryById(categoryId);
        return new CategoryResponse(category);
    }

    // 카테고리 목록 조회


    // 카테고리 수정
    @Transactional
    public void updateCategory(UUID categoryId, CategoryRequest categoryRequest) {
        Category category = findCategoryById(categoryId);
        category.setUpdateBy(
                categoryRequest.getName(),
                "test@eamil.com"
        );
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = findCategoryById(categoryId);
        category.setDeletedBy(
            "test@eamil.com"
        );
    }






    private Category findCategoryById(UUID categoryId) {

        // 카테고리 조회
        Category category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));
        // 삭제된 상태인지 확인
        if(category.getIsDeleted()){
            throw new CustomException(ErrorType.CATEGORY_NOT_FOUND);
        }
        return category;
    }

    private Category findCategoryByName(String name) {
        // 카테고리 조회
        Category category = categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));
        // 삭제된 상태인지 확인
        if(category.getIsDeleted()){
            throw new CustomException(ErrorType.CATEGORY_NOT_FOUND);
        }
        return category;
    }
}
