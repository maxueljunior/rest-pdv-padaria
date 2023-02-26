package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.repositories.CompraEstoqueRepository;

@Service
public class CompraEstoqueService {
	
	@Autowired
	CompraEstoqueRepository repository;
	
	public CompraEstoqueVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não existe nenhuma compra"));
		return DozerMapper.parseObject(entity, CompraEstoqueVO.class);
	}
	
	public List<CompraEstoqueVO> findAll(){
		return DozerMapper.parseListObjects(repository.findAll(), CompraEstoqueVO.class);
	}
	
	public List<CompraEstoqueVO> findAllWithProduto(Long id){
		return DozerMapper.parseListObjects(repository.findAllWithProduto(id), CompraEstoqueVO.class);
	}
	
	public List<CompraEstoqueVO> findAllWithCompra(Long id){
		return DozerMapper.parseListObjects(repository.findAllWithCompras(id), CompraEstoqueVO.class);
	}
	
	public CompraEstoqueVO create(CompraEstoqueVO compraEstoque) {
		var entity = DozerMapper.parseObject(compraEstoque, CompraEstoque.class);
		var vo = DozerMapper.parseObject(repository.save(entity), CompraEstoqueVO.class);
		return vo;
	}

	public CompraEstoqueVO update(CompraEstoqueVO compraEstoque) {
		var entity = repository.findByIdProdutoAndCompras(compraEstoque.getEstoque().getKey(), compraEstoque.getCompras().getKey());
		if(entity == null) throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		entity.setQuantidade(compraEstoque.getQuantidade());
		entity.setPreco(compraEstoque.getPreco());
		var vo = DozerMapper.parseObject(repository.save(entity), CompraEstoqueVO.class);
		return vo;
	}
	
	public void delete(Long idProduto, Long idCompra) {
		if(idProduto == 0 || idCompra == 0) throw new ResourceNotFoundException("Id do Produto ou da Compra não estão sendo informados");
		var entity = repository.findByIdProdutoAndCompras(idProduto, idCompra);
		if(entity == null) throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		repository.delete(entity);
	}
}
