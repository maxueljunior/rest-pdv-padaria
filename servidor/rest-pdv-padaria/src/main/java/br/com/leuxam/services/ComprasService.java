package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.Compras;
import br.com.leuxam.repositories.ComprasRepository;

@Service
public class ComprasService {

	@Autowired
	ComprasRepository repository;
	
	public List<Compras> listAll(){
		return repository.findAll();
	}
	
	public Compras findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		return entity;
	}
	
	public Compras create(Compras compra) {
		return repository.save(compra);
	}
	
	public Compras update(Compras compra) {
		var entity = repository.findById(compra.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		entity.setValorTotal(compra.getValorTotal());
		return repository.save(entity);
	}
	
	public void delete(Long id){
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		repository.delete(entity);
	}
}
