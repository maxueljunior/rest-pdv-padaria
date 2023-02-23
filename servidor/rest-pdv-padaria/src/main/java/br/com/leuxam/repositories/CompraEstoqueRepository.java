package br.com.leuxam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.CompraEstoque;

@Repository
public interface CompraEstoqueRepository extends JpaRepository<CompraEstoque, Long>{
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.compras.id =:idCompra")
	public List<CompraEstoque> findAllWithCompras(@Param("idCompra") Long idCompra);
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto")
	public List<CompraEstoque> findAllWithProduto(@Param("idProduto") Long idProduto);
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto AND c.id.compras.id =:idCompra")
	public CompraEstoque findByIdProdutoAndCompras(
			@Param("idProduto") Long idProduto,
			@Param("idCompra") Long idCompra);
	
	@Modifying
	@Query("DELETE FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto AND c.id.compras.id =:idCompra")
	public void deleteByIdProdutoAndCompras(
			@Param("idProduto") Long idProduto,
			@Param("idCompra") Long idCompra);
}
