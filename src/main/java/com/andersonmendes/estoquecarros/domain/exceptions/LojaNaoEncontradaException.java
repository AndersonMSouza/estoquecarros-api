package com.andersonmendes.estoquecarros.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LojaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public LojaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public LojaNaoEncontradaException(Long lojaId) {
		this(String.format("Não existe um cadastro de loja com código %d", lojaId));
	}
	
}