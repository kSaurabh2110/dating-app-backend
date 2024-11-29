package com.soulstar.userFacing.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<V> {
    private Integer statusCode;
    private String status;
    private String error;
    private V result;
}
