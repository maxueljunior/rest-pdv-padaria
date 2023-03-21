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

import br.com.leuxam.controller.ClienteController;
import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.repositories.ClienteRepository;

@Service
public class ClienteServices {
	
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	PagedResourcesAssembler<ClienteVO> assembler;
	
	public PagedModel<EntityModel<ClienteVO>> findAll(Pageable pageable){
		
		var clientesPage = repository.findAll(pageable);
		
		var clientesVosPage = clientesPage.map(c -> DozerMapper.parseObject(c, ClienteVO.class));
		clientesVosPage.map(c -> c.add(linkTo(methodOn(ClienteController.class).findById(c.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(ClienteController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(clientesVosPage, link);
	}
	
	public ClienteVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		var vo = DozerMapper.parseObject(entity, ClienteVO.class);
		vo.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public ClienteVO create(ClienteVO cliente) {
		if(cliente == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(cliente, Cliente.class);
		var vo = DozerMapper.parseObject(repository.save(entity), ClienteVO.class);
		vo.add(linkTo(methodOn(ClienteController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public ClienteVO update(ClienteVO cliente) {
		if(cliente == null) throw new RequiredObjectIsNullException();
		var entity = repository.findById(cliente.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		entity.setDataNascimento(cliente.getDataNascimento());
		entity.setEndereco(cliente.getEndereco());
		entity.setLucratividade(cliente.getLucratividade());
		entity.setNome(cliente.getNome());
		entity.setSexo(cliente.getSexo());
		entity.setSobrenome(cliente.getSobrenome());
		entity.setTelefone(cliente.getTelefone());

		var vo = DozerMapper.parseObject(repository.save(entity), ClienteVO.class);
		vo.add(linkTo(methodOn(ClienteController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não tem nenhum cliente com esse ID"));
		repository.delete(entity);
	}
}
