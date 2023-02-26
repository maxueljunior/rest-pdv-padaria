package br.com.leuxam.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.leuxam.data.vo.v1.FornecedorVO;
import br.com.leuxam.model.Fornecedor;

public class MockFornecedor {
	
	public Fornecedor mockEntity() {
		return mockEntity(0);
	}
	
	public FornecedorVO mockVO() {
		return mockVO(0);
	}
	
	public Fornecedor mockEntity(Integer number) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setCnpj("CNPJ"+number);
		fornecedor.setId(number.longValue());
		fornecedor.setNomeDoContato("Contato"+number);
		fornecedor.setRazaoSocial("Razao"+number);
		fornecedor.setTelefone("Telefone"+number);
		return fornecedor;
	}
	
	public FornecedorVO mockVO(Integer number) {
		FornecedorVO fornecedor = new FornecedorVO();
		fornecedor.setCnpj("CNPJ"+number);
		fornecedor.setKey(number.longValue());
		fornecedor.setNomeDoContato("Contato"+number);
		fornecedor.setRazaoSocial("Razao"+number);
		fornecedor.setTelefone("Telefone"+number);
		return fornecedor;
	}
	
	public List<Fornecedor> mockEntityList(){
		List<Fornecedor> fornecedores = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			fornecedores.add(mockEntity(i));
		}
		return fornecedores;
	}
	
	public List<FornecedorVO> mockVOList(){
		List<FornecedorVO> fornecedores = new ArrayList<>();
		for(int i = 0; i<14; i++) {
			fornecedores.add(mockVO(i));
		}
		return fornecedores;
	}
}
