package com.sportyshoes.modules.logout.controller;

import com.sportyshoes.modules.users.dto.UserDto;
import com.sportyshoes.share.SportyShoesException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

    @GetMapping
    public String signIn(HttpServletRequest request) throws SportyShoesException {

        request.getSession().removeAttribute("email");
        request.getSession().invalidate();


        return "redirect:/login";
    }

}
