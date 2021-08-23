package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.categories.repository.CategoryRepository;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.ProductPurchase;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CreatePurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CreatePurchaseService(PurchaseRepository purchaseRepository,
                                 UserRepository userRepository,
                                 ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public PurchaseDto execute(PurchaseDto purchaseDto) throws SportyShoesResourceNotFoundException {


        Optional<User> userOptional = userRepository.findById(purchaseDto.getUser().getId());
        if( userOptional.isEmpty() ) {
            throw new SportyShoesResourceNotFoundException("User " + purchaseDto.getUser().getId() + " not found");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(userOptional.get());

        for(ProductPurchaseDto productPurchaseDto:purchaseDto.getProductPurchaseDtoList()) {

            Optional<Product> productOptional = productRepository.findById(productPurchaseDto.getProduct().getId());
            if( productOptional.isEmpty() ) {
                throw new SportyShoesResourceNotFoundException("Product " + productPurchaseDto.getId() + " not found");
            }

            ProductPurchase productPurchase = new ProductPurchase();
            productPurchase.setPurchase(purchase);
            Product product = productOptional.get();

            productPurchase.setProduct(product);
            productPurchase.setQuantity(productPurchaseDto.getQuantity());

            product.addProductPurchase(productPurchase);
            purchase.addProductPurchase(productPurchase);

        }

        purchaseRepository.save(purchase);

        return purchase.toDto();
    }

}
