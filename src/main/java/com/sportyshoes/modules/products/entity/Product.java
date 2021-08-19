package com.sportyshoes.modules.products.entity;

import com.sportyshoes.modules.categories.entity.Category;
import com.sportyshoes.modules.products.dto.ProductDto;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    @GeneratedValue
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    private Category category;

    @Column(name="price")
    private Double price;


    public ProductDto toDto() {
        ProductDto productDto = ProductDto.builder().id(this.id).name(this.name).category(this.category.toDto()).price(this.price).build();
        return productDto;
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
