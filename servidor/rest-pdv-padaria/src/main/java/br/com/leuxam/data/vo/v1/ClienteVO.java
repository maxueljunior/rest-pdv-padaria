package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({"id", "nome", "sobrenome", "telefone", "dataNascimento", "endereco", "lucratividade", "sexo", "vendas"})
public class ClienteVO extends RepresentationModel<ClienteVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private String nome;
	private String sobrenome;
	private String telefone;
	private Date dataNascimento;
	private String endereco;
	private Double lucratividade;
	private String sexo;
	
	@JsonIgnore
	private List<VendasVO> vendas = new ArrayList<>();
	
	public ClienteVO() {}

	public ClienteVO(Long key, String nome, String sobrenome, String telefone, Date dataNascimento, String endereco,
			Double lucratividade, String sexo) {
		this.key = key;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.lucratividade = lucratividade;
		this.sexo = sexo;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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

	public List<VendasVO> getVendas() {
		return vendas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, endereco, key, lucratividade, nome, sexo, sobrenome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteVO other = (ClienteVO) obj;
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(endereco, other.endereco)
				&& Objects.equals(key, other.key) && Objects.equals(lucratividade, other.lucratividade)
				&& Objects.equals(nome, other.nome) && Objects.equals(sexo, other.sexo)
				&& Objects.equals(sobrenome, other.sobrenome) && Objects.equals(telefone, other.telefone);
	}
}
