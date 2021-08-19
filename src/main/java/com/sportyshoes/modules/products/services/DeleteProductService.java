package com.sportyshoes.modules.products.services;

import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DeleteProductService {

    private final ProductRepository productRepository;

    @Autowired
    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(Long id) {

        productRepository.deleteById(id);

    }

}
