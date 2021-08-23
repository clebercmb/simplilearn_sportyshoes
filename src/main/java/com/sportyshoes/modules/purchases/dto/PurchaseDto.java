package com.sportyshoes.modules.purchases.dto;


import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.users.dto.UserDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {

    private Long id;
    private UserDto user;

    @Setter(AccessLevel.NONE)
    private List<ProductPurchaseDto> productPurchaseDtoList = new ArrayList<>();

    public void addProductPurchaseDto(ProductPurchaseDto productDto) {
        productPurchaseDtoList.add(productDto);
    }

    public void removeProductPurchaseDto(ProductPurchaseDto productDto) {
        productPurchaseDtoList.remove(productDto);

    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
