package br.com.leuxam.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.VendaEstoque;

@Repository
public interface VendasEstoqueRepository extends JpaRepository<VendaEstoque, Long>{
	
	@Query("SELECT v FROM VendaEstoque v WHERE v.id.estoque.id =:idProduto AND v.id.vendas.id =:idVendas")
	public VendaEstoque findByIdProductAndVendas(
			@Param("idProduto") Long idProduto,
			@Param("idVendas") Long idVendas);
	
	@Query("SELECT v FROM VendaEstoque v WHERE v.id.estoque.id =:idProduto")
	public Page<VendaEstoque> findAllWithProducts(@Param("idProduto") Long idProduto, Pageable pageable);
	
	@Query("SELECT v FROM VendaEstoque v WHERE v.id.vendas.id =:idVendas")
	public Page<VendaEstoque> findAllWithVendas(@Param("idVendas") Long idVendas, Pageable pageable);
	
	@Modifying
	@Query("DELETE FROM VendaEstoque v WHERE v.id.estoque.id =:idProduto AND v.id.vendas.id =:idVendas")
	public void deleteByIdProductAndVendas(
			@Param("idProduto") Long idProduto,
			@Param("idVendas") Long idVendas);
	
	@Query("SELECT SUM (v.preco * v.quantidade) FROM VendaEstoque v WHERE v.id.vendas.id =:idVendas")
	public Double updateValorTotalFromVendas(@Param("idVendas") Long idVendas);
	
	/*
	 * 
	 * Esssa função está comentada pois não vejo sentido nela agora
	 * 
	@Modifying
	@Query("UPDATE VendaEstoque v SET v.id.estoque.id =:idProdutoNovo WHERE v.id.estoque.id =:idProdutoAntigo AND v.id.vendas.id =:idVenda")
	public void updateProdutoWithVendas(
			@Param("idProdutoAntigo") Long idProdutoAntigo, 
			@Param("idVenda") Long idVenda, 
			@Param("idProdutoNovo") Long idProdutoNovo);
			*/
	
}
