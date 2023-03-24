package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.CompraEstoqueVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelCompraEstoque {
	
	@XmlElement(name = "content")
	private List<CompraEstoqueVO> content;

	public PagedModelCompraEstoque() {
	}

	public List<CompraEstoqueVO> getContent() {
		return content;
	}

	public void setContent(List<CompraEstoqueVO> content) {
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
		PagedModelCompraEstoque other = (PagedModelCompraEstoque) obj;
		return Objects.equals(content, other.content);
	}
}