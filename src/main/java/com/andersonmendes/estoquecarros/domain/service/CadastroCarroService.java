package com.andersonmendes.estoquecarros.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Carro;
import com.andersonmendes.estoquecarros.domain.repository.CarroRepository;

@Service
public class CadastroCarroService {

	@Autowired
	private CarroRepository carroRepository;
	
	public Carro salvar(Carro carro) {
		return carroRepository.save(carro);
	}
	
	public void excluir(Long carroId) {
		try {
			carroRepository.deleteById(carroId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe carro cadastrado com o cógigo %d", carroId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Carro de código %d não pode removido, pois está em uso!", carroId));
		}
	}
	
}
