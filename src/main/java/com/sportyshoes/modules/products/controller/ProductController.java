package com.sportyshoes.modules.products.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    String all() {
        logger.info(">>>>/products.All");
        return "products";
    }

    @GetMapping(value="/add_form")
    String add() {
        logger.info(">>>>All");
        return "/productAddForm";
    }
}