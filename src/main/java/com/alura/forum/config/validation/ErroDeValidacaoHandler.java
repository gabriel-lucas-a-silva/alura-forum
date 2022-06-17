package com.alura.forum.config.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alura.forum.dto.response.ErroDeFormularioResponseDto;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

  @Autowired
  private MessageSource messageSource;
  
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public List<ErroDeFormularioResponseDto> handle(MethodArgumentNotValidException exception) {
    List<ErroDeFormularioResponseDto> dto = new ArrayList<>();
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

    fieldErrors.forEach(e -> {
      String messagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
      ErroDeFormularioResponseDto erro = new ErroDeFormularioResponseDto(e.getField(), messagem);
      dto.add(erro);
    });

    return dto;
  }

}
 