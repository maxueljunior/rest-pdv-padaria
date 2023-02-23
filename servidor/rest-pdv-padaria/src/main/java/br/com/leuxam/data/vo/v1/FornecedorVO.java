package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FornecedorVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private String telefone;
	private String nomeDoContato;
	private List<ComprasVO> compras = new ArrayList<>();
	
	public FornecedorVO() {}

	public FornecedorVO(Long id, String razaoSocial, String cnpj, String telefone, String nomeDoContato) {
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.nomeDoContato = nomeDoContato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNomeDoContato() {
		return nomeDoContato;
	}

	public void setNomeDoContato(String nomeDoContato) {
		this.nomeDoContato = nomeDoContato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, id, nomeDoContato, razaoSocial, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FornecedorVO other = (FornecedorVO) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(id, other.id)
				&& Objects.equals(nomeDoContato, other.nomeDoContato) && Objects.equals(razaoSocial, other.razaoSocial)
				&& Objects.equals(telefone, other.telefone);
	}
}
