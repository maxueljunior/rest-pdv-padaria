package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({"id", "razaoSocial", "cnpj", "telefone", "nomeDoContato", "compras"})
public class FornecedorVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private String razaoSocial;
	private String cnpj;
	private String telefone;
	private String nomeDoContato;
	private List<ComprasVO> compras = new ArrayList<>();
	
	public FornecedorVO() {}

	public FornecedorVO(Long key, String razaoSocial, String cnpj, String telefone, String nomeDoContato) {
		this.key = key;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.nomeDoContato = nomeDoContato;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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
	
	@JsonIgnore
	public List<ComprasVO> getCompras() {
		return compras;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, key, nomeDoContato, razaoSocial, telefone);
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
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(key, other.key)
				&& Objects.equals(nomeDoContato, other.nomeDoContato) && Objects.equals(razaoSocial, other.razaoSocial)
				&& Objects.equals(telefone, other.telefone);
	}
}
