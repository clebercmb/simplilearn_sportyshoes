package com.sportyshoes.modules.logout.controller;

import com.sportyshoes.share.exceptions.SportyShoesException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
