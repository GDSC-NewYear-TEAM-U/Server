package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode error, String message) {
        super(error, message);
    }

}
