package com.ozmenyavuz.dto;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message
) {}