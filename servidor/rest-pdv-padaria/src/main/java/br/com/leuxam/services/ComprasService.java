package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.repositories.ComprasRepository;

@Service
public class ComprasService {

	@Autowired
	ComprasRepository repository;
	
	public List<ComprasVO> findAll(){
		return repository.findAll();
	}
	
	public ComprasVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		return entity;
	}
	
	public ComprasVO create(ComprasVO compra) {
		return repository.save(compra);
	}
	
	public ComprasVO update(ComprasVO compra) {
		var entity = repository.findById(compra.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		entity.setValorTotal(compra.getValorTotal());
		entity.setFornecedor(compra.getFornecedor());
		return repository.save(entity);
	}
	
	public void delete(Long id){
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		repository.delete(entity);
	}
}
