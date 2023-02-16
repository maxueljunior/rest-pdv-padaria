package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estoque")
public class Estoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 80)
	private String descricao;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private Date dataCompra;
	
	@Column(nullable = false)
	private Date dataValidade;
	
	@Column(nullable = false, length = 2)
	private String unidade;
	
	@OneToMany(mappedBy = "id.estoque")
	private Set<VendaEstoque> items = new HashSet<>();
	
	@OneToMany(mappedBy = "id.estoque")
	private Set<CompraEstoque> compras = new HashSet<>();
	
	public Estoque() {}
	
	public Estoque(Long id, String descricao, Integer quantidade, Date dataCompra, Date dataValidade, String unidade) {
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.dataCompra = dataCompra;
		this.dataValidade = dataValidade;
		this.unidade = unidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@JsonIgnore
	public Set<Vendas> getVendas(){
		Set<Vendas> set = new HashSet<>();
		for(VendaEstoque x : items) {
			set.add(x.getVendas());
		}
		return set;
	}
	
	@JsonIgnore
	public Set<Compras> getCompras(){
		Set<Compras> set = new HashSet<>();
		for (CompraEstoque x : compras) {
			set.add(x.getCompras());
		}
		return set;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCompra, dataValidade, descricao, id, quantidade, unidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		return Objects.equals(dataCompra, other.dataCompra) && Objects.equals(dataValidade, other.dataValidade)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(quantidade, other.quantidade) && Objects.equals(unidade, other.unidade);
	}
}
