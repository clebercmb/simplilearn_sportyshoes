package com.sportyshoes.modules.home;

import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.products.services.ReadAllProductService;
import com.sportyshoes.modules.products.services.ReadProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReadAllProductService readAllProductService;

    @Autowired
    public HomeController(ReadAllProductService readAllProductService) {
        this.readAllProductService = readAllProductService;
    }

    @GetMapping
    public String welcome(Model model) {
        logger.info(">>>>>>Welcome");

        List<ProductDto> productDtoList =  readAllProductService.execute();

        model.addAttribute("products", productDtoList);

        return "home";
    }
}
