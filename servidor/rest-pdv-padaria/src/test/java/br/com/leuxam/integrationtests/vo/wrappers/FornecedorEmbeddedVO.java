package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.FornecedorVO;

public class FornecedorEmbeddedVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("fornecedorVOList")
	private List<FornecedorVO> fornecedores;

	public FornecedorEmbeddedVO() {
	}

	public List<FornecedorVO> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<FornecedorVO> fornecedores) {
		this.fornecedores = fornecedores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fornecedores);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FornecedorEmbeddedVO other = (FornecedorEmbeddedVO) obj;
		return Objects.equals(fornecedores, other.fornecedores);
	}
}
