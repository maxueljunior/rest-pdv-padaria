package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.VendaEstoqueVO;

public class VendaEstoqueEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("vendaEstoqueVOList")
	private List<VendaEstoqueVO> vendaEstoques;

	public VendaEstoqueEmbeddedVO() {
	}

	public List<VendaEstoqueVO> getVendaEstoques() {
		return vendaEstoques;
	}

	public void setVendaEstoques(List<VendaEstoqueVO> vendaEstoques) {
		this.vendaEstoques = vendaEstoques;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vendaEstoques);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaEstoqueEmbeddedVO other = (VendaEstoqueEmbeddedVO) obj;
		return Objects.equals(vendaEstoques, other.vendaEstoques);
	}
}
