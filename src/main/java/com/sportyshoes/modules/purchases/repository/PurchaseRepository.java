package com.sportyshoes.modules.purchases.repository;

import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

    public List<Purchase> findByUser(User user);

}

