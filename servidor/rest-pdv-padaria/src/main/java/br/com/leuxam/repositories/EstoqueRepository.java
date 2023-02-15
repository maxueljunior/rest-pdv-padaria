package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	
}
