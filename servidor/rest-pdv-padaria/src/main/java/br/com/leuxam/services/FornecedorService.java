package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	FornecedorRepository repository;
	
	public List<FornecedorVO> findAll(){
		return repository.findAll();
	}
	
	public FornecedorVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		return entity;
	}
	
	public FornecedorVO create(FornecedorVO fornecedor) {
		return repository.save(fornecedor);
	}
	
	public FornecedorVO update(FornecedorVO fornecedor) {
		var entity = repository.findById(fornecedor.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		entity.setCnpj(fornecedor.getCnpj());
		entity.setNomeDoContato(fornecedor.getNomeDoContato());
		entity.setRazaoSocial(fornecedor.getRazaoSocial());
		entity.setTelefone(fornecedor.getTelefone());
		return repository.save(entity);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		repository.delete(entity);
	}

}
