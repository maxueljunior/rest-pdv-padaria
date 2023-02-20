package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.CompraEstoque;

@Repository
public interface CompraEstoqueRepository extends JpaRepository<CompraEstoque, Long>{
	
}
