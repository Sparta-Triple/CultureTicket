package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, CategoryRepositoryCustom {

    Optional<Category> findCategoryByName(String name);
    boolean existsCategoriesByName(String name);

    Optional<Category> findCategoryById(UUID categoryId);


    boolean existsCategoriesByIsDeleted(Boolean isDeleted);
}
