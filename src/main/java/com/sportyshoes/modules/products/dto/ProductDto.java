package com.sportyshoes.modules.products.dto;

import com.sportyshoes.modules.categories.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private CategoryDto category;
    private Double price;

    public String priceFormatted() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        return nf.format(price);
    }

}
