package com.soulstar.userFacing.Utils;

import com.soulstar.userFacing.model.response.Response;
import org.springframework.http.HttpStatus;

public class ResponseUtils {
    public static Response errorResponse(Integer statusCode, String status, String error){
        Response responseDto=new Response<>();
        responseDto.setError(error);
        responseDto.setStatusCode(statusCode);
        responseDto.setStatus(status);
        return responseDto;
    }

    public static <T> void successResponse(Response<T> responseDto, T result){
        responseDto.setStatusCode(HttpStatus.OK.value());
        responseDto.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDto.setResult(result);
    }
}
