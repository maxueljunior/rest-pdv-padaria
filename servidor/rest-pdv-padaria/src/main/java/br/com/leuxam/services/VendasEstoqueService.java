package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.model.Estoque;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.repositories.EstoqueRepository;
import br.com.leuxam.repositories.VendasEstoqueRepository;

@Service
public class VendasEstoqueService {
	
	@Autowired
	VendasEstoqueRepository repository;
	
	@Autowired
	EstoqueRepository estoqueRepository;
	
	public List<VendaEstoque> findAll(){
		return repository.findAll();
	}
	
	public VendaEstoque create(VendaEstoque vendaEstoque) {
		return repository.save(vendaEstoque);
	}
	
	public List<VendaEstoque> findAllWithProdutcs(Long id){
		return repository.listAllWithProducts(id);
	}
	
}
