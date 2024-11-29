package com.soulstar.userFacing.config;

public class CorrelationIdGenerator {
    private static ThreadLocal<String> uuid = new ThreadLocal<>();
    public static String getUuid() {
        return uuid.get();
    }
    public static String getUniqueRequestIdLogging(){
        return "UUID "+getUuid()+" ";
    }

    public static void setUuid(String uuid) {
        CorrelationIdGenerator.uuid.set(uuid);
    }
}
