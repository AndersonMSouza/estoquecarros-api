package com.andersonmendes.estoquecarros.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Carro;
import com.andersonmendes.estoquecarros.domain.model.Loja;
import com.andersonmendes.estoquecarros.domain.repository.CarroRepository;
import com.andersonmendes.estoquecarros.domain.repository.LojaRepository;

@Service
public class CadastroCarroService {

	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private LojaRepository lojaRepository;
	
	public Carro salvar(Carro carro) {
		Long lojaId = carro.getLoja().getId();
		Optional<Loja> loja = lojaRepository.findById(lojaId);
		
		if (loja == null) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de loja com o codigo %d.", lojaId));
		}
		
		carro.setLoja(loja.get());
		
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
