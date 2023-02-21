package br.com.leuxam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leuxam.model.VendaEstoque;

@Repository
public interface VendasEstoqueRepository extends JpaRepository<VendaEstoque, Long>{
	/*
	@Modifying
	@Query("UPDATE VendaEstoque v SET v.id.estoque =:idProdutoNovo WHERE v.id.estoque =:idProdutoAntigo AND v.id.vendas =:idVenda")
	VendaEstoque updateVendaEstoque(@Param("idProdutoAntigo") Long idProdutoAntigo, @Param("idVenda") Long idVenda, @Param("idProdutoNovo") Long idProdutoNovo);
	
	@Query("SELECT v FROM VendaEstoque v WHERE v.id.estoque :=idProduto AND v.id.vendas =:idVenda")
	VendaEstoque findByProduct(@Param("idProduto") Long idProduto, @Param("idVenda") Long idVenda);
	*/
	
	@Query("SELECT v FROM VendaEstoque v WHERE v.id.estoque.id =:idProduto")
	public List<VendaEstoque> listAllWithProducts(@Param("idProduto") Long idProduto);
}
