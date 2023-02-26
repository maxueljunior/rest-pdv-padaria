package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dozermapper.core.Mapping;

public class EstoqueVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	private Long key;
	private String descricao;
	private Double quantidade;
	private Date dataCompra;
	private Date dataValidade;
	private String unidade;
	private Set<VendaEstoqueVO> items = new HashSet<>();
	private Set<CompraEstoqueVO> compras = new HashSet<>();
	
	public EstoqueVO() {}
	
	public EstoqueVO(Long key, String descricao, Double quantidade, Date dataCompra, Date dataValidade, String unidade) {
		this.key = key;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.dataCompra = dataCompra;
		this.dataValidade = dataValidade;
		this.unidade = unidade;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
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
	public Set<VendasVO> getVendas(){
		Set<VendasVO> set = new HashSet<>();
		for(VendaEstoqueVO x : items) {
			set.add(x.getVendas());
		}
		return set;
	}
	
	@JsonIgnore
	public Set<ComprasVO> getCompras(){
		Set<ComprasVO> set = new HashSet<>();
		for (CompraEstoqueVO x : compras) {
			set.add(x.getCompras());
		}
		return set;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCompra, dataValidade, descricao, key, quantidade, unidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstoqueVO other = (EstoqueVO) obj;
		return Objects.equals(dataCompra, other.dataCompra) && Objects.equals(dataValidade, other.dataValidade)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(key, other.key)
				&& Objects.equals(quantidade, other.quantidade) && Objects.equals(unidade, other.unidade);
	}
}
