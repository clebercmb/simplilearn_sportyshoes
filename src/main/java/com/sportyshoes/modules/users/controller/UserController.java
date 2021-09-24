package com.sportyshoes.modules.users.controller;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.services.DeleteUserService;
import com.sportyshoes.modules.users.services.ReadAllUserService;
import com.sportyshoes.modules.users.services.ReadUserService;
import com.sportyshoes.modules.users.services.UpdateUserService;
import com.sportyshoes.share.exceptions.SportyShoesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReadAllUserService readAllUserService;
    private final ReadUserService readUserService;
    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;

    @Autowired
    public UserController(ReadAllUserService readAllUserService, ReadUserService readUserService, UpdateUserService updateUserService, DeleteUserService deleteUserService) {
        this.readAllUserService = readAllUserService;
        this.readUserService = readUserService;
        this.updateUserService = updateUserService;
        this.deleteUserService = deleteUserService;
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

    @RequestMapping(path = {"/{id}/edit"})
    public ModelAndView read(@PathVariable Optional<Long> id) {

        ModelAndView modelAndView = new ModelAndView("usersChangeForm");

        if(id.isPresent()) {
            Optional<UserDto> userDtoOptional = readUserService.execute(id.get());
            modelAndView.addObject("update", true);
            if(userDtoOptional.isPresent()) {
                modelAndView.addObject("user", userDtoOptional.get());
            } else {
                modelAndView.addObject("user", UserDto.builder().id(-1L).build());
            }
        } else {
            modelAndView.addObject("create", true);
        }
        return modelAndView;
    }


    @RequestMapping(path = {"/{id}/save"})
    public String save(@PathVariable Optional<Long> id, UserDto userDto) throws SportyShoesException {

        updateUserService.execute(userDto);

        return "redirect:/users";
    }

    @RequestMapping(path = {"/{id}/delete"})
    public String delete(@PathVariable Optional<Long> id) throws SportyShoesException {

        id.ifPresent(deleteUserService::execute);

        return "redirect:/users";
    }

}
