package com.sportyshoes.modules.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String all() {
        logger.info(">>>>All");
        return "users";
    }

    @GetMapping(value="/add_form")
    public String add() {
        return "usersAddForm";
    }

}
