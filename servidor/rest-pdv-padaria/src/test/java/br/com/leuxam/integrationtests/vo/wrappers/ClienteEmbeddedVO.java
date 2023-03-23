package br.com.leuxam.integrationtests.vo.wrappers;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.leuxam.integrationtests.vo.ClienteVO;

public class ClienteEmbeddedVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("clienteVOList")
	private List<ClienteVO> clientes;

	public ClienteEmbeddedVO() {}

	public List<ClienteVO> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteVO> clientes) {
		this.clientes = clientes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteEmbeddedVO other = (ClienteEmbeddedVO) obj;
		return Objects.equals(clientes, other.clientes);
	}
}
