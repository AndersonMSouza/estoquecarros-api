package com.andersonmendes.estoquecarros.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.estoquecarros.domain.model.Carro;
import com.andersonmendes.estoquecarros.domain.repository.CarroRepository;
import com.andersonmendes.estoquecarros.domain.service.CadastroCarroService;

@RestController
@RequestMapping("/carros")
public class CarroController {

	@Autowired
	private CarroRepository carroRepository;
	
	@Autowired
	private CadastroCarroService cadastroCarroService;
	
	@GetMapping
	public List<Carro> listar() {
		return carroRepository.findAll();
	}

	@GetMapping("/{carroId}")
	public Carro buscar(@PathVariable Long carroId) {
		return cadastroCarroService.buscarOuFalhar(carroId);
	}	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Carro adicionar(@RequestBody Carro carro) {
		return cadastroCarroService.salvar(carro);
	}
	
	@PutMapping("/{carroId}")
	public Carro atualizar(@PathVariable Long carroId, 
			@RequestBody Carro carro) {		
		Carro carroAtual = cadastroCarroService.buscarOuFalhar(carroId);
		
		BeanUtils.copyProperties(carro, carroAtual, "id");
			
		return cadastroCarroService.salvar(carroAtual);
	}
	
	@DeleteMapping("/{carroId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long carroId) {
		cadastroCarroService.excluir(carroId);	
	}	
}
