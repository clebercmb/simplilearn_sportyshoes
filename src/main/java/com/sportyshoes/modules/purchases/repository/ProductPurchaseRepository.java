package com.sportyshoes.modules.purchases.repository;

import com.sportyshoes.modules.purchases.entity.ProductPurchase;
import com.sportyshoes.modules.purchases.entity.Purchase;
import com.sportyshoes.modules.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPurchaseRepository extends JpaRepository<ProductPurchase, Long>{

}

