package br.com.leuxam.services;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.EstoqueController;
import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.repositories.EstoqueRepository;

@Service
public class EstoqueService {
	
	@Autowired
	EstoqueRepository repository;
	
	public List<EstoqueVO> findAll(){
		var estoques = DozerMapper.parseListObjects(repository.findAll(), EstoqueVO.class);
		estoques
			.stream()
			.forEach(e -> e.add(linkTo(methodOn(EstoqueController.class).findById(e.getKey())).withSelfRel()));
		return estoques;
	}
	
	public EstoqueVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		var vo = DozerMapper.parseObject(entity, EstoqueVO.class);
		vo.add(linkTo(methodOn(EstoqueController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public EstoqueVO create(EstoqueVO estoque) {
		var entity = DozerMapper.parseObject(estoque, Estoque.class);
		var vo = DozerMapper.parseObject(repository.save(entity), EstoqueVO.class);
		vo.add(linkTo(methodOn(EstoqueController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public EstoqueVO update(EstoqueVO estoque) {
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
