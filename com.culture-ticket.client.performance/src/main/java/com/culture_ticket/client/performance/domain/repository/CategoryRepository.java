package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByNameAndIsDeletedFalse(String name);

    Optional<Category> findCategoryById(UUID categoryId);

    Page<Category> findByNameContainingAndIsDeletedFalse(String name, Pageable pageable);

    Page<Category> findByIsDeletedFalse(Pageable pageable);
}
