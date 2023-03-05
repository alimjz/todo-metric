package com.apress.todoreactor.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class MetricConfig {

    @Bean
    public MappedInterceptor metricInterceptor(MeterRegistry registry) {
        return new MappedInterceptor(new String[]{"/**"}, new TodoMetricInterceptor(registry));
    }
}
