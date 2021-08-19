package com.sportyshoes.modules.categories.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class ReadCategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public ReadCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<CategoryDto> execute(Long id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        CategoryDto categoryDto=null;
        if(categoryOptional.isPresent()) {
            categoryDto=categoryOptional.get().toDto();
        }

        return Optional.ofNullable(categoryDto);
    }

}
