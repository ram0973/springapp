package com.me.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/")
@Controller
public class HomeController {
    public String home() {
        return "redirect:/swagger-ui";
    }
}
