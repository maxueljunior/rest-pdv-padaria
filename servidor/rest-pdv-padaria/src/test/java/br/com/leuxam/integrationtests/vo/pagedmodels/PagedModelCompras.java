package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.ComprasVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelCompras {
	
	@XmlElement(name = "content")
	private List<ComprasVO> content;

	public PagedModelCompras() {
	}

	public List<ComprasVO> getContent() {
		return content;
	}

	public void setContent(List<ComprasVO> content) {
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
		PagedModelCompras other = (PagedModelCompras) obj;
		return Objects.equals(content, other.content);
	}
}
