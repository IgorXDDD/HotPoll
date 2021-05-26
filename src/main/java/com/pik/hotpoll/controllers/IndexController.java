package com.pik.hotpoll.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class IndexController {
    @GetMapping("")
    public ModelAndView frontend(ModelMap model) {
        model.addAttribute("attribute", "frontend");
        return new ModelAndView("forward:/frontend/index.html", model);
    }

    @GetMapping("icon")
    public ModelAndView icon(ModelMap model) {
        model.addAttribute("attribute", "frontend");
        return new ModelAndView("forward:/frontend/google_button.png", model);
    }
}
