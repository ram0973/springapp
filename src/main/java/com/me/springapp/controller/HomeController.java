package com.me.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//@CrossOrigin(origins = "*")
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello, " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        // return "redirect:/swagger-ui/index.html";
    }
}
