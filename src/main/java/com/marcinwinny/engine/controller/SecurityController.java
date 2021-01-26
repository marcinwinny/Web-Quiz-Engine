package com.marcinwinny.engine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecurityController {

    @GetMapping(value = "/username")
    @ResponseBody
    public String currentUserName(Principal principal){
        return principal.getName();
    }
}
