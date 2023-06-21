package com.nexus.config;
import feign.RequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FeignHttpClientConfiguration {


    private static final Logger log = LoggerFactory.getLogger(FeignHttpClientConfiguration.class);

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate ->
        {
            requestTemplate.header("globalCorrelationId", MDC.get("correlationId"));
            if ( log.isInfoEnabled() )
                log.info("Invoking Feign Target {} with correlation id {}",requestTemplate.feignTarget().url(),MDC.get("correlationId"));
        };
    }
}
