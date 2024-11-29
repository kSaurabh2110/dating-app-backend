package com.soulstar.userFacing.service;

import com.soulstar.userFacing.model.wrapper.RequestDataWrapper;

public abstract class AbstractService {
    protected abstract boolean validate(RequestDataWrapper requestDataWrapper) throws Exception;
    protected abstract void process(RequestDataWrapper requestDataWrapper) throws Exception;

    public void execute(RequestDataWrapper requestDataWrapper) throws Exception {
        if (requestDataWrapper == null) return;
        if (!validate(requestDataWrapper)) return;
        process(requestDataWrapper);
    }
}
