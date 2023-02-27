package br.com.leuxam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.Vendas;

@Repository
public interface VendasRepository extends JpaRepository<Vendas,Long>{
	
	@Modifying
	@Query("UPDATE Vendas v SET v.valorTotal =:valorTotalNovo WHERE v.id =:idVendas")
	public void updateValorTotalFromVendas(
			@Param("valorTotalNovo") Double valorTotalNovo,
			@Param("idVendas") Long idVendas);
	
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
