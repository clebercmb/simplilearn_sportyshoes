package com.sportyshoes.modules.products.entity;

import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.purchases.entity.ProductPurchase;
import com.sportyshoes.modules.purchases.entity.Purchase;
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
@Entity(name = "products")
@NamedQueries(value={
        @NamedQuery(name = "query_get_all_products",
                query = "Select c From products c")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    private Category category;

    @Column(name="price")
    private Double price;

    @OneToMany(mappedBy = "product")
    @Setter(AccessLevel.NONE)
    private List<ProductPurchase> productPurchaseList = new ArrayList<>();
    //private Set<ProductPurchase> productPurchaseList = new HashSet<>();

    public ProductDto toDto() {
        ProductDto productDto = ProductDto.builder().id(this.id).name(this.name).category(this.category.toDto()).price(this.price).build();
        return productDto;
    }

    public void addProductPurchase(ProductPurchase productPurchase) {
        this.productPurchaseList.add(productPurchase);
    }

    public void removeProductPurchase(ProductPurchase productPurchase) {
        this.productPurchaseList.remove(productPurchase);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
