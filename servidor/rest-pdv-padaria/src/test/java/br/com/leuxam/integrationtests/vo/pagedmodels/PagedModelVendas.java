package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.VendasVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelVendas {

	@XmlElement(name = "content")
	private List<VendasVO> content;

	public PagedModelVendas() {
	}

	public List<VendasVO> getContent() {
		return content;
	}

	public void setContent(List<VendasVO> content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PagedModelVendas other = (PagedModelVendas) obj;
		return Objects.equals(content, other.content);
	}
}
