package com.sportyshoes.modules.login.controller;


import com.sportyshoes.modules.login.services.ValidateLoginService;
import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.modules.users.entity.UserType;
import com.sportyshoes.modules.users.services.CreateUserService;
import com.sportyshoes.modules.users.services.ReadUserByEmailService;
import com.sportyshoes.share.exceptions.SportyShoesException;
import com.sportyshoes.share.exceptions.SportyShoesResourceAlreadyExistException;
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
        modelAndView.addObject("is_sign_in", true);
        return modelAndView;
    }

    @RequestMapping(path = "/sign_up", method = RequestMethod.POST)
    public String signUp(UserDto userDto, Model model) throws SportyShoesException  {
        try {
            userDto.setUserType(UserType.USER);
            UserDto userDtoCreated = createUserService.execute(userDto);
        } catch (SportyShoesResourceAlreadyExistException e)  {
            model.addAttribute("login_error_sign_up", "Email already used. Try another one." );
            model.addAttribute("is_sign_in", false);
            return "login";
        }
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
            session.setAttribute("userId", user.getId());
            session.setAttribute("user", user);

            return "redirect:/";
        }
        model.addAttribute("is_sign_in", true);
        model.addAttribute("login_error", "Email or Password invalid. Try again" );
        return "login";
    }

}
