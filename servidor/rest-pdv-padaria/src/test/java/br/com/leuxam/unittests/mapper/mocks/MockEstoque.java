package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.model.Estoque;

public class MockEstoque {
	
	public Estoque mockEntity() {
		return mockEntity(0);
	}
	
	public EstoqueVO mockVO() {
		return mockVO(0);
	}
	
	public Estoque mockEntity(Integer number) {
		Estoque estoque = new Estoque();
		estoque.setDataCompra(new Date());
		estoque.setDataValidade(new Date());
		estoque.setDescricao("Descricao"+number);
		estoque.setId(number.longValue());
		estoque.setQuantidade(number.doubleValue());
		estoque.setUnidade((number % 2) == 0 ? "UN" : "KG");
		return estoque;
	}
	
	public EstoqueVO mockVO(Integer number) {
		EstoqueVO estoque = new EstoqueVO();
		estoque.setDataCompra(new Date());
		estoque.setDataValidade(new Date());
		estoque.setDescricao("Descricao"+number);
		estoque.setId(number.longValue());
		estoque.setQuantidade(number.doubleValue());
		estoque.setUnidade((number % 2) == 0 ? "UN" : "KG");
		return estoque;
	}
	
	public List<Estoque> mockEntityList(){
		List<Estoque> estoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			estoque.add(mockEntity(i));
		}
		return estoque;
	}
	
	public List<EstoqueVO> mockVOList(){
		List<EstoqueVO> estoque = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			estoque.add(mockVO(i));
		}
		return estoque;
	}
}
