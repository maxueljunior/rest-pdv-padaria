package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 45)
	private String razaoSocial;
	
	@Column(nullable = false, length = 18)
	private String cnpj;
	
	@Column(nullable = false, length = 13)
	private String telefone;
	
	@Column(nullable = false, length = 45)
	private String nomeDoContato;
	
	public Fornecedor() {}

	public Fornecedor(Long id, String razaoSocial, String cnpj, String telefone, String nomeDoContato) {
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
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(id, other.id)
				&& Objects.equals(nomeDoContato, other.nomeDoContato) && Objects.equals(razaoSocial, other.razaoSocial)
				&& Objects.equals(telefone, other.telefone);
	}
}
