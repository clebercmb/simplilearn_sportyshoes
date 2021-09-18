package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.ProductPurchase;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UpdatePurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public UpdatePurchaseService(PurchaseRepository purchaseRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Optional<PurchaseDto> execute(PurchaseDto purchaseDto) throws  SportyShoesResourceNotFoundException{

        if (purchaseDto == null) {
            throw new SportyShoesResourceNotFoundException("Please provide a purchase to be found ");
        }

        Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseDto.getId());
        if(purchaseOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("Purchase "+purchaseDto.getId()+" not found");
        }

        Optional<User> userOptional = userRepository.findById(purchaseDto.getUser().getId());
        if( userOptional.isEmpty() ) {
            throw new SportyShoesResourceNotFoundException("User " + purchaseDto.getUser().getId() + " not found");
        }

        Purchase purchase = purchaseOptional.get();
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

        return Optional.ofNullable(purchase.toDto());
    }
}
