package com.soulstar.userFacing.config;

public class CorrelationIdGenerator {
    private static ThreadLocal<String> uuid = new ThreadLocal<>();
    private static ThreadLocal<Object>parametersPassed=new ThreadLocal<>();
    public static String getUuid() {
        return uuid.get();
    }
    public static String getUniqueRequestIdLogging(){
        return "UUID "+getUuid()+" ";
    }

    public static void setUuid(String uuid) {
        CorrelationIdGenerator.uuid.set(uuid);
    }
    public static Object getParametersPassed() { return parametersPassed.get(); }

    public static void setParametersPassed(Object parametersPassed) {
        CorrelationIdGenerator.parametersPassed.set(parametersPassed);
    }
    public static void destroyThreadLocal() {
        uuid.remove();
        parametersPassed.remove();
    }
}
