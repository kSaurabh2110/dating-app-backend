package com.soulstar.userFacing.model.wrapper;

import com.soulstar.userFacing.enums.RequestAPIPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public abstract class RequestDataWrapper {
    private RequestAPIPath requestAPIPath;
}
