package com.me.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins = "*")
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}
