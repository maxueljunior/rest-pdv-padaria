package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.EstoqueVO;

public class EstoqueEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("estoqueVOList")
	private List<EstoqueVO> estoques;

	public EstoqueEmbeddedVO() {
	}

	public List<EstoqueVO> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<EstoqueVO> estoques) {
		this.estoques = estoques;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estoques);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstoqueEmbeddedVO other = (EstoqueEmbeddedVO) obj;
		return Objects.equals(estoques, other.estoques);
	}
}
