package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.repositories.VendasRepository;

@Service
public class VendasService {
	
	@Autowired
	VendasRepository repository;
	
	public Vendas findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		return entity;
	}
	
	public List<Vendas> findAll(){
		return repository.findAll();
	}
	
	public Vendas create(Vendas venda) {
		return repository.save(venda);
	}
	
	public Vendas update(Vendas venda) {
		var entity = repository.findById(venda.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		entity.setCliente(venda.getCliente());
		entity.setCondicaoPagamento(venda.getCondicaoPagamento());
		entity.setDataVenda(venda.getDataVenda());
		entity.setValorTotal(venda.getValorTotal());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		repository.delete(entity);
	}
	
}