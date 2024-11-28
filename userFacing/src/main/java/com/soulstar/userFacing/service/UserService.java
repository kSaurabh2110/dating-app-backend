package com.soulstar.userFacing.service;

import com.soulstar.userFacing.model.User;
import com.soulstar.userFacing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String verifyOtp(User user) throws Exception {
        boolean otpVerified = false;
        // OTP verification logic (mocked here)
        if (Boolean.TRUE.equals(otpVerified)) {
            userRepository.saveUser(user);
            return "OTP verified, proceed to complete your profile...";
        }
        return "Invalid OTP...";
    }

    public String completeProfile(User userDetails) throws  Exception {
        // Complete user profile with DOB, gender, etc.
        User user = userRepository.findUserByPhoneNumber(userDetails.getPhoneNumber());
        if (user==null || Boolean.TRUE.equals(user.getProfileCompletedStatus())){
            return "User not found or profile already completed.";
        }
        userRepository.updateUserProfile(userDetails);
        return "Profile completed successfully.";

    }

}
