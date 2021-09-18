package com.sportyshoes.modules.purchases.services;

import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.purchases.repository.PurchaseRepository;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.share.exceptions.SportyShoesResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReadProductPurchaseByUserService {


    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;


    @Autowired
    public ReadProductPurchaseByUserService(PurchaseRepository purchaseRepository,
                                            UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<PurchaseDto> execute(Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new SportyShoesResourceNotFoundException("User " + userId + " not found!");
        }
        User user = userOptional.get();
        List<Purchase> purchaseList = purchaseRepository.findByUser(user);

        List<PurchaseDto> purchaseDtoList = new ArrayList<>();

        purchaseList.forEach(purchase -> purchaseDtoList.add(purchase.toDto()));

        return purchaseDtoList;
    }

}
