package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.data.vo.v1.VendaEstoqueVO;
import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.model.Estoque;
import br.com.leuxam.model.VendaEstoque;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.model.enums.CondicaoPagamento;

public class MockVendaEstoque {
	
	public VendaEstoque mockEntity() {
		return mockEntity(0);
	}
	
	public VendaEstoqueVO mockVO() {
		return mockVO(0);
	}
	
	public VendaEstoque mockEntity(Integer number) {
		VendaEstoque vendaEstoque = new VendaEstoque();
		Estoque estoque = new Estoque(number.longValue(), null, null, null, null, null);
		Vendas vendas = new Vendas(number.longValue(), null, null, CondicaoPagamento.PIX, null);
		vendaEstoque.setEstoque(estoque);
		vendaEstoque.setPreco(number.doubleValue());
		vendaEstoque.setQuantidade(number.doubleValue());
		vendaEstoque.setVendas(vendas);
		return vendaEstoque;
	}
	
	public VendaEstoqueVO mockVO(Integer number) {
		VendaEstoqueVO vendaEstoque = new VendaEstoqueVO();
		EstoqueVO estoque = new EstoqueVO(number.longValue(), null, null, null, null, null);
		VendasVO vendas = new VendasVO(number.longValue(), null, null, CondicaoPagamento.PIX, null);
		vendaEstoque.setEstoque(estoque);
		vendaEstoque.setPreco(number.doubleValue());
		vendaEstoque.setQuantidade(number.doubleValue());
		vendaEstoque.setVendas(vendas);
		return vendaEstoque;
	}
	
	public List<VendaEstoque> mockEntityList(){
		List<VendaEstoque> vendaEstoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendaEstoque.add(mockEntity(i));
		}
		return vendaEstoque;
	}	
	
	public List<VendaEstoque> mockEntityListForProdutos(){
		List<VendaEstoque> vendaEstoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendaEstoque.add(mockEntity(i));
			vendaEstoque.get(i).getEstoque().setId(1L);
		}
		return vendaEstoque;
	}
	
	
	public List<VendaEstoque> mockEntityListForVendas(){
		List<VendaEstoque> vendaEstoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendaEstoque.add(mockEntity(i));
			vendaEstoque.get(i).getVendas().setId(1L);
		}
		return vendaEstoque;
	}
	
	public List<VendaEstoqueVO> mockVOList(){
		List<VendaEstoqueVO> vendaEstoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendaEstoque.add(mockVO(i));
		}
		return vendaEstoque;
	}
}
