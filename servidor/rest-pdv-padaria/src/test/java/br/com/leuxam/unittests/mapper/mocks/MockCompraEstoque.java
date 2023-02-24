package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.leuxam.data.vo.v1.CompraEstoqueVO;
import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.model.CompraEstoque;
import br.com.leuxam.model.Compras;
import br.com.leuxam.model.Estoque;

public class MockCompraEstoque {
	
	public CompraEstoque mockEntity() {
		return mockEntity(0);
	}
	
	public CompraEstoqueVO mockVO() {
		return mockVO(0);
	}
	
	public CompraEstoque mockEntity(Integer number) {
		CompraEstoque compraEstoque = new CompraEstoque();
		Compras compras = new Compras(number.longValue(), null, null);
		Estoque estoque = new Estoque(number.longValue(), null, null, null, null, null);
		compraEstoque.setCompras(compras);
		compraEstoque.setEstoque(estoque);
		compraEstoque.setPreco(number.doubleValue());
		compraEstoque.setQuantidade(number.doubleValue());
		return compraEstoque;
	}
	
	public CompraEstoqueVO mockVO(Integer number) {
		CompraEstoqueVO compraEstoque = new CompraEstoqueVO();
		ComprasVO compras = new ComprasVO(number.longValue(), null, null);
		EstoqueVO estoque = new EstoqueVO(number.longValue(), null, null, null, null, null);
		compraEstoque.setCompras(compras);
		compraEstoque.setEstoque(estoque);
		compraEstoque.setPreco(number.doubleValue());
		compraEstoque.setQuantidade(number.doubleValue());
		return compraEstoque;
	}
	
	public List<CompraEstoque> mockEntityList(){
		List<CompraEstoque> comprasEstoque = new ArrayList<>();
		for(int i = 0; i < 14; i++) {
			comprasEstoque.add(mockEntity(i));
		}
		return comprasEstoque;
	}
	
	public List<CompraEstoqueVO> mockVOList(){
		List<CompraEstoqueVO> comprasEstoque = new ArrayList<>();
		for(int i = 0; i < 14; i++) {
			comprasEstoque.add(mockVO(i));
		}
		return comprasEstoque;
	}
}
