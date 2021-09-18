package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.ProductPurchase;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.ProductPurchaseRepository;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CreatePurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductPurchaseRepository productPurchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Autowired
    public CreatePurchaseService(PurchaseRepository purchaseRepository,
                                 UserRepository userRepository,
                                 ProductRepository productRepository,
                                 ProductPurchaseRepository productPurchaseRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productPurchaseRepository = productPurchaseRepository;
    }

    @Transactional
    public PurchaseDto execute(PurchaseDto purchaseDto) throws SportyShoesResourceNotFoundException {

        Optional<User> userOptional = userRepository.findById(purchaseDto.getUser().getId());
        if( userOptional.isEmpty() ) {
            throw new SportyShoesResourceNotFoundException("User " + purchaseDto.getUser().getId() + " not found");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(userOptional.get());

        purchaseRepository.save(purchase);

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

            purchase.addProductPurchase(productPurchase);
            productPurchaseRepository.save(productPurchase);

            product.addProductPurchase(productPurchase);

        }



        return purchase.toDto();
    }

}
