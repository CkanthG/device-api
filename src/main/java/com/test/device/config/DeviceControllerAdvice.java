package com.test.device.config;

import com.test.device.exception.DeviceException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DeviceControllerAdvice {

  @ExceptionHandler(DeviceException.class)
  public ProblemDetail handleDeviceException(DeviceException ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    problemDetail.setTitle("Device Exception");
    return problemDetail;
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    problemDetail.setTitle("Entity Not Found Exception");
    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    problemDetail.setTitle("INTERNAL SERVER ERROR");
    return problemDetail;
  }
}
