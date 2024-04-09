package IvanovVasil.OrderManagmentSystem.exceptions;

import IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads.ErrorsListResponseDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads.ErrorsResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
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
    if (e.getErrorList() != null) {
      List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
      return new ErrorsListResponseDTO(e.getMessage(), new Date(), errorsList);
    } else {
      return new ErrorsListResponseDTO(e.getMessage(), new Date(), new ArrayList<>());
    }
  }


  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleNotFound(NotFoundException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorsResponseDTO handleMethodArgumentNotValidException(BadRequestException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(NotContextException.class)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ErrorsResponseDTO handleNotContent(NotContextException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsResponseDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public ErrorsResponseDTO handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsResponseDTO handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorsResponseDTO handleAccesDenied(AccessDeniedException e) {
    return new ErrorsResponseDTO(e.getMessage(), new Date());
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

