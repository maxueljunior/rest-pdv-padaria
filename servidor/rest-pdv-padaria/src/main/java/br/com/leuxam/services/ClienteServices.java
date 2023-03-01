package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
	
	public List<ClienteVO> findAll(){
		var clientes = DozerMapper.parseListObjects(repository.findAll(), ClienteVO.class);
		clientes
			.stream()
			.forEach(c -> c.add(linkTo(methodOn(ClienteController.class).findById(c.getKey())).withSelfRel()));
		return clientes;
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
