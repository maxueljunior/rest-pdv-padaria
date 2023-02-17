package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.repositories.ClienteRepository;

@Service
public class ClienteServices {
	
	@Autowired
	ClienteRepository repository;
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}
	
	public Cliente findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		return entity;
	}
	
	public Cliente create(Cliente cliente) {
		var entity = repository.save(cliente);
		return entity;
	}
	
	public Cliente update(Cliente cliente) {
		var entity = repository.findById(cliente.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		entity.setDataNascimento(cliente.getDataNascimento());
		entity.setEndereco(cliente.getEndereco());
		entity.setLucratividade(cliente.getLucratividade());
		entity.setNome(cliente.getNome());
		entity.setSexo(cliente.getSexo());
		entity.setSobrenome(cliente.getSobrenome());
		entity.setTelefone(cliente.getTelefone());

		return repository.save(entity);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		repository.delete(entity);
	}
}
