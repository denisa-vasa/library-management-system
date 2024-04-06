package com.gisdev.dea.util.general;

import org.modelmapper.ModelMapper;

public class JsonUtil {

    private final static ModelMapper mapper = new ModelMapper();

    private JsonUtil() {}

    public static ModelMapper getMapper() {
        return mapper;
    }

    public static <T> T map(Object payload, Class<T> destinationClass) {
        return getMapper().map(payload, destinationClass);
    }
}
