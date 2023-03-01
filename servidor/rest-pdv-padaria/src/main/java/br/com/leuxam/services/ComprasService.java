package br.com.leuxam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.ComprasController;
import br.com.leuxam.controller.FornecedorController;
import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Compras;
import br.com.leuxam.repositories.ComprasRepository;

@Service
public class ComprasService {

	@Autowired
	ComprasRepository repository;
	
	public List<ComprasVO> findAll(){
		var compras = DozerMapper.parseListObjects(repository.findAll(), ComprasVO.class);
		compras
			.stream()
			.forEach(c -> c.add(linkTo(methodOn(ComprasController.class).findById(c.getKey())).withSelfRel()));
		
		List<FornecedorVO> fornecedor = new ArrayList<>();
		
		for (ComprasVO compraVO : compras) {
			fornecedor.add(compraVO.getFornecedor());
		}
		
		fornecedor
			.stream()
			.forEach(f -> f.add(linkTo(methodOn(FornecedorController.class).findById(f.getKey())).withSelfRel()));
		
		for (int i = 0; i < fornecedor.size(); i++) {
			compras.get(i).setFornecedor(fornecedor.get(i));
		}
		
		return compras;
	}
	
	public ComprasVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		var vo = DozerMapper.parseObject(entity, ComprasVO.class);
		vo.add(linkTo(methodOn(ComprasController.class).findById(id)).withSelfRel());
		FornecedorVO fornecedorVO = vo.getFornecedor();
		fornecedorVO.add(linkTo(methodOn(FornecedorController.class).findById(fornecedorVO.getKey())).withSelfRel());
		vo.setFornecedor(fornecedorVO);
		return vo;
	}
	
	public ComprasVO create(ComprasVO compra) {
		if(compra == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(compra, Compras.class);
		var vo = DozerMapper.parseObject(repository.save(entity), ComprasVO.class);
		vo.add(linkTo(methodOn(ComprasController.class).findById(vo.getKey())).withSelfRel());
		FornecedorVO fornecedorVO = vo.getFornecedor();
		fornecedorVO.add(linkTo(methodOn(FornecedorController.class).findById(fornecedorVO.getKey())).withSelfRel());
		vo.setFornecedor(fornecedorVO);
		return vo;
	}
	
	public ComprasVO update(ComprasVO compra) {
		if(compra == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(repository.findById(compra.getKey()), Compras.class);
		if(entity == null) throw new ResourceNotFoundException("Não existe compras com esse ID");
		var newEntity = DozerMapper.parseObject(compra, Compras.class);
		entity.setId(newEntity.getId());
		entity.setValorTotal(newEntity.getValorTotal());
		entity.setFornecedor(newEntity.getFornecedor());
		var vo = DozerMapper.parseObject(repository.save(entity), ComprasVO.class);
		vo.add(linkTo(methodOn(ComprasController.class).findById(vo.getKey())).withSelfRel());
		FornecedorVO fornecedorVO = vo.getFornecedor();
		fornecedorVO.add(linkTo(methodOn(FornecedorController.class).findById(fornecedorVO.getKey())).withSelfRel());
		vo.setFornecedor(fornecedorVO);
		return vo;
	}
	
	public void delete(Long id){
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		repository.delete(entity);
	}
}
