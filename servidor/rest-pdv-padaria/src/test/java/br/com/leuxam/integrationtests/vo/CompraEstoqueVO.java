package br.com.leuxam.integrationtests.vo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leuxam.integrationtests.pk.vo.CompraEstoquePKVO;


public class CompraEstoqueVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private CompraEstoquePKVO id = new CompraEstoquePKVO();
	private Double preco;
	private Double quantidade;
	
	public CompraEstoqueVO() {}
	
	public CompraEstoqueVO(ComprasVO compra, EstoqueVO estoque, Double preco, Double quantidade) {
		id.setCompras(compra);
		id.setEstoque(estoque);
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public ComprasVO getCompras() {
		return id.getCompras();
	}
	
	public void setCompras(ComprasVO compra) {
		id.setCompras(compra);
	}
	
	public EstoqueVO getEstoque() {
		return id.getEstoque();
	}
	
	public void setEstoque(EstoqueVO estoque) {
		id.setEstoque(estoque);
	}

	public Double getPreco() {
		return preco;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraEstoqueVO other = (CompraEstoqueVO) obj;
		return Objects.equals(id, other.id);
	}
}
