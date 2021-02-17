package com.czareg.logging.filter;

import com.czareg.logging.service.LoggingService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private LoggingService loggingService;

    public LoggingFilter(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String requestBody = getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
        loggingService.logRequest(request, requestBody);

        byte[] contentAsByteArray = wrappedResponse.getContentAsByteArray();
        String responseBody = getContentAsString(contentAsByteArray, response.getCharacterEncoding());
        loggingService.logResponse(request, response, responseBody);

        wrappedResponse.copyBodyToResponse();
    }

    private String getContentAsString(byte[] contentAsByteArray, String charsetName) {
        if (contentAsByteArray == null || contentAsByteArray.length == 0) {
            return "";
        }
        try {
            return new String(contentAsByteArray, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }
}