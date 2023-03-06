package br.com.leuxam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leuxam.data.vo.v1.VendaEstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.model.enums.CondicaoPagamento;
import br.com.leuxam.repositories.VendasEstoqueRepository;
import br.com.leuxam.repositories.VendasRepository;
import jakarta.transaction.Transactional;

@Service
public class VendasEstoqueService {
	
	@Autowired
	VendasEstoqueRepository repository;
	
	@Autowired
	VendasRepository vendasRepository;
	
	public List<VendaEstoqueVO> findAll(){
		return DozerMapper.parseListObjects(repository.findAll(), VendaEstoqueVO.class);
	}
	
	@Transactional
	public VendaEstoqueVO create(VendaEstoqueVO vendaEstoque) {
		if(vendaEstoque == null) throw new RequiredObjectIsNullException();
		vendaEstoque.getVendas().setCondicaoPagamento(CondicaoPagamento.NULL);
		var entity = DozerMapper.parseObject(vendaEstoque, VendaEstoque.class);
		
		Vendas vendas = entity.getVendas();
		vendas = vendasRepository.findById(vendas.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Não encontrado a Venda"));
		
		vendas.setCondicaoPagamento(vendas.getCondicaoPagamento());
		entity.setVendas(vendas);
		var vo = DozerMapper.parseObject(repository.save(entity), VendaEstoqueVO.class);
		Double valorTotalAtt = repository.updateValorTotalFromVendas(entity.getVendas().getId());
		vendasRepository.updateValorTotalFromVendas(valorTotalAtt, entity.getVendas().getId());
		return vo;
	}
	
	public List<VendaEstoqueVO> findAllWithProdutcs(Long id){
		return DozerMapper.parseListObjects(repository.findAllWithProducts(id), VendaEstoqueVO.class);
	}
	
	public List<VendaEstoqueVO> findAllWithVendas(Long id){
		return DozerMapper.parseListObjects(repository.findAllWithVendas(id), VendaEstoqueVO.class);
	}
	
	@Transactional
	public VendaEstoqueVO updateByIdProductAndVendas(VendaEstoqueVO vendaEstoque) {
		if(vendaEstoque == null) throw new RequiredObjectIsNullException();
		var entity = repository.findByIdProductAndVendas(vendaEstoque.getEstoque().getKey(), vendaEstoque.getVendas().getKey());
		if(entity == null) {
			throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		}
		entity.setQuantidade(vendaEstoque.getQuantidade());
		entity.setPreco(vendaEstoque.getPreco());
		var vo = DozerMapper.parseObject(repository.save(entity), VendaEstoqueVO.class);
		Double valorTotalAtt = repository.updateValorTotalFromVendas(entity.getVendas().getId());
		vendasRepository.updateValorTotalFromVendas(valorTotalAtt, entity.getVendas().getId());
		return vo;
	}
	
	@Transactional
	public void delete(Long idProdutos, Long idVendas) {
		if(idProdutos == 0 || idVendas == 0) throw new ResourceNotFoundException("Id do produto ou Id da venda está zerado!");
		var entity = repository.findByIdProductAndVendas(idProdutos, idVendas);
		if(entity == null) throw new ResourceNotFoundException("Não encontrado a Venda de Produtos com os ID's informados");
		repository.delete(entity);
		Double valorTotalAtt = repository.updateValorTotalFromVendas(entity.getVendas().getId());
		vendasRepository.updateValorTotalFromVendas(valorTotalAtt, entity.getVendas().getId());
	}
	
	/*
	 * Essa função está comentada pois não vejo utilidade nela, por enquanto.
	 * 
	@Transactional
	public void updateProdutoWithVendas(Long idProdutoAntigo, Long idVendas, Long idProdutoNovo) {
		if(idProdutoAntigo == 0 || idVendas == 0 || idProdutoNovo == 0) throw new ResourceNotFoundException("Recursos não encontrados");
		repository.updateProdutoWithVendas(idProdutoAntigo, idVendas, idProdutoNovo);
	}
	*/
}
