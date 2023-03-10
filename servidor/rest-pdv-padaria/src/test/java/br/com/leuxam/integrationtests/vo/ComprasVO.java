package br.com.leuxam.integrationtests.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ComprasVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double valorTotal;
	private FornecedorVO fornecedor;
	private Set<CompraEstoqueVO> items = new HashSet<>();
	
	public ComprasVO() {}

	public ComprasVO(Long id, Double valorTotal, FornecedorVO fornecedor) {
		this.id = id;
		this.valorTotal = valorTotal;
		this.fornecedor = fornecedor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FornecedorVO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorVO fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	@JsonIgnore
	public Set<CompraEstoqueVO> getItems(){
		return items;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, valorTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComprasVO other = (ComprasVO) obj;
		return Objects.equals(id, other.id) && Objects.equals(valorTotal, other.valorTotal);
	}
}
