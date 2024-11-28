package com.soulstar.userFacing.controller;

import com.soulstar.userFacing.model.User;
import com.soulstar.userFacing.repository.UserRepository;
import com.soulstar.userFacing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soulstar/user")
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody User user) throws Exception {
        return userService.verifyOtp(user);
    }

    @PostMapping("/complete-profile")
    public String completeProfile(@RequestBody User user) throws Exception {
        return userService.completeProfile(user);
    }
}
