package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode error, String message) {
        super(error, message);
    }

}
