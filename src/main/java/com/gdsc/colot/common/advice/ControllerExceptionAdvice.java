package com.gdsc.colot.common.advice;

import com.gdsc.colot.common.dto.BaseResponse;
import com.gdsc.colot.exception.ErrorCode;
import com.gdsc.colot.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@Component
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    /**
     * 400 BAD_REQUEST
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected BaseResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        log.error("Validation error for field {}: {}", fieldError.getField(), fieldError.getDefaultMessage());
        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION, String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected BaseResponse handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        log.error("Missing Request Header: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION, String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION.getMessage(), e.getHeaderName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected BaseResponse handleMissingRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("Missing Request Parameter: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION, String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION.getMessage(), e.getParameterName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected BaseResponse handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("Http Request Method Not Supported: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.REQUEST_METHOD_VALIDATION_EXCEPTION, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    protected BaseResponse badCredentialsException(final BadCredentialsException e) {
        log.error("Bad Credentials: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.AUTHORIZE_FAILED_EXCEPTION, ErrorCode.AUTHORIZE_FAILED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public BaseResponse fileSizeLimitExceeded(final MaxUploadSizeExceededException e) {
        log.error("File Size Limit Exceeded: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.MAX_UPLOAD_SIZE_EXCEED_EXCEPTION, e.getMessage());
    }

    /**
     * 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected BaseResponse handleException(final Exception e, final HttpServletRequest request) throws IOException {
        log.error("Internal Server Error: {}", e.getMessage(), e);
        return BaseResponse.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    /**
     * custom error
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<BaseResponse> handleGroomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getHttpStatus())
                .body(BaseResponse.error(e.getErrorCode(), e.getMessage()));
    }
}


