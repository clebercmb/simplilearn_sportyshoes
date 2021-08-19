package com.sportyshoes.modules.products.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.share.SportyShoesException;
import com.sportyshoes.share.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CreateProductService(ProductRepository productRepository,
                                CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public ProductDto execute(ProductDto productDto) throws SportyShoesResourceNotFoundException {

        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategory().getId());

        if(categoryOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("Category not found");
        }

        Product product = Product.builder().
                name(productDto.getName()).
                category(categoryOptional.get()).
                price(productDto.getPrice()).
                build();

        productRepository.save(product);
        return product.toDto();
    }

}
