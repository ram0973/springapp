package com.me.springapp.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}
