package com.adventure.assignment.userservice.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        throw new RuntimeException(httpResponse.getBody().toString());
    }
}

