package com.grcp.resilience.config;

import feign.Logger;
import feign.Request;

public class FeignLoggerTestConfiguration extends Logger {

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        System.out.println("->>> from request");
        super.logRequest(configKey, logLevel, request);
    }

    @Override
    protected void log(String s, String s1, Object... objects) {
        System.out.println(String.format(s, s1, objects));
    }
}
