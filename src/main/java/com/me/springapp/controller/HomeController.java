package com.me.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

//@CrossOrigin(origins = "*")
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
        // return "redirect:/swagger-ui/index.html";
    }
}
