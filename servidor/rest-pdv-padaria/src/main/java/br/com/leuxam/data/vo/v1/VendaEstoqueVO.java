package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.leuxam.data.pk.vo.v1.VendaEstoquePKVO;

@JsonPropertyOrder({"id", "quantidade", "preco"})
public class VendaEstoqueVO extends RepresentationModel<VendaEstoqueVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@JsonProperty("id")
	@Mapping("id")
	private VendaEstoquePKVO key = new VendaEstoquePKVO();
	private Double quantidade;
	private Double preco;
	
	public VendaEstoqueVO() {}

	public VendaEstoqueVO(VendasVO venda, EstoqueVO estoque, Double quantidade, Double preco) {
		key.setVendas(venda);
		key.setEstoque(estoque);
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public VendasVO getVendas() {
		return key.getVendas();
	}
	
	public void setVendas(VendasVO venda) {
		key.setVendas(venda);
	}
	
	public EstoqueVO getEstoque() {
		return key.getEstoque();
	}
	
	public void setEstoque(EstoqueVO estoque) {
		key.setEstoque(estoque);
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
		return Objects.hash(key);
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
		return Objects.equals(key, other.key);
	}
}
