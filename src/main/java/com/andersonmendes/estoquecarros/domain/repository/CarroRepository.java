package com.andersonmendes.estoquecarros.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andersonmendes.estoquecarros.domain.model.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long>{

}
