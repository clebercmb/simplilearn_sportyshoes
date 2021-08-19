package com.sportyshoes.modules.categories.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CreateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryDto execute(CategoryDto categoryDto) {
        Category category = Category.builder().name(categoryDto.getName()).build();
        categoryRepository.save(category);
        return category.toDto();
    }

}
