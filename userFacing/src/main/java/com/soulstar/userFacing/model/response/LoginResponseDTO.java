package com.soulstar.userFacing.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDTO {
    private Status status;
    private String jwtToken;
    private String phoneNumber;

    public enum Status{
        EXISTING_USER,
        NEW_USER,
        USER_REGISTERED
    }
}
