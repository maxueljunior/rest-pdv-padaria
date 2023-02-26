package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dozermapper.core.Mapping;

public class ComprasVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Mapping("id")
	private Long key;
	private Double valorTotal;
	private FornecedorVO fornecedor;
	private Set<CompraEstoqueVO> items = new HashSet<>();
	
	public ComprasVO() {}

	public ComprasVO(Long key, Double valorTotal, FornecedorVO fornecedor) {
		this.key = key;
		this.valorTotal = valorTotal;
		this.fornecedor = fornecedor;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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
		return Objects.hash(key, valorTotal);
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
		return Objects.equals(key, other.key) && Objects.equals(valorTotal, other.valorTotal);
	}
}
