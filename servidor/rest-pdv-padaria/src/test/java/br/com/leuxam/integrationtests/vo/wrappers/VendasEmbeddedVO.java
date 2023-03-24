package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.VendasVO;

public class VendasEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("vendasVOList")
	private List<VendasVO> vendas;

	public VendasEmbeddedVO() {
	}

	public List<VendasVO> getVendas() {
		return vendas;
	}

	public void setVendas(List<VendasVO> vendas) {
		this.vendas = vendas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vendas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendasEmbeddedVO other = (VendasEmbeddedVO) obj;
		return Objects.equals(vendas, other.vendas);
	}
}
