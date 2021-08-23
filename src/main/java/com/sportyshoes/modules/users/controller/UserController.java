package com.sportyshoes.modules.users.controller;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.services.ReadAllUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadAllUserService readAllUserService;

    @Autowired
    public UserController(ReadAllUserService readAllUserService) {
        this.readAllUserService = readAllUserService;
    }

    @GetMapping
    public ModelAndView all() {
        logger.info(">>>>All");

        ModelAndView modelAndView = new ModelAndView("users");

        List<UserDto> userDtoList = readAllUserService.execute();
        modelAndView.addObject("users", userDtoList);

        return modelAndView;
    }

    @GetMapping(value="/add_form")
    public String add() {
        return "usersAddForm";
    }

}
