package com.sportyshoes.modules.purchases.controller;


import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.services.ReadProductService;
import com.sportyshoes.modules.purchases.dto.ProductPurchaseDto;
import com.sportyshoes.modules.purchases.dto.PurchaseDto;
import com.sportyshoes.modules.purchases.services.CreatePurchaseService;
import com.sportyshoes.modules.purchases.services.DeletePurchaseService;
import com.sportyshoes.modules.purchases.services.ReadPurchaseByUserService;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/purchases")
public class PurchasesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadPurchaseByUserService readPurchaseByUserService;
    private final ReadProductService readProductService;
    private final CreatePurchaseService createPurchaseService;
    private final DeletePurchaseService deletePurchaseService;


    @Autowired
    public PurchasesController(ReadPurchaseByUserService readPurchaseByUserService,
                               ReadProductService readProductService,
                               CreatePurchaseService createPurchaseService,
                               DeletePurchaseService deletePurchaseService) {
        this.readPurchaseByUserService = readPurchaseByUserService;
        this.readProductService = readProductService;
        this.createPurchaseService = createPurchaseService;
        this.deletePurchaseService = deletePurchaseService;
    }

    @GetMapping()
    String all(HttpSession session, Model model) {
        logger.info(">>>>All");
        UserDto userDto = (UserDto) session.getAttribute("user");

        List<PurchaseDto> purchaseDtoList = readPurchaseByUserService.execute(userDto.getId());
        model.addAttribute("purchases", purchaseDtoList);

        return "purchases";
    }


    @GetMapping(value = {"/product2/{productId}"})
    String purchase2(@PathVariable Long productId, HttpSession session, Model model) {
        logger.info(">>>>purchase");
        UserDto userDto = (UserDto) session.getAttribute("user");

        Optional<ProductDto> productDtoOptional = readProductService.execute(productId);

        assert productDtoOptional.isPresent();

        ProductDto productDto = productDtoOptional.get();
        model.addAttribute("product", productDto);

        return "purchaseProductForm";
    }

    @GetMapping(value = {"/product/{productId}"})
    public ModelAndView purchase(@PathVariable Long productId, HttpSession session) {
            logger.info(">>>>purchase");
        ModelAndView modelAndView = new ModelAndView("purchaseProductForm");
        Optional<ProductDto> productDtoOptional = readProductService.execute(productId);

        if(productDtoOptional.isEmpty()) {
            modelAndView.addObject("message", "Product Not Found");
            return modelAndView;
        }

        UserDto userDto = (UserDto) session.getAttribute("user");

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(userDto);

        ProductPurchaseDto productPurchaseDto = new ProductPurchaseDto();
        productPurchaseDto.setProduct(productDtoOptional.get());
        productPurchaseDto.setPurchase(purchaseDto);
        purchaseDto.addProductPurchaseDto(productPurchaseDto);


        modelAndView.addObject("productPurchase", productPurchaseDto);

        return modelAndView;
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String create(ProductPurchaseDto productPurchaseDto, HttpSession session) {

        //productDto =  createProductService.execute(productDto);

        PurchaseDto purchaseDto = new PurchaseDto();
        UserDto user = (UserDto) session.getAttribute("user");

        purchaseDto.setUser(user);
        purchaseDto.addProductPurchaseDto(productPurchaseDto);

        createPurchaseService.execute(purchaseDto);

        return "redirect:/purchases";
    }



    @GetMapping(value = {"/{id}/delete"})
    public String delete(@PathVariable Long id, HttpSession session) {
        logger.info(">>>>delete order id={}", id);

        UserDto userDto = (UserDto) session.getAttribute("user");

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setUser(userDto);

        deletePurchaseService.execute(id);

        return "redirect:/purchases";
    }


}
