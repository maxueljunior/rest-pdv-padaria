package br.com.leuxam.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.leuxam.controller.CompraEstoqueController;
import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.exceptions.RequiredObjectIsNullException;
import br.com.leuxam.exceptions.ResourceNotFoundException;
import br.com.leuxam.mapper.DozerMapper;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.repositories.CompraEstoqueRepository;
import br.com.leuxam.repositories.ComprasRepository;
import jakarta.transaction.Transactional;

@Service
public class CompraEstoqueService {
	
	@Autowired
	CompraEstoqueRepository repository;
	
	@Autowired
	ComprasRepository comprasRepository;
	
	@Autowired
	PagedResourcesAssembler<CompraEstoqueVO> assembler;
	
	public PagedModel<EntityModel<CompraEstoqueVO>> findAll(Pageable pageable){
		
		var compraEstoquePage = repository.findAll(pageable);
		
		var compraEstoqueVosPage = compraEstoquePage.map(ce -> DozerMapper.parseObject(ce, CompraEstoqueVO.class));
		
		Link link = linkTo(methodOn(CompraEstoqueController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(compraEstoqueVosPage, link);
	}
	
	public PagedModel<EntityModel<CompraEstoqueVO>> findAllWithProduto(Long id, Pageable pageable){
		var compraEstoqueProdPage = repository.findAllWithProduto(id, pageable);
		
		var compraEstoqueProdVosPage = compraEstoqueProdPage.map(ce -> DozerMapper.parseObject(ce, CompraEstoqueVO.class));
		
		Link link = linkTo(methodOn(CompraEstoqueController.class).findAllWithProdutoOrCompra(id, "produto", pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(compraEstoqueProdVosPage, link);
	}
	
	public PagedModel<EntityModel<CompraEstoqueVO>> findAllWithCompra(Long id, Pageable pageable){
		
		var compraEstoqueCompPage = repository.findAllWithCompras(id, pageable);
		
		var compraEstoqueCompVosPage = compraEstoqueCompPage.map(ce -> DozerMapper.parseObject(ce, CompraEstoqueVO.class));
		
		Link link = linkTo(methodOn(CompraEstoqueController.class).findAllWithProdutoOrCompra(id, "compras", pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		
		return assembler.toModel(compraEstoqueCompVosPage, link);
	}
	
	@Transactional
	public CompraEstoqueVO create(CompraEstoqueVO compraEstoque) {
		if(compraEstoque == null) throw new RequiredObjectIsNullException();
		var entity = DozerMapper.parseObject(compraEstoque, CompraEstoque.class);
		var vo = DozerMapper.parseObject(repository.save(entity), CompraEstoqueVO.class);
		Double valorTotalAtt = repository.updateValorTotalFromCompras(entity.getCompras().getId());
		comprasRepository.updateValorTotalFromCompras(valorTotalAtt, entity.getCompras().getId());
		return vo;
	}
	
	@Transactional
	public CompraEstoqueVO update(CompraEstoqueVO compraEstoque) {
		if(compraEstoque == null) throw new RequiredObjectIsNullException();
		var entity = repository.findByIdProdutoAndCompras(compraEstoque.getEstoque().getKey(), compraEstoque.getCompras().getKey());
		if(entity == null) throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		entity.setQuantidade(compraEstoque.getQuantidade());
		entity.setPreco(compraEstoque.getPreco());
		var vo = DozerMapper.parseObject(repository.save(entity), CompraEstoqueVO.class);
		Double valorTotalAtt = repository.updateValorTotalFromCompras(entity.getCompras().getId());
		comprasRepository.updateValorTotalFromCompras(valorTotalAtt, entity.getCompras().getId());
		return vo;
	}
	
	@Transactional
	public void delete(Long idProduto, Long idCompra) {
		if(idProduto == 0 || idCompra == 0) throw new ResourceNotFoundException("Id do Produto ou da Compra não estão sendo informados");
		var entity = repository.findByIdProdutoAndCompras(idProduto, idCompra);
		if(entity == null) throw new ResourceNotFoundException("Não encontrado o relatório com os ID's informados");
		repository.delete(entity);
		Double valorTotalAtt = repository.updateValorTotalFromCompras(entity.getCompras().getId());
		comprasRepository.updateValorTotalFromCompras(valorTotalAtt, entity.getCompras().getId());
	}
}
