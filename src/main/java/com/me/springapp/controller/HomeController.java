package com.me.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

//TODO: check this
@CrossOrigin(origins = "*")
@RequestMapping("/")
@Controller
public class HomeController {
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}
