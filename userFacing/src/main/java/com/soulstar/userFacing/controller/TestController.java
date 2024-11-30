package com.soulstar.userFacing.controller;

import com.soulstar.userFacing.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    JWTService jwtService;
    @PostMapping("/test")
    public Object test(@RequestBody String s){
        String jwtToken = jwtService.generateToken(s);
        return jwtToken;
    }

    @PostMapping("/vali")
    public Object vali(@RequestBody String s){
        return jwtService.validateToken(s);
    }
}
