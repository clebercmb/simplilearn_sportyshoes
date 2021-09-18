package com.sportyshoes.modules.products.services;

import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UpdateProductService(ProductRepository productRepository,
                                CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<ProductDto> execute(ProductDto productDto) throws  SportyShoesResourceNotFoundException{

        if (productDto == null) {
            throw new SportyShoesResourceNotFoundException("Please provide a product to be found ");
        }

        Optional<Product> productOptional = productRepository.findById(productDto.getId());
        if(productOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("Product "+productDto.getId()+" not found");
        }

        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategory().getId());
        if(categoryOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("Category "+productDto.getCategory().getId()+" not found");
        }

        Product product = productOptional.get();
        product.setName(productDto.getName());
        product.setCategory(categoryOptional.get());
        product.setPrice(productDto.getPrice());

        product = productRepository.save(product);

        return Optional.ofNullable(product.toDto());
    }
}
