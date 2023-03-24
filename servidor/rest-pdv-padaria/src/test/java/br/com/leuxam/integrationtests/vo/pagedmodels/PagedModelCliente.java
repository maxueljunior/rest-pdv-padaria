package br.com.leuxam.integrationtests.vo.pagedmodels;

import java.util.List;
import java.util.Objects;

import br.com.leuxam.integrationtests.vo.ClienteVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagedModelCliente {
	
	@XmlElement(name = "content")
	private List<ClienteVO> content;

	public PagedModelCliente() {
	}

	public List<ClienteVO> getContent() {
		return content;
	}

	public void setContent(List<ClienteVO> content) {
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
		PagedModelCliente other = (PagedModelCliente) obj;
		return Objects.equals(content, other.content);
	}
}
