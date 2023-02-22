package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.model.enums.CondicaoPagamento;
import br.com.leuxam.repositories.VendasEstoqueRepository;
import jakarta.transaction.Transactional;

@Service
public class VendasEstoqueService {
	
	@Autowired
	VendasEstoqueRepository repository;
	
	public List<VendaEstoque> findAll(){
		return repository.findAll();
	}
	
	public VendaEstoque create(VendaEstoque vendaEstoque) {
		vendaEstoque.getVendas().setCondicaoPagamento(CondicaoPagamento.NULL);
		return repository.save(vendaEstoque);
	}
	
	public List<VendaEstoque> findAllWithProdutcs(Long id){
		return repository.findAllWithProducts(id);
	}
	
	public List<VendaEstoque> findAllWithVendas(Long id){
		return repository.findAllWithVendas(id);
	}
	
	public VendaEstoque updateByIdProductAndVendas(VendaEstoque vendaEstoque) {
		var entity = repository.findByIdProductAndVendas(vendaEstoque.getEstoque().getId(), vendaEstoque.getVendas().getId());
		if(entity == null) {
			throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		}
		entity.setQuantidade(vendaEstoque.getQuantidade());
		entity.setPreco(vendaEstoque.getPreco());
		return repository.save(entity);
	}
	
	public void delete(Long idProdutos, Long idVendas) {
		if(idProdutos == 0 || idVendas == 0) throw new ResourceNotFoundException("Id do produto ou Id da venda está zerado!");
		var entity = repository.findByIdProductAndVendas(idProdutos, idVendas);
		if(entity == null) throw new ResourceNotFoundException("Não encontrado a Venda de Produtos com os ID's informados");
		repository.delete(entity);
	}
	
	/*
	 * Essa função está comentada pois não vejo utilidade nela, por enquanto.
	 * 
	@Transactional
	public void updateProdutoWithVendas(Long idProdutoAntigo, Long idVendas, Long idProdutoNovo) {
		if(idProdutoAntigo == 0 || idVendas == 0 || idProdutoNovo == 0) throw new ResourceNotFoundException("Recursos não encontrados");
		repository.updateProdutoWithVendas(idProdutoAntigo, idVendas, idProdutoNovo);
	}
	*/
}
