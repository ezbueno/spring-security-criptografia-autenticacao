package com.buenoezandro.encriptar.senha.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.buenoezandro.encriptar.senha.service.exception.UsuarioNaoAutorizadoException;
import com.buenoezandro.encriptar.senha.service.exception.UsuarioNaoEncontradoException;
import com.buenoezandro.encriptar.senha.utils.MessageUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private final MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

			campos.add(new Problema.Campo(nome, mensagem));
		});

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(MessageUtils.TITULO_MSG);
		problema.setCampos(campos);

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	private ResponseEntity<Object> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(e.getMessage());

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(UsuarioNaoAutorizadoException.class)
	private ResponseEntity<Object> handleUsuarioNaoAutorizado(UsuarioNaoAutorizadoException e, WebRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;

		var problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(e.getMessage());

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

}