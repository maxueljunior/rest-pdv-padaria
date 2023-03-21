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

import br.com.leuxam.controller.EstoqueController;
import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.repositories.EstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	EstoqueRepository repository;
	
	@Autowired
	PagedResourcesAssembler<EstoqueVO> assembler;
	
	public PagedModel<EntityModel<EstoqueVO>> findAll(Pageable pageable){
		
		var estoquePage = repository.findAll(pageable);
		
		var estoqueVosPage = estoquePage.map(e -> DozerMapper.parseObject(e, EstoqueVO.class));
		estoqueVosPage.map(e -> e.add(linkTo(methodOn(EstoqueController.class).findById(e.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(EstoqueController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(estoqueVosPage, link);
	}
	
	public EstoqueVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		var vo = DozerMapper.parseObject(entity, EstoqueVO.class);
		vo.add(linkTo(methodOn(EstoqueController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public EstoqueVO create(EstoqueVO estoque) {
		if(estoque == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(estoque, Estoque.class);
		var vo = DozerMapper.parseObject(repository.save(entity), EstoqueVO.class);
		vo.add(linkTo(methodOn(EstoqueController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public EstoqueVO update(EstoqueVO estoque) {
		if(estoque == null) throw new RequiredObjectIsNullException();
		var entity = repository.findById(estoque.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		entity.setDataCompra(estoque.getDataCompra());
		entity.setDataValidade(estoque.getDataValidade());
		entity.setDescricao(estoque.getDescricao());
		entity.setQuantidade(estoque.getQuantidade());
		entity.setUnidade(estoque.getUnidade());
		
		var vo = DozerMapper.parseObject(repository.save(entity), EstoqueVO.class);
		vo.add(linkTo(methodOn(EstoqueController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		
		repository.delete(entity);
	}
}
