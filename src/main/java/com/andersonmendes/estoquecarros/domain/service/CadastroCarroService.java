package com.andersonmendes.estoquecarros.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Carro;
import com.andersonmendes.estoquecarros.domain.model.Loja;
import com.andersonmendes.estoquecarros.domain.repository.CarroRepository;
import com.andersonmendes.estoquecarros.domain.repository.LojaRepository;

@Service
public class CadastroCarroService {
	
	private static final String MSG_CARRO_EM_USO 
	= "Carro de código %d não pode ser removido, pois está em uso";

	private static final String MSG_CARRO_NAO_ENCONTRADO 
	= "Não existe um cadastro de carro com código %d";

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
				String.format(MSG_CARRO_NAO_ENCONTRADO, carroId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CARRO_EM_USO, carroId));
		}
	}
	
	public Carro buscarOuFalhar(@PathVariable Long carroId) {
		return carroRepository.findById(carroId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format(MSG_CARRO_NAO_ENCONTRADO, carroId)));
	}
	
}
