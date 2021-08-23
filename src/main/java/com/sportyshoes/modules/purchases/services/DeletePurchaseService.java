package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletePurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public DeletePurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void execute(Long id) {

        purchaseRepository.deleteById(id);

    }

}
