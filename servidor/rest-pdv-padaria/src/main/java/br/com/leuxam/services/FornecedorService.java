package br.com.leuxam.services;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.FornecedorController;
import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Fornecedor;
import br.com.leuxam.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	FornecedorRepository repository;
	
	public List<FornecedorVO> findAll(){
		var fornecedores = DozerMapper.parseListObjects(repository.findAll(), FornecedorVO.class);
		fornecedores
			.stream()
			.forEach(f -> f.add(linkTo(methodOn(FornecedorController.class).findById(f.getKey())).withSelfRel()));
		return fornecedores;
	}
	
	public FornecedorVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		var vo = DozerMapper.parseObject(entity, FornecedorVO.class);
		vo.add(linkTo(methodOn(FornecedorController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public FornecedorVO create(FornecedorVO fornecedor) {
		var entity = DozerMapper.parseObject(fornecedor, Fornecedor.class);
		var vo = DozerMapper.parseObject(repository.save(entity), FornecedorVO.class);
		vo.add(linkTo(methodOn(FornecedorController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public FornecedorVO update(FornecedorVO fornecedor) {
		var entity = repository.findById(fornecedor.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		entity.setCnpj(fornecedor.getCnpj());
		entity.setNomeDoContato(fornecedor.getNomeDoContato());
		entity.setRazaoSocial(fornecedor.getRazaoSocial());
		entity.setTelefone(fornecedor.getTelefone());
		var vo = DozerMapper.parseObject(repository.save(entity), FornecedorVO.class);
		vo.add(linkTo(methodOn(FornecedorController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		repository.delete(entity);
	}

}
