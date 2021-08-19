package com.sportyshoes.modules.products.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReadProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ReadProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Optional<ProductDto> execute(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        ProductDto productDto=null;
        if(productOptional.isPresent()) {
            productDto=productOptional.get().toDto();
        }

        return Optional.ofNullable(productDto);
    }

}
