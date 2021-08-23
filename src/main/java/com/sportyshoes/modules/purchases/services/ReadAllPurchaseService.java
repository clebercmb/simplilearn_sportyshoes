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
import java.util.ArrayList;
import java.util.List;


@Service

public class ReadAllPurchaseService {


    private final PurchaseRepository purchaseRepository;

    @Autowired
    public ReadAllPurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Transactional
    public List<PurchaseDto>  execute() {
        List<Purchase> purchaseList = purchaseRepository.findAll();

        List<PurchaseDto> purchaseDtoList = new ArrayList<>();

        purchaseList.forEach(purchase -> purchaseDtoList.add(purchase.toDto()));

        return purchaseDtoList;
    }

}
