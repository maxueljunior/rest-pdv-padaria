package br.com.leuxam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.ClienteController;
import br.com.leuxam.controller.VendasController;
import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.model.enums.CondicaoPagamento;
import br.com.leuxam.repositories.VendasRepository;

@Service
public class VendasService {
	
	@Autowired
	VendasRepository repository;
	
	public VendasVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		var vo = DozerMapper.parseObject(entity, VendasVO.class);
		vo.add(linkTo(methodOn(VendasController.class).findById(id)).withSelfRel());
		ClienteVO cliente = vo.getCliente();
		cliente.add(linkTo(methodOn(ClienteController.class).findById(cliente.getKey())).withSelfRel());
		vo.setCliente(cliente);
		return vo;
	}
	
	public List<VendasVO> findAll(){
		var vendas = DozerMapper.parseListObjects(repository.findAll(), VendasVO.class);
		vendas
			.stream()
			.forEach(v -> v.add(linkTo(methodOn(VendasController.class).findById(v.getKey())).withSelfRel()));
		
		List<ClienteVO> clientes = new ArrayList<>();
		
		for (VendasVO vendaVO : vendas) {
			clientes.add(vendaVO.getCliente());
		}
		
		clientes
			.stream()
			.forEach(c -> c.add(linkTo(methodOn(ClienteController.class).findById(c.getKey())).withSelfRel()));
		
		for (int i = 0; i < clientes.size(); i++) {
			vendas.get(i).setCliente(clientes.get(i));
		}
		
		return vendas;
	}
	
	public VendasVO create(VendasVO venda) {
		if(venda == null) throw new RequiredObjectIsNullException();
		venda.setCondicaoPagamento(CondicaoPagamento.PIX);
		var entity = DozerMapper.parseObject(venda, Vendas.class);
		var vo = DozerMapper.parseObject(repository.save(entity), VendasVO.class);
		vo.add(linkTo(methodOn(VendasController.class).findById(vo.getKey())).withSelfRel());
		ClienteVO cliente = vo.getCliente();
		cliente.add(linkTo(methodOn(ClienteController.class).findById(cliente.getKey())).withSelfRel());
		vo.setCliente(cliente);
		vo.setCondicaoPagamento(CondicaoPagamento.NULL);
		vo.setDataVenda(new Date());
		return vo;
	}
	
	public VendasVO update(VendasVO venda) {
		if(venda == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(repository.findById(venda.getKey()), Vendas.class);
		if(entity == null) throw new ResourceNotFoundException("Não existe venda com esse ID");
		var newEntity = DozerMapper.parseObject(venda, Vendas.class);
		entity.setId(newEntity.getId());
		entity.setCliente(newEntity.getCliente());
		entity.setCondicaoPagamento(newEntity.getCondicaoPagamento());
		entity.setDataVenda(newEntity.getDataVenda());
		entity.setValorTotal(newEntity.getValorTotal());
		var vo = DozerMapper.parseObject(repository.save(entity), VendasVO.class);
		vo.add(linkTo(methodOn(VendasController.class).findById(vo.getKey())).withSelfRel());
		ClienteVO cliente = vo.getCliente();
		cliente.add(linkTo(methodOn(ClienteController.class).findById(cliente.getKey())).withSelfRel());
		vo.setCliente(cliente);
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		repository.delete(entity);
	}
}
