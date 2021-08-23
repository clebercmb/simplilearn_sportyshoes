package com.sportyshoes.modules.purchases.entity;

import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_purchase")
public class ProductPurchase {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    private Product product;

    @ManyToOne
    private Purchase purchase;

    @Column
    private Long quantity;


    @Override
    public String toString() {
        return "ProductPurchase{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }
}
