package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.Vendas;

@Repository
public interface VendasEstoqueRepository extends JpaRepository<Vendas, Long>{
	
}
