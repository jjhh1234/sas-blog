package com.ex.sas.Domain.Category.Repository;

import com.ex.sas.Domain.Category.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
}
