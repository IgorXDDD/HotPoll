package com.pik.hotpoll.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/login")
public class LoginController {
    @GetMapping("")
    public ModelAndView frontend(ModelMap model) {
        model.addAttribute("attribute", "frontend");
        return new ModelAndView("forward:/frontend/login.html", model);
    }
}
