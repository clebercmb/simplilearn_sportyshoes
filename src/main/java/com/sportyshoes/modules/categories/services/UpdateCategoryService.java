package com.sportyshoes.modules.categories.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public UpdateCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<CategoryDto> execute(CategoryDto categoryDto) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
        if(categoryOptional.isEmpty()) {
            return Optional.empty();
        }
        Category category = categoryOptional.get();
        category.setDto(categoryDto);

        category = categoryRepository.save(category);
        return Optional.ofNullable(category.toDto());
    }
}
