package br.com.leuxam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String nome;
	
	@Column(nullable = false, length = 80)
	private String sobrenome;
	
	@Column(nullable = false, length = 80)
	private String telefone;
	
	@Column(nullable = false)
	private Date dataNascimento;
	
	@Column(nullable = false, length = 80)
	private String endereco;
	
	@Column(nullable = false)
	private Double lucratividade;
	
	@Column(nullable = false, length = 1)
	private String sexo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private List<Vendas> vendas = new ArrayList<>();
	
	public Cliente() {}

	public Cliente(Long id, String nome, String sobrenome, String telefone, Date dataNascimento, String endereco,
			Double lucratividade, String sexo) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.lucratividade = lucratividade;
		this.sexo = sexo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Double getLucratividade() {
		return lucratividade;
	}

	public void setLucratividade(Double lucratividade) {
		this.lucratividade = lucratividade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Vendas> getVendas() {
		return vendas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, endereco, id, lucratividade, nome, sexo, sobrenome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(id, other.id) && Objects.equals(lucratividade, other.lucratividade)
				&& Objects.equals(nome, other.nome) && Objects.equals(sexo, other.sexo)
				&& Objects.equals(sobrenome, other.sobrenome) && Objects.equals(telefone, other.telefone);
	}
}
