package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.FornecedorVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelFornecedor {
	
	@XmlElement(name = "content")
	private List<FornecedorVO> content;

	public PagedModelFornecedor() {
	}

	public List<FornecedorVO> getContent() {
		return content;
	}

	public void setContent(List<FornecedorVO> content) {
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
		PagedModelFornecedor other = (PagedModelFornecedor) obj;
		return Objects.equals(content, other.content);
	}
}
