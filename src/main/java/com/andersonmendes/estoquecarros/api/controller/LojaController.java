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
import com.andersonmendes.estoquecarros.domain.model.Loja;
import com.andersonmendes.estoquecarros.domain.repository.LojaRepository;
import com.andersonmendes.estoquecarros.domain.service.CadastroLojaService;

@RestController
@RequestMapping("/lojas")
public class LojaController {

	@Autowired
	private LojaRepository lojaRepository;
	
	@Autowired
	private CadastroLojaService cadastroLojaService;
	
	@GetMapping
	public List<Loja> listar() {
		return lojaRepository.findAll();
	}
	
	@GetMapping("/{lojaId}")
	public ResponseEntity<Loja> buscar(@PathVariable Long lojaId) {
		Optional<Loja> loja = lojaRepository.findById(lojaId);
		
		if (loja.isPresent()) {
			return ResponseEntity.ok(loja.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Loja loja) {
		try {
			loja = cadastroLojaService.salvar(loja);
			return ResponseEntity.status(HttpStatus.CREATED).body(loja);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{lojaId}")
	public ResponseEntity<?> atualizar(@PathVariable Long lojaId, @RequestBody Loja loja) {
		try {
			Optional<Loja> lojaAtual = lojaRepository.findById(lojaId);
			
			if (lojaAtual.isPresent()) {
				BeanUtils.copyProperties(loja, lojaAtual.get(), "id");
				Loja lojaSalva = cadastroLojaService.salvar(lojaAtual.get());
				return ResponseEntity.ok(lojaSalva);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{lojaId}")
	public ResponseEntity<?> remover(@PathVariable Long lojaId) {
		try {
			cadastroLojaService.excluir(lojaId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());		
		}
		
	}
}
