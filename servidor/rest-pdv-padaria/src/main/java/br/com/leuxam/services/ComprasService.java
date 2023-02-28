package br.com.leuxam.services;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Compras;
import br.com.leuxam.repositories.ComprasRepository;

@Service
public class ComprasService {

	@Autowired
	ComprasRepository repository;
	
	public List<ComprasVO> findAll(){
		return DozerMapper.parseListObjects(repository.findAll(), ComprasVO.class);
	}
	
	public ComprasVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		return DozerMapper.parseObject(entity, ComprasVO.class);
	}
	
	public ComprasVO create(ComprasVO compra) {
		var entity = DozerMapper.parseObject(compra, Compras.class);
		var vo = DozerMapper.parseObject(repository.save(entity), ComprasVO.class);
		return vo;
	}
	
	public ComprasVO update(ComprasVO compra) {
		var entity = DozerMapper.parseObject(repository.findById(compra.getKey()), Compras.class);
		if(entity == null) throw new ResourceNotFoundException("Não existe compras com esse ID");
		var newEntity = DozerMapper.parseObject(compra, Compras.class);
		entity.setId(newEntity.getId());
		entity.setValorTotal(newEntity.getValorTotal());
		entity.setFornecedor(newEntity.getFornecedor());
		var vo = DozerMapper.parseObject(repository.save(entity), ComprasVO.class);
		return vo;
	}
	
	public void delete(Long id){
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe compras com esse ID"));
		repository.delete(entity);
	}
}
