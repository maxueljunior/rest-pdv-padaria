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
		return repository.listAllWithProducts(id);
	}
	
	public List<VendaEstoque> findAllWithVendas(Long id){
		return repository.listAllWithVendas(id);
	}
	
	@Transactional
	public void updateProdutoWithVendas(Long idProdutoAntigo, Long idVendas, Long idProdutoNovo) {
		if(idProdutoAntigo == 0 || idVendas == 0 || idProdutoNovo == 0) throw new ResourceNotFoundException("Recursos não encontrados");
		repository.updateProdutoWithVendas(idProdutoAntigo, idVendas, idProdutoNovo);
	}
}
