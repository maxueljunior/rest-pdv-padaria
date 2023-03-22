package br.com.leuxam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
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
import br.com.leuxam.security.jwt.JwtTokenProvider;

@Service
public class VendasService {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	VendasRepository repository;
	
	@Autowired
	PagedResourcesAssembler<VendasVO> assembler;
	
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
	
	public PagedModel<EntityModel<VendasVO>> findAll(Pageable pageable){
		
		var vendasPage = repository.findAll(pageable);
		
		var vendasVosPage = vendasPage.map(v -> DozerMapper.parseObject(v, VendasVO.class));
		vendasVosPage.map(v -> v.add(linkTo(methodOn(VendasController.class).findById(v.getKey())).withSelfRel()));
		
		List<ClienteVO> clientes = new ArrayList<>();
		
		for (VendasVO vendaVO : vendasVosPage) {
			clientes.add(vendaVO.getCliente());
		}
		
		clientes
			.stream()
			.forEach(c -> c.add(linkTo(methodOn(ClienteController.class).findById(c.getKey())).withSelfRel()));
		
		for (int i = 0; i < clientes.size(); i++) {
			vendasVosPage.toList().get(i).setCliente(clientes.get(i));
		}
		
		Link link = linkTo(methodOn(VendasController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(vendasVosPage, link);
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
