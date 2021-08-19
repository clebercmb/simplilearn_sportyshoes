package com.sportyshoes.modules.products.dto;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFormDto {

    private Long id;
    private String name;
    private Long categoryId;
    private Double price;

}
