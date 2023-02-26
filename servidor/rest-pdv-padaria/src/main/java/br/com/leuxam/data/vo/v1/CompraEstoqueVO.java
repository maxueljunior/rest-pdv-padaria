package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.github.dozermapper.core.Mapping;

import br.com.leuxam.data.pk.vo.v1.CompraEstoquePKVO;

public class CompraEstoqueVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Mapping("id")
	private CompraEstoquePKVO key = new CompraEstoquePKVO();
	private Double preco;
	private Double quantidade;
	
	public CompraEstoqueVO() {}
	
	public CompraEstoqueVO(ComprasVO compra, EstoqueVO estoque, Double preco, Double quantidade) {
		key.setCompras(compra);
		key.setEstoque(estoque);
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public ComprasVO getCompras() {
		return key.getCompras();
	}
	
	public void setCompras(ComprasVO compra) {
		key.setCompras(compra);
	}
	
	public EstoqueVO getEstoque() {
		return key.getEstoque();
	}
	
	public void setEstoque(EstoqueVO estoque) {
		key.setEstoque(estoque);
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
		CompraEstoqueVO other = (CompraEstoqueVO) obj;
		return Objects.equals(key, other.key);
	}
}
