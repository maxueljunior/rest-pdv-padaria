package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return DozerMapper.parseListObjects(repository.findAll(), EstoqueVO.class);
	}
	
	public EstoqueVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		return DozerMapper.parseObject(entity, EstoqueVO.class);
	}
	
	public EstoqueVO create(EstoqueVO estoque) {
		var entity = DozerMapper.parseObject(estoque, Estoque.class);
		var vo = DozerMapper.parseObject(repository.save(entity), EstoqueVO.class);
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
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum produto com esse ID"));
		
		repository.delete(entity);
	}
}
