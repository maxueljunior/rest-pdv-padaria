package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.model.Compras;
import br.com.leuxam.model.Fornecedor;

public class MockCompras {
	
	public Compras mockEntity() {
		return mockEntity(0);
	}
	
	public ComprasVO mockVO() {
		return mockVO(0);
	}
	
	public Compras mockEntity(Integer number) {
		Compras compras = new Compras();
		Fornecedor fornecedor = new Fornecedor(number.longValue(), null, null, null, null);
		compras.setFornecedor(fornecedor);
		compras.setId(number.longValue());
		compras.setValorTotal(number.doubleValue());
		return compras;
	}
	
	public ComprasVO mockVO(Integer number) {
		ComprasVO compras = new ComprasVO();
		FornecedorVO fornecedor = new FornecedorVO(number.longValue(), null, null, null, null);
		compras.setFornecedor(fornecedor);
		compras.setKey(number.longValue());
		compras.setValorTotal(number.doubleValue());
		return compras;
	}
	
	public List<Compras> mockEntityList(){
		List<Compras> compras = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			compras.add(mockEntity(i));
		}
		return compras;
	}
	
	public List<ComprasVO> mockVOList(){
		List<ComprasVO> compras = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			compras.add(mockVO(i));
		}
		return compras;
	}
}
