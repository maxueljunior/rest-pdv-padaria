package br.com.leuxam.integrationtests.vo;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leuxam.integrationtests.pk.vo.VendaEstoquePKVO;


public class VendaEstoqueVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private VendaEstoquePKVO id = new VendaEstoquePKVO();
	private Double quantidade;
	private Double preco;
	
	public VendaEstoqueVO() {}

	public VendaEstoqueVO(VendasVO venda, EstoqueVO estoque, Double quantidade, Double preco) {
		id.setVendas(venda);
		id.setEstoque(estoque);
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public VendasVO getVendas() {
		return id.getVendas();
	}
	
	public void setVendas(VendasVO venda) {
		id.setVendas(venda);
	}
	
	public EstoqueVO getEstoque() {
		return id.getEstoque();
	}
	
	public void setEstoque(EstoqueVO estoque) {
		id.setEstoque(estoque);
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		VendaEstoqueVO other = (VendaEstoqueVO) obj;
		return Objects.equals(id, other.id);
	}
}
