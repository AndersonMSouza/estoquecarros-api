package com.andersonmendes.estoquecarros.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeEmUsoException;
import com.andersonmendes.estoquecarros.domain.exceptions.EntidadeNaoEncontradaException;
import com.andersonmendes.estoquecarros.domain.model.Pessoa;
import com.andersonmendes.estoquecarros.domain.repository.PessoaRepository;
import com.andersonmendes.estoquecarros.domain.service.CadastroPessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CadastroPessoaService cadastroPessoaService;
	
	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}
	
	@GetMapping("/{pessoaId}")
	public Pessoa buscar(@PathVariable Long pessoaId) {
		return cadastroPessoaService.buscarOuFalhar(pessoaId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pessoa adicionar(@RequestBody Pessoa pessoa) {
		return cadastroPessoaService.salvar(pessoa);
	}
	
	@PutMapping("/{pessoaId}")
	public Pessoa atualizar(@PathVariable Long pessoaId, @RequestBody Pessoa pessoa) {
		Pessoa pessoaAtual = cadastroPessoaService.buscarOuFalhar(pessoaId);
		
		BeanUtils.copyProperties(pessoa, pessoaAtual, "id");
		
		return cadastroPessoaService.salvar(pessoaAtual);
	}
	
	@DeleteMapping("/{pessoaId}")
	public ResponseEntity<?> remover(@PathVariable Long pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
