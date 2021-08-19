package com.sportyshoes.modules.categories.entity;


import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.products.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
@Entity(name = "categories")
@NamedQueries(value={
        @NamedQuery(name = "query_get_all_categories",
            query = "Select c From categories c")
})
public class Category {

    public Category(CategoryDto categoryDto) {
        this.id=categoryDto.getId();
        this.name=categoryDto.getName();
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name")
    private String name;

    //mappedBy makes Product the owner side of the relationship
    @OneToMany(mappedBy = "category")
    @Setter(AccessLevel.NONE)
    public Set<Product> products = new HashSet<>();

    public CategoryDto toDto() {
        return CategoryDto.builder().id(this.id).name(this.name).build();
    }

    public void setDto(CategoryDto categoryDto) {
        this.name=categoryDto.getName();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
