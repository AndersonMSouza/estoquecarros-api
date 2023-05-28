package com.andersonmendes.estoquecarros.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Pessoa;
import com.andersonmendes.estoquecarros.domain.repository.PessoaRepository;

@Service
public class CadastroPessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public void excluir(Long pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe pessoa cadastrada com o código %d", pessoaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Pessoa de código %d não pode ser removida, pois está em uso!", pessoaId));
		}
	}
}
