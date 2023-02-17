package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.repositories.EstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	EstoqueRepository repository;
	
	public List<Estoque> findAll(){
		return repository.findAll();
	}
	
	public Estoque findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		return entity;
	}
	
	public Estoque create(Estoque estoque) {
		return repository.save(estoque);
	}
	
	public Estoque update(Estoque estoque) {
		var entity = repository.findById(estoque.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		entity.setDataCompra(estoque.getDataCompra());
		entity.setDataValidade(estoque.getDataValidade());
		entity.setDescricao(estoque.getDescricao());
		entity.setQuantidade(estoque.getQuantidade());
		entity.setUnidade(estoque.getUnidade());
		
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		
		repository.delete(entity);
	}
}
