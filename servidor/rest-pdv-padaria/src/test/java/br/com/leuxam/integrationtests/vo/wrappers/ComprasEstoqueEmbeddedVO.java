package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.CompraEstoqueVO;

public class ComprasEstoqueEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("compraEstoqueVOList")
	private List<CompraEstoqueVO> compraEstoque;

	public ComprasEstoqueEmbeddedVO() {
	}

	public List<CompraEstoqueVO> getCompraEstoque() {
		return compraEstoque;
	}

	public void setCompraEstoque(List<CompraEstoqueVO> compraEstoque) {
		this.compraEstoque = compraEstoque;
	}

	@Override
	public int hashCode() {
		return Objects.hash(compraEstoque);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComprasEstoqueEmbeddedVO other = (ComprasEstoqueEmbeddedVO) obj;
		return Objects.equals(compraEstoque, other.compraEstoque);
	}
}
