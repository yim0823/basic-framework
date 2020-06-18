package com.stove.ipd.sample.api.advice;

import com.stove.ipd.sample.api.exception.ApplicationException;
import com.stove.ipd.sample.api.response.ApiResponseDto;
import com.stove.ipd.sample.utils.enums.RtCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.advice.ApiCommonAdvice
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@Order(value = 1)
@RestControllerAdvice // REST 요청에 대한 에러를 핸들링
public class ApiCommonAdvice {

  private static final Logger logger = LoggerFactory.getLogger(ApiCommonAdvice.class);

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = Exception.class)
  public ApiResponseDto<String> handleBaseException(Exception e) {
    logger.error("[{}] {}", RtCode.RT_INTERNAL_ERROR.getRtCode(), e);
    return ApiResponseDto.createException(RtCode.RT_INTERNAL_ERROR, e.getMessage());
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = MissingPathVariableException.class)
  public ApiResponseDto<String> handleBaseException(MissingPathVariableException e) {
    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_MISSING_PATH_VARIABLE, e.getMessage());
    logger.error("[{}] {}", RtCode.RT_MISSING_PATH_VARIABLE.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.OK)
  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ApiResponseDto<String> handleBaseException(ConstraintViolationException e) {
    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_WRONG_PARAMETER, "Wrong parameters.");
    logger.error("[{}] {}", RtCode.RT_WRONG_PARAMETER.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {ApplicationException.class})
  public ApiResponseDto<String> handleValidException(ApplicationException e) {
    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_NOT_SUPPORT, e.getMessage());
    logger.error("[{}] {}", RtCode.RT_NOT_SUPPORT.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {ConversionFailedException.class})
  public ApiResponseDto<String> handleValidException(ConversionFailedException e) {
    String errorMessage =
        String.format(
            " %s Conversion failed for Object of a request.",
            e.getTargetType().getType().getSimpleName());

    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_WRONG_PARAMETER, errorMessage);
    logger.error("[{}] {}", RtCode.RT_WRONG_PARAMETER.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ApiResponseDto<String> handleValidException(MethodArgumentNotValidException e) {

    BindingResult result = e.getBindingResult();
    String errorMessage =
        String.format(
            " %s Validation failed for Object of a request.",
            Objects.requireNonNull(result.getFieldError()).getField());

    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_VALIDATION_FAILURE, errorMessage);
    logger.error("[{}] {}", RtCode.RT_VALIDATION_FAILURE.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {BindException.class})
  public ApiResponseDto<ErrorMessageCollection> handleValidException(BindException e) {

    ApiResponseDto<ErrorMessageCollection> exception =
        ApiResponseDto.createException(
            RtCode.RT_WRONG_PARAMETER,
            new ErrorMessageCollection(
                e.getBindingResult().getFieldErrors(), e.getBindingResult().getGlobalErrors()));
    logger.error("[{}] {}", RtCode.RT_BINDING_FAILURE.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {NumberFormatException.class})
  public ApiResponseDto<String> handleValidException(NumberFormatException e) {
    ApiResponseDto<String> exception =
        ApiResponseDto.createException(RtCode.RT_WRONG_PARAMETER, "Wrong parameters.");
    logger.error("[{}] {}", RtCode.RT_WRONG_PARAMETER.getRtCode(), exception);

    return exception;
  }

  @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Permission Denied")
  @ExceptionHandler(value = HttpClientErrorException.class)
  public ApiResponseDto<String> handleValidException() {
    ApiResponseDto<String> exception =
        ApiResponseDto.createException(
            RtCode.RT_AUTHENTICATION_FAILURE,
            "Could not authenticate forbidden. You need a ApiKey");
    logger.error("[{}] {}", RtCode.RT_AUTHENTICATION_FAILURE.getRtCode(), exception);

    return exception;
  }
}
