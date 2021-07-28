package com.sportyshoes.modules.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping
    public String welcome() {
        logger.info(">>>>>>Welcome");

        return "home";
    }
}
