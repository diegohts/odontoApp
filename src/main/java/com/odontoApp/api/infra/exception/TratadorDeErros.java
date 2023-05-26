package com.odontoApp.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;
import com.odontoApp.api.domain.ValidacaoException;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

	private record DadosErroValidacao(String campo, String mensagem){
		public DadosErroValidacao(FieldError fieldError){
			this(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Void> tratarErro404(){
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException exception){
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException exception){
		var erros = exception.getFieldErrors();

		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
}
