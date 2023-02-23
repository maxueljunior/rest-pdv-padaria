package br.com.leuxam.data.pk.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.leuxam.data.vo.v1.ComprasVO;
import br.com.leuxam.data.vo.v1.EstoqueVO;

public class CompraEstoquePKVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private EstoqueVO estoque;
	private ComprasVO compras;
	
	public EstoqueVO getEstoque() {
		return estoque;
	}
	public ComprasVO getCompras() {
		return compras;
	}
	public void setEstoque(EstoqueVO estoque) {
		this.estoque = estoque;
	}
	public void setCompras(ComprasVO compras) {
		this.compras = compras;
	}
	@Override
	public int hashCode() {
		return Objects.hash(compras, estoque);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraEstoquePKVO other = (CompraEstoquePKVO) obj;
		return Objects.equals(compras, other.compras) && Objects.equals(estoque, other.estoque);
	}
}
