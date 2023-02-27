package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.Compras;

@Repository
public interface ComprasRepository extends JpaRepository<Compras, Long>{
	
	@Modifying
	@Query("UPDATE Compras c SET c.valorTotal =:valorTotalNovo WHERE c.id =:idCompras")
	public void updateValorTotalFromCompras(
			@Param("valorTotalNovo") Double valorTotalNovo,
			@Param("idCompras") Long idCompras);
}
