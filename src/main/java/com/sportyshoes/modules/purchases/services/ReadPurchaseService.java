package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.products.repository.ProductRepository;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ReadPurchaseService {


    private final PurchaseRepository purchaseRepository;

    @Autowired
    public ReadPurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public Optional<PurchaseDto> execute(Long id) {

        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);

        PurchaseDto purchaseDto=null;
        if(purchaseOptional.isPresent()) {
            purchaseDto=purchaseOptional.get().toDto();
        }

        return Optional.ofNullable(purchaseDto);
    }

}
