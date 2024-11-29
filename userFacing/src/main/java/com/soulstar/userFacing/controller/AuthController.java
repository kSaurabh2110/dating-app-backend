package com.soulstar.userFacing.controller;

import com.soulstar.userFacing.Utils.ResponseUtils;
import com.soulstar.userFacing.config.CorrelationIdGenerator;
import com.soulstar.userFacing.enums.RequestAPIPath;
import com.soulstar.userFacing.model.request.LoginRequestDTO;
import com.soulstar.userFacing.model.response.LoginResponseDTO;
import com.soulstar.userFacing.model.wrapper.AuthLoginWrapper;
import com.soulstar.userFacing.model.response.Response;
import com.soulstar.userFacing.service.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soulstar/auth")
public class AuthController {
    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Response<LoginResponseDTO> verifyOtp(@RequestBody LoginRequestDTO loginRequestDTO){
        Response<LoginResponseDTO> response = new Response<>();
        try {
            AuthLoginWrapper authLoginWrapper = new AuthLoginWrapper();
            authLoginWrapper.setRequestAPIPath(RequestAPIPath.LOGIN);
            authLoginWrapper.setLoginRequestDTO(loginRequestDTO);
            authService.execute(authLoginWrapper);
            ResponseUtils.successResponse(response, authLoginWrapper.getLoginResponseDTO());
        }catch (Exception e){
            response = ResponseUtils.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),e.getMessage());
            logger.error(CorrelationIdGenerator.getUniqueRequestIdLogging()+"Error processing response for send-otp");
        }
        return response;
    }
}
