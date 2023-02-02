package com.andersonmendes.estoquecarros.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Carro> buscar(@PathVariable Long carroId) {
		Optional<Carro> carro = carroRepository.findById(carroId);
	
		if (carro.isPresent()) {
			return ResponseEntity.ok(carro.get());
		}
		
		return ResponseEntity.notFound().build();
	
	}	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Carro carro) {
		try {
			carro = cadastroCarroService.salvar(carro);
			return ResponseEntity.status(HttpStatus.CREATED).body(carro);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{carroId}")
	public ResponseEntity<?> atualizar(@PathVariable Long carroId, @RequestBody Carro carro) {
		try {
			Optional<Carro> carroAtual = carroRepository.findById(carroId);
			
			if (carroAtual.isPresent()) {
				BeanUtils.copyProperties(carro, carroAtual.get(), "id");
				Carro carroSalvo = cadastroCarroService.salvar(carroAtual.get());
				return ResponseEntity.ok(carroSalvo);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{carroId}")
	public ResponseEntity<Carro> remover(@PathVariable Long carroId) {
		try {
			cadastroCarroService.excluir(carroId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
}
