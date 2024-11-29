package com.soulstar.userFacing.Utils;

import com.google.gson.Gson;

public class JsonAndGsonUtils {
    public JsonAndGsonUtils() {
    }

    private static Gson gson = new Gson();
    public static Gson getGson() {
        return gson;
    }

}
