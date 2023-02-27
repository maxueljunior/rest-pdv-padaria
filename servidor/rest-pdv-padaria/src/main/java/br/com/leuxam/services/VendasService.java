package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.VendasVO;
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
		return DozerMapper.parseObject(entity, VendasVO.class);
	}
	
	public List<VendasVO> findAll(){
		return DozerMapper.parseListObjects(repository.findAll(), VendasVO.class);
	}
	
	public VendasVO create(VendasVO venda) {
		venda.setCondicaoPagamento(CondicaoPagamento.NULL);
		var entity = DozerMapper.parseObject(venda, Vendas.class);
		var vo = DozerMapper.parseObject(repository.save(entity), VendasVO.class);
		return vo;
	}
	
	public VendasVO update(VendasVO venda) {
		var entity = DozerMapper.parseObject(repository.findById(venda.getKey()), Vendas.class);
		if(entity == null) throw new ResourceNotFoundException("Não existe venda com esse ID");
		var newEntity = DozerMapper.parseObject(venda, Vendas.class);
		entity.setId(newEntity.getId());
		entity.setCliente(newEntity.getCliente());
		entity.setCondicaoPagamento(newEntity.getCondicaoPagamento());
		entity.setDataVenda(newEntity.getDataVenda());
		entity.setValorTotal(newEntity.getValorTotal());
		var vo = DozerMapper.parseObject(repository.save(entity), VendasVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe venda com esse ID"));
		repository.delete(entity);
	}
}
