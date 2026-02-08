package IvanovVasil.OrderManagmentSystem.exceptions;

import IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads.ErrorDetailDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads.ErrorsListResponseDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads.ErrorsResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.NotContextException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsListResponseDTO handleBadRequest(BadRequestException e) {
    List<ErrorDetailDTO> errorsList = new ArrayList<>();
    if (e.getErrorList() != null) {
      errorsList = e.getErrorList().stream().map(error -> new ErrorDetailDTO((((FieldError) error).getField()), error.getDefaultMessage())).toList();
    }
    return new ErrorsListResponseDTO(e.getMessage(), new Date(), errorsList);

  }


  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleNotFound(NotFoundException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ErrorsResponseDTO("Invalid data type, please follow the instructions in the form", new Date());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public ErrorsResponseDTO handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsResponseDTO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    return new ErrorsResponseDTO("Entered data type is not valid!", new Date());
  }


  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorsResponseDTO handleAnauthorized(UnauthorizedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ErrorsResponseDTO handleAnauthorized(HttpRequestMethodNotSupportedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }


  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorsResponseDTO handleGeneric(Exception e) {
    e.printStackTrace();
    return new ErrorsResponseDTO("we are sorry at the moment we have some internal problems, we are trying to resolve them", new Date());
  }

}

