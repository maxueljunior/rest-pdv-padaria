package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperFornecedorVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private FornecedorEmbeddedVO embedded;

	public WrapperFornecedorVO() {
	}

	public FornecedorEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(FornecedorEmbeddedVO embedded) {
		this.embedded = embedded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(embedded);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WrapperFornecedorVO other = (WrapperFornecedorVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
}
