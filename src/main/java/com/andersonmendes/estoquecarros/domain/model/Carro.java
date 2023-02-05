package com.andersonmendes.estoquecarros.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carro {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String marca;
	
	@Column(nullable = false)
	private String modelo;
	
	@Column(nullable = false)
	private Long ano;
	
	@Column(nullable = false)
	private String cor;
	
	@Column(nullable = false)
	private String placa;
	
	@Enumerated(EnumType.STRING)
	private StatusCarro statusCarro;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Loja loja;
	
}
