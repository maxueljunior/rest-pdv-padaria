package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.repositories.CompraEstoqueRepository;

@Service
public class CompraEstoqueService {
	
	@Autowired
	CompraEstoqueRepository repository;
	
	public CompraEstoque findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhuma compra"));
		return entity;
	}
	
	public List<CompraEstoque> findAll(){
		return repository.findAll();
	}
	
	public CompraEstoque create(CompraEstoque compraEstoque) {
		return repository.save(compraEstoque);
	}
	
	public CompraEstoque update(CompraEstoque compraEstoque) {
		var entity = repository.findById(compraEstoque.getCompras().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhuma compra com esse ID"));
		entity.setEstoque(compraEstoque.getEstoque());
		entity.setPreco(compraEstoque.getPreco());
		entity.setQuantidade(compraEstoque.getQuantidade());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhuma compra com esse ID"));
		repository.delete(entity);
	}
	
}
