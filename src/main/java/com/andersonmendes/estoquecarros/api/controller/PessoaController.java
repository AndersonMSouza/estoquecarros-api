package com.andersonmendes.estoquecarros.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Pessoa;
import com.andersonmendes.estoquecarros.domain.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{pessoaId}")
	public ResponseEntity<Pessoa> buscar(@PathVariable Long pessoaId) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
		
		if (pessoa.isPresent()) {
			return ResponseEntity.ok(pessoa.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Pessoa pessoa) {
		try {
			pessoa = pessoaRepository.save(null);
			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
