package com.sportyshoes.modules.purchases.entity;

import com.sportyshoes.modules.products.entity.Product;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.users.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "purchases")
@NamedQueries(value={
        @NamedQuery(name = "query_get_all_purchases",
                query = "Select p From purchases p")
})
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    //mappedBy makes Product the owner of the relationship
    @OneToMany(mappedBy = "purchase")
    @Setter(AccessLevel.NONE)
    private List<ProductPurchase> productPurchaseList = new ArrayList<>();
    //private Set<ProductPurchase> productPurchaseList = new HashSet<>();


    public void addProductPurchase(ProductPurchase productPurchase) {
        this.productPurchaseList.add(productPurchase);
    }

    public void removeProductPurchase(ProductPurchase productPurchase) {
        this.productPurchaseList.remove(productPurchase);
    }


    public PurchaseDto toDto() {

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(this.id);
        purchaseDto.setUser(this.user.toDto());
        for ( ProductPurchase productPurchase: productPurchaseList ) {

            ProductPurchaseDto productPurchaseDto = new ProductPurchaseDto();
            productPurchaseDto.setPurchase(purchaseDto);
            productPurchaseDto.setProduct(productPurchase.getProduct().toDto());
            productPurchaseDto.setQuantity(productPurchase.getQuantity());
            //purchaseDto.addProductPurchaseDto(productPurchase.toDto());
            purchaseDto.addProductPurchaseDto(productPurchaseDto);


        }

        return purchaseDto;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                '}';
    }
}
