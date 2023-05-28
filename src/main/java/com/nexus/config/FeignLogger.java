package com.nexus.config;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;

@Configuration


public class FeignLogger extends Logger {

    private static final org.slf4j.Logger FEIGN_LOGGER = LoggerFactory.getLogger(FeignLogger.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String RESPONSE_LOG_MESSAGE = "<--- %s %s %s (%sms) Response body : %s";
    private static final String REQUEST_LOG_MESSAGE = "---> %s %s Request body: %s";

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (FEIGN_LOGGER.isInfoEnabled()) {
            FEIGN_LOGGER.info(format(configKey, format, args));
        }
    }

    protected void debug(String configKey, String format, Object... args) {
        if (FEIGN_LOGGER.isDebugEnabled()) {
            FEIGN_LOGGER.debug(format(configKey, format, args));
        }
    }

    protected void error(String configKey, String format, Object... args) {
        if (FEIGN_LOGGER.isErrorEnabled()) {
            FEIGN_LOGGER.error(format(configKey, format, args));
        }
    }

    protected String format(String configKey, String format, Object... args) {
        format = methodTag(configKey) + format;
        return String.format(format, args);
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {

        log(configKey, "---> %s %s", request.httpMethod().name(), request.url());

        log(configKey, "---> %s %s", "correlationId", request.headers().get("X-Correlation-Id"));
        log(configKey, "---> %s %s %s %s", "X-Correlation-Id", request.headers().get("X-Correlation-Id"), request.httpMethod().name(), request.url());
        if (request.body() != null && request.body().length > 0) {

            String requestBody = new String(request.body());

            if (isJSONValid(requestBody)) {
                JsonElement jsonBody = new JsonParser().parse(requestBody);
                requestBody = GSON.toJson(jsonBody);
            }

            debug(configKey, REQUEST_LOG_MESSAGE, request.httpMethod().name(), request.url(), requestBody);
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
            throws IOException {

        int status = response.status();

        if (isNotErrorStatus(status)) {
            log(configKey, "<--- %s %s %s (%sms) ", response.request().httpMethod().name(), response.request().url(),
                    response.status(), elapsedTime);
        } else {
            error(configKey, "<--- %s %s %s (%sms) ", response.request().httpMethod().name(), response.request().url(),
                    response.status(), elapsedTime);
        }

        if (response.body() != null && status != 204 && status != 205) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            String responseBody = Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data");

            if (isJSONValid(responseBody)) {
                JsonElement jsonBody = new JsonParser().parse(responseBody);
                responseBody = GSON.toJson(jsonBody);
            }

            logResponseBody(response, configKey, responseBody, elapsedTime);

            return response.toBuilder().body(bodyData).build();

        }
        return response;
    }

    private void logResponseBody(Response response, String configKey, String responseBody, long elapsedTime) {

        int status = response.status();

        if (Strings.isNotBlank(responseBody)) {
            if (isNotErrorStatus(status)) {
                debug(configKey, RESPONSE_LOG_MESSAGE, response.request().httpMethod().name(), response.request().url(),
                        response.status(), elapsedTime, responseBody);
            } else {
                error(configKey, RESPONSE_LOG_MESSAGE, response.request().httpMethod().name(), response.request().url(),
                        response.status(), elapsedTime, responseBody);
            }
        }
    }

    private boolean isNotErrorStatus(int status) {
        return status < 400;
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Level.BASIC;
    }

    private boolean isJSONValid(String jsonInString) {
        try {
            ObjectMapper mapper = getMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }


}