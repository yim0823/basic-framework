package com.stove.ipd.sample.utils.enums;

import org.springframework.http.HttpStatus;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.utils.enums.RtCode
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
public enum RtCode {

    // Common Return Code;
    RT_EXECUTED(1, "Executed", HttpStatus.ACCEPTED),
    RT_SUCCESS(0, "Success", HttpStatus.OK),
    RT_INTERNAL_ERROR(-1, "Internal error", HttpStatus.INTERNAL_SERVER_ERROR),
    RT_WRONG_PARAMETER(-2, "Wrong parameter", HttpStatus.BAD_REQUEST),
    RT_WRONG_STATUS(-3, "Wrong status", HttpStatus.BAD_REQUEST),
    RT_MISSING_PATH_VARIABLE(-4, "Missing path variable", HttpStatus.BAD_REQUEST),
    RT_PROPAGATION_ERROR(-5, "Propagation error", HttpStatus.BAD_REQUEST),
    RT_VALIDATION_FAILURE(-6, "Validation error", HttpStatus.BAD_REQUEST),
    RT_BINDING_FAILURE(-7, "Binding error", HttpStatus.BAD_REQUEST),
    RT_PARSING_ERROR(-8, "Parsing Error", HttpStatus.BAD_REQUEST),
    RT_AUTHENTICATION_FAILURE(-9, "Authentication failure", HttpStatus.FORBIDDEN),
    RT_NOT_EXIST(-10, "Not exist", HttpStatus.GONE),
    RT_DUPLICATED(-11, "Duplicated", HttpStatus.BAD_REQUEST),
    RT_NOT_SUPPORT(-12, "Not support", HttpStatus.NOT_FOUND),
    RT_FAILURE(-99, "Failure", HttpStatus.INTERNAL_SERVER_ERROR),

    //  Return Code [-3001XX]
    RT_PROB_INTERNAL_ERROR(-300100, "Deploy Legacy System Error", HttpStatus.INTERNAL_SERVER_ERROR),
    RT_PROB_CONNECTION_ERROR(-300101, "Can not connect prob-manager", HttpStatus.INTERNAL_SERVER_ERROR),
    RT_PROB_PARSING_ERROR(-300102, "Prob Legacy System Parsing Error", HttpStatus.INTERNAL_SERVER_ERROR),
    RT_PROB_PROPAGATION_ERROR(300103, "Prob Propagation error", HttpStatus.BAD_REQUEST),

    // UNKNOWN Error Return Code
    RT_UNKNOWN(-999999, "Unknown error. contact to admin", HttpStatus.INTERNAL_SERVER_ERROR);

    private final long rtCode;
    private final String rtMessage;
    private final HttpStatus httpStatus;

    private RtCode(long rtCode, String rtMessage, HttpStatus httpStatus) {
        this.rtCode = rtCode;
        this.rtMessage = rtMessage;
        this.httpStatus = httpStatus;
    }

    public long getRtCode() {
        return this.rtCode;
    }

    public String getRtMessage() {
        return this.rtMessage;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(this.rtCode).append(ApplicationConstants.DELIMITER_COLON);
        sb.append("[").append(this.rtMessage).append("] ");
        //sb.append(this.httpStatus);

        return sb.toString();
    }
}
