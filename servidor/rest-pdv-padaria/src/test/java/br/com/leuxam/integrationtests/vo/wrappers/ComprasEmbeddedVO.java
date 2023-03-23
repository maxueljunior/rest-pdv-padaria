package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.ComprasVO;

public class ComprasEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("comprasVOList")
	private List<ComprasVO> compras;

	public ComprasEmbeddedVO() {
	}

	public List<ComprasVO> getCompras() {
		return compras;
	}

	public void setCompras(List<ComprasVO> compras) {
		this.compras = compras;
	}

	@Override
	public int hashCode() {
		return Objects.hash(compras);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComprasEmbeddedVO other = (ComprasEmbeddedVO) obj;
		return Objects.equals(compras, other.compras);
	}
}
