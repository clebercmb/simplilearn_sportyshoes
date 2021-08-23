package com.sportyshoes.modules.login.controller;


import com.sportyshoes.modules.login.services.ValidateLoginService;
import com.sportyshoes.modules.products.dto.ProductDto;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.User;
import com.sportyshoes.modules.users.repository.UserRepository;
import com.sportyshoes.modules.users.services.CreateUserService;
import com.sportyshoes.modules.users.services.ReadUserByEmailService;
import com.sportyshoes.modules.users.services.ReadUserService;
import com.sportyshoes.share.SportyShoesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CreateUserService createUserService;
    private final ValidateLoginService validateLoginService;
    private final ReadUserByEmailService readUserByEmailService;


    @Autowired
    public LoginController(CreateUserService createUserService,
                           ValidateLoginService validateLoginService,
                           ReadUserByEmailService readUserByEmailService) {
        this.createUserService = createUserService;
        this.validateLoginService = validateLoginService;
        this.readUserByEmailService = readUserByEmailService;
    }

    @GetMapping
    private ModelAndView all() {
        logger.info(">>>>All");

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("user", new UserDto());
        modelAndView.addObject("login_error", "" );
        return modelAndView;
    }

    @RequestMapping(path = "/sign_up", method = RequestMethod.POST)
    public String signUp(UserDto userDto) throws SportyShoesException  {

        UserDto userDtoCreated =  createUserService.execute(userDto);

        return "redirect:/login";
    }

    @RequestMapping(path = "/sign_in", method = RequestMethod.POST)
    public String signIn(UserDto userDto, Model model, HttpSession session) throws SportyShoesException  {

        boolean hasValidLogin =  validateLoginService.execute(userDto);

        if( hasValidLogin ) {


            Optional<UserDto> userDtoOptional = readUserByEmailService.execute(userDto.getEmail());
            assert userDtoOptional.isPresent();
            UserDto user = userDtoOptional.get();

            session.setAttribute("email", user.getEmail());
            session.setAttribute("userType", user.getUserType().userType);

            return "redirect:/";
        }

        model.addAttribute("login_error", "Email or Password invalid. Try again" );
        return "login";
    }



}
