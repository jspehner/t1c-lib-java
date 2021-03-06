package com.t1t.t1c.exceptions;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class CertificateOrderingException extends AbstractRuntimeException {

    public CertificateOrderingException(String message) {
        super(message);
    }

    @Override
    public Integer getHttpCode() {
        return null;
    }

    @Override
    public Integer getErrorCode() {
        return ErrorCodes.CERTIFICATE_ORDERING_ERROR;
    }

    @Override
    public String getMoreInfoUrl() {
        return ErrorCodes.CERTIFICATE_ORDERING_ERROR_INFO;
    }
}