package com.sportyshoes.modules.products.controller;


import com.sportyshoes.modules.categories.dto.CategoryDto;
import com.sportyshoes.modules.categories.services.ReadAllCategoriesService;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.dto.ProductFormDto;
import com.sportyshoes.modules.products.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadAllCategoriesService readAllCategoriesService;
    private final CreateProductService createProductService;
    private final DeleteProductService deleteProductService;
    private final ReadAllProductService readAllProductService;
    private final ReadProductService readProductService;
    private final UpdateProductService updateProductService;

    @Autowired
    public ProductController(CreateProductService createProductService,
                             DeleteProductService deleteProductService,
                             ReadAllProductService readAllProductService,
                             ReadProductService readProductService,
                             UpdateProductService updateProductService,
                             ReadAllCategoriesService readAllCategoriesService ) {
        this.createProductService = createProductService;
        this.deleteProductService = deleteProductService;
        this.readAllProductService = readAllProductService;
        this.readProductService = readProductService;
        this.updateProductService = updateProductService;
        this.readAllCategoriesService = readAllCategoriesService;
    }

    @GetMapping
    public ModelAndView all() {
        logger.info(">>>>/products.All");
        ModelAndView modelAndView =  new ModelAndView("products");
        List<ProductDto> productDtoList = readAllProductService.execute();
        modelAndView.addObject("products", productDtoList);
        return modelAndView;
    }

    @GetMapping(value="/add_form")
    public ModelAndView add() {
        logger.info(">>>>Add");
        ModelAndView modelAndView = new ModelAndView("productsAddForm");
        ProductDto productDto = ProductDto.builder().build();
        //productDto.setCategory(CategoryDto.builder().build());
        modelAndView.addObject("product", productDto);

        List<CategoryDto> categoryDtoList = readAllCategoriesService.execute();
        modelAndView.addObject("categories", categoryDtoList);

        return modelAndView;
    }

    @GetMapping(value = {"/{id}/edit"})
    public ModelAndView update(@PathVariable Long id) {
        logger.info(">>>>update");
        ModelAndView modelAndView = new ModelAndView("productsUpdateForm");
        Optional<ProductDto> productDtoOptional = readProductService.execute(id);

        if(productDtoOptional.isEmpty()) {
            modelAndView.addObject("message", "Product Not Found");
            return modelAndView;
        }
        modelAndView.addObject("product", productDtoOptional.get());

        List<CategoryDto> categoryDtoList = readAllCategoriesService.execute();
        modelAndView.addObject("categories", categoryDtoList);

        return modelAndView;
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String create(ProductDto productDto) {

        productDto =  createProductService.execute(productDto);

        return "redirect:/products";
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public String update(ProductDto productDto) {

        updateProductService.execute(productDto);

        return "redirect:/products";
    }




}
