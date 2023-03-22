package br.com.leuxam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.FornecedorController;
import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Fornecedor;
import br.com.leuxam.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	
	@Autowired
	FornecedorRepository repository;
	
	@Autowired
	PagedResourcesAssembler<FornecedorVO> assembler;
	
	public PagedModel<EntityModel<FornecedorVO>> findAll(Pageable pageable){
		
		var fornecedorPage = repository.findAll(pageable);
		
		var fornecedorVosPage = fornecedorPage.map(f -> DozerMapper.parseObject(f, FornecedorVO.class));
		fornecedorVosPage.map(f -> f.add(linkTo(methodOn(FornecedorController.class).findById(f.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(FornecedorController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(fornecedorVosPage, link);
	}
	
	public FornecedorVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe fornecedor com esse ID"));
		var vo = DozerMapper.parseObject(entity, FornecedorVO.class);
		vo.add(linkTo(methodOn(FornecedorController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public FornecedorVO create(FornecedorVO fornecedor) {
		if(fornecedor == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(fornecedor, Fornecedor.class);
		var vo = DozerMapper.parseObject(repository.save(entity), FornecedorVO.class);
		vo.add(linkTo(methodOn(FornecedorController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public FornecedorVO update(FornecedorVO fornecedor) {
		if(fornecedor == null) throw new RequiredObjectIsNullException();
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
