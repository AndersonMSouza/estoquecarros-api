package com.andersonmendes.estoquecarros.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Loja;
import com.andersonmendes.estoquecarros.domain.repository.LojaRepository;

@Service
public class CadastroLojaService {

	@Autowired
	private LojaRepository lojaRepository;
	
	public Loja salvar(Loja loja) {
		return lojaRepository.save(loja);
	}
	
	public void excluir(Long lojaId) {
		try {
			lojaRepository.deleteById(lojaId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe loja cadastrada com o código %d", lojaId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Loja de código %d não pode ser removida, pois está em uso!", lojaId));
		}
	}
	
	public Loja buscarOuFalhar(@PathVariable Long lojaId) {
		return lojaRepository.findById(lojaId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe loja cadastrada com o código %d", lojaId)));
	}
	
}
