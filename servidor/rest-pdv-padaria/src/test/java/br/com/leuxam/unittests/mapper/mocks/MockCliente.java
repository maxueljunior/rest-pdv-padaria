package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.leuxam.data.vo.v1.ClienteVO;
import br.com.leuxam.model.Cliente;

public class MockCliente {
	
	public Cliente mockEntity() {
		return mockEntity(0);
	}
	
	public ClienteVO mockVO() {
		return mockVO(0);
	}
	
	public Cliente mockEntity(Integer number) {
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(new Date());
		cliente.setEndereco("Endereço" + number);
		cliente.setId(number.longValue());
		cliente.setLucratividade(number.doubleValue());
		cliente.setNome("Nome" + number);
		cliente.setSexo(((number % 2) == 0 ? "M" : "F"));
		cliente.setSobrenome("Sobrenome" + number);
		cliente.setTelefone("Telefone" + number);
		return cliente;
	}
	
	public ClienteVO mockVO(Integer number) {
		ClienteVO cliente = new ClienteVO();
		cliente.setDataNascimento(new Date());
		cliente.setEndereco("Endereço" + number);
		cliente.setId(number.longValue());
		cliente.setLucratividade(number.doubleValue());
		cliente.setNome("Nome" + number);
		cliente.setSexo(((number % 2) == 0 ? "M" : "F"));
		cliente.setSobrenome("Sobrenome" + number);
		cliente.setTelefone("Telefone" + number);
		return cliente;
	}
	
	public List<Cliente> mockEntityList(){
		List<Cliente> clientes = new ArrayList<>();
		for(int i=0;i<14;i++) {
			clientes.add(mockEntity(i));
		}
		return clientes;
	}
	
	public List<ClienteVO> mockVOList(){
		List<ClienteVO> clientes = new ArrayList<>();
		for(int i=0;i<14;i++) {
			clientes.add(mockVO(i));
		}
		return clientes;
	}
	
}
