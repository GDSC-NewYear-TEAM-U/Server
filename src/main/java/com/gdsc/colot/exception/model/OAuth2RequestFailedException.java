package com.gdsc.colot.exception.model;

import com.gdsc.colot.exception.ErrorCode;

public class OAuth2RequestFailedException extends CustomException {

    public OAuth2RequestFailedException(ErrorCode error, String message) {
        super(error, message);
    }

}
