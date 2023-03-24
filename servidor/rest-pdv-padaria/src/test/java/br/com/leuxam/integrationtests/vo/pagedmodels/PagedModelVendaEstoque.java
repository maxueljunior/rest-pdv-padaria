package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.VendaEstoqueVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelVendaEstoque {

	@XmlElement(name = "content")
	private List<VendaEstoqueVO> content;

	public PagedModelVendaEstoque() {
	}

	public List<VendaEstoqueVO> getContent() {
		return content;
	}

	public void setContent(List<VendaEstoqueVO> content) {
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
		PagedModelVendaEstoque other = (PagedModelVendaEstoque) obj;
		return Objects.equals(content, other.content);
	}
}
