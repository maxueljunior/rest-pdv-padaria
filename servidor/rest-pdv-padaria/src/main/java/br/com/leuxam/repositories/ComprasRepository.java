package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.Compras;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Long>{
	
}
