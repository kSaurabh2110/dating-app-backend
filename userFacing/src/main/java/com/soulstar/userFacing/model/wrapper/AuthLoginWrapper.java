package com.soulstar.userFacing.model.wrapper;

import com.soulstar.userFacing.model.request.LoginRequestDTO;
import com.soulstar.userFacing.model.response.LoginResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AuthLoginWrapper extends RequestDataWrapper {
    private LoginRequestDTO loginRequestDTO;
    private LoginResponseDTO loginResponseDTO;
}
