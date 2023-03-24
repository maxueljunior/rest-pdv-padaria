package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.EstoqueVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelEstoque {

	@XmlElement(name = "content")
	private List<EstoqueVO> content;

	public PagedModelEstoque() {
	}

	public List<EstoqueVO> getContent() {
		return content;
	}

	public void setContent(List<EstoqueVO> content) {
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
		PagedModelEstoque other = (PagedModelEstoque) obj;
		return Objects.equals(content, other.content);
	}
}
