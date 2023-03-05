package com.apress.todoreactor.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class TodoMetricInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(TodoMetricInterceptor.class);
    private MeterRegistry registry;
    private String URI, pathKey, METHOD;

    public TodoMetricInterceptor(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        URI = request.getRequestURI();
        METHOD = request.getMethod();
        if (!URI.contains("prometheus")) {
            logger.info(" >>> PATH: {}", URI);
            logger.info(" >>> METHOD: {}", METHOD);
            pathKey = "api_".concat(METHOD.toLowerCase()).concat(URI.toLowerCase().replace("/","_"));
            this.registry.counter(pathKey).increment();
            logger.info(" >>>> the number of calls {} ", this.registry.counter(pathKey).count());
        }
    }
}
