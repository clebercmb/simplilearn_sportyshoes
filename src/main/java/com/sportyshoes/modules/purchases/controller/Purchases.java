package com.sportyshoes.modules.purchases.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/purchases")
public class Purchases {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    String all() {
        logger.info(">>>>All");
        return "purchases";
    }
}
