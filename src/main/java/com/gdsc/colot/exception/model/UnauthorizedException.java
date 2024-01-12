package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode error, String message) {
        super(error, message);
    }
}
