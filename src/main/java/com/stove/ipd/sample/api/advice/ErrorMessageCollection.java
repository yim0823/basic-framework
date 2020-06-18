package com.stove.ipd.sample.api.advice;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Project : sample
 * Class : com.stove.dcos.sample.api.advice.ErrorMessageCollection
 * Version : 2020-03-18 v0.0.1
 * Created by thyim on 2020-03-18.
 * *** 저작권 주의 ***
 */
@ToString
@Getter
public class ErrorMessageCollection {

  private List<ErrorMessage> errors = Lists.newArrayList();

  ErrorMessageCollection(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
    errors.addAll(getFieldErrors(fieldErrors));
    errors.addAll(getObjectErrors(globalErrors));
  }

  public ErrorMessageCollection(List<ErrorMessage> errors) {
    this.errors = errors;
  }

  private List<ErrorMessage> getFieldErrors(List<FieldError> fieldErrors) {
    return fieldErrors.stream()
        .map(f -> ErrorMessage.builder().field(f.getField()).message(f.getDefaultMessage()).build())
        .collect(Collectors.toList());
  }

  private List<ErrorMessage> getObjectErrors(List<ObjectError> globalErrors) {
    return globalErrors.stream()
        .map(
            o ->
                ErrorMessage.builder()
                    .field(o.getObjectName())
                    .message(o.getDefaultMessage())
                    .build())
        .collect(Collectors.toList());
  }
}
