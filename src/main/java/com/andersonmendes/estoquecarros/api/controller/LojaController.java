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
	public Loja buscar(@PathVariable Long lojaId) {
		return cadastroLojaService.buscarOuFalhar(lojaId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Loja adicionar(@RequestBody Loja loja) {
		return cadastroLojaService.salvar(loja);
	}
	
	@PutMapping("/{lojaId}")
	public Loja atualizar(@PathVariable Long lojaId, @RequestBody Loja loja) {
		Loja lojaAtual = cadastroLojaService.buscarOuFalhar(lojaId);
		
		BeanUtils.copyProperties(loja, lojaAtual, "id");
				
		return cadastroLojaService.salvar(lojaAtual);
	}
	
	@DeleteMapping("/{lojaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long lojaId) {
		cadastroLojaService.excluir(lojaId);
	}
}
