package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.data.vo.v1.VendasVO;
import br.com.leuxam.model.Cliente;
import br.com.leuxam.model.Vendas;
import br.com.leuxam.model.enums.CondicaoPagamento;

public class MockVendas {
	
	public Vendas mockEntity() {
		return mockEntity(0);
	}
	
	public VendasVO mockVO() {
		return mockVO(0);
	}
	
	public Vendas mockEntity(Integer number) {
		Vendas vendas = new Vendas();
		Cliente cliente = new Cliente(number.longValue(), null, null, null, null, null, null, null);
		vendas.setCliente(cliente);
		vendas.setCondicaoPagamento(CondicaoPagamento.PIX);
		vendas.setDataVenda(new Date());
		vendas.setId(number.longValue());
		vendas.setValorTotal(number.doubleValue());
		return vendas;
	}
	
	public VendasVO mockVO(Integer number) {
		VendasVO vendas = new VendasVO();
		ClienteVO cliente = new ClienteVO(number.longValue(), null, null, null, null, null, null, null);
		vendas.setCliente(cliente);
		vendas.setCondicaoPagamento(CondicaoPagamento.PIX);
		vendas.setDataVenda(new Date());
		vendas.setKey(number.longValue());
		vendas.setValorTotal(number.doubleValue());
		return vendas;
	}
	
	public List<Vendas> mockEntityList(){
		List<Vendas> vendas = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendas.add(mockEntity(i));
		}
		return vendas;
	}
	
	public List<VendasVO> mockVOList(){
		List<VendasVO> vendas = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			vendas.add(mockVO(i));
		}
		return vendas;
	}
}
