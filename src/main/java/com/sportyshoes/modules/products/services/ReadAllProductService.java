package com.sportyshoes.modules.products.services;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReadAllProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ReadAllProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto>  execute() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        productList.forEach(product -> productDtoList.add(product.toDto()));

        return productDtoList;
    }

}
