package br.com.leuxam.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.CompraEstoque;

@Repository
public interface CompraEstoqueRepository extends JpaRepository<CompraEstoque, Long>{
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.compras.id =:idCompra")
	public Page<CompraEstoque> findAllWithCompras(@Param("idCompra") Long idCompra, Pageable pageable);
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto")
	public Page<CompraEstoque> findAllWithProduto(@Param("idProduto") Long idProduto, Pageable pageable);
	
	@Query("SELECT c FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto AND c.id.compras.id =:idCompra")
	public CompraEstoque findByIdProdutoAndCompras(
			@Param("idProduto") Long idProduto,
			@Param("idCompra") Long idCompra);

	@Modifying
	@Query("DELETE FROM CompraEstoque c WHERE c.id.estoque.id =:idProduto AND c.id.compras.id =:idCompra")
	public void deleteByIdProdutoAndCompras(
			@Param("idProduto") Long idProduto,
			@Param("idCompra") Long idCompra);
	
	@Query("SELECT SUM (c.preco * c.quantidade) FROM CompraEstoque c WHERE c.id.compras.id =:idCompras")
	public Double updateValorTotalFromCompras(@Param("idCompras") Long idCompras);
}
