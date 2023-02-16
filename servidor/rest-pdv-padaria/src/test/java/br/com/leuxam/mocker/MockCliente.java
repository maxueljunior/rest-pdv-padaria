package br.com.leuxam.mocker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.leuxam.model.Cliente;

public class MockCliente {
	
	public Cliente mockEntity() {
		return mockEntity(0);
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
	
	public List<Cliente> mockEntityList(){
		List<Cliente> clientes = new ArrayList<>();
		for(int i=0;i<14;i++) {
			clientes.add(mockEntity(i));
		}
		return clientes;
	}
	
}
