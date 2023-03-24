package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperVendasVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private VendasEmbeddedVO embedded;

	public WrapperVendasVO() {
	}

	public VendasEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(VendasEmbeddedVO embedded) {
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
		WrapperVendasVO other = (WrapperVendasVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
}
