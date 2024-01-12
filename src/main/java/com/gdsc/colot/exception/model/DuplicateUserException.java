package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class DuplicateUserException extends CustomException {

    public DuplicateUserException(ErrorCode error, String message) {
        super(error, message);
    }

}
