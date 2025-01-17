package com.ing.exception;

import com.ing.domain.BaseRestException;
import org.springframework.http.HttpStatus;

public class PreconditionFailedException extends BaseRestException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.PRECONDITION_FAILED;
    private static final String ERROR_BODY_MESSAGE = "Precondition failed. ";
    private final String displayMessage;

    public PreconditionFailedException(String message) {
        super(message);
        this.displayMessage = ERROR_BODY_MESSAGE + message;
    }

    public PreconditionFailedException(Throwable cause, String message, String errorInfo) {
        super(cause, message);
        this.displayMessage = ERROR_BODY_MESSAGE + errorInfo;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

    @Override
    public String getDisplayMessage() {
        return displayMessage;
    }
}
