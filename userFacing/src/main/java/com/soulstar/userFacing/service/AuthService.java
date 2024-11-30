package com.soulstar.userFacing.service;


import com.google.firebase.auth.FirebaseToken;
import com.soulstar.userFacing.model.User;
import com.soulstar.userFacing.model.request.LoginRequestDTO;
import com.soulstar.userFacing.model.response.LoginResponseDTO;
import com.soulstar.userFacing.model.wrapper.AuthLoginWrapper;
import com.soulstar.userFacing.model.wrapper.RequestDataWrapper;
import com.soulstar.userFacing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends AbstractService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private JWTService jwtService;

    @Override
    protected boolean validate(RequestDataWrapper requestDataWrapper) throws Exception {
        return true;
    }

    @Override
    protected void process(RequestDataWrapper requestDataWrapper) throws Exception {
        switch (requestDataWrapper.getRequestAPIPath()){
            case LOGIN :{
                AuthLoginWrapper authLoginWrapper = (AuthLoginWrapper) requestDataWrapper;
                processLogin(authLoginWrapper);
                break;
            }
        }
    }

    private void processLogin(AuthLoginWrapper authLoginWrapper) {
        LoginRequestDTO loginRequestDTO = authLoginWrapper.getLoginRequestDTO();
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        authLoginWrapper.setLoginResponseDTO(loginResponseDTO);

        String idToken = loginRequestDTO.getFirebaseIdToken();
        try {
            // Verify Firebase ID Token
            FirebaseToken decodedToken = firebaseService.verifyIdToken(idToken);
            // Get UID from token
            String uid = decodedToken.getUid();
            // Get phone number from token claims
            String phoneNumber = (String) decodedToken.getClaims().get("phone_number");

            // Generate JWT for existing user
            String jwtToken = jwtService.generateToken(phoneNumber);
            // Check if user exists
            User user = userRepository.findUserByUid(uid);
            if (user == null) {
                loginResponseDTO.setStatus(LoginResponseDTO.Status.NEW_USER);
                loginResponseDTO.setPhoneNumber(phoneNumber);
                loginResponseDTO.setJwtToken(jwtToken);
                return;
            }

            loginResponseDTO.setStatus(LoginResponseDTO.Status.EXISTING_USER);
            loginResponseDTO.setJwtToken(jwtToken);
            return;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
