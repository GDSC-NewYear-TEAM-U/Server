package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class OAuth2NotSupportException extends CustomException {
    public OAuth2NotSupportException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
