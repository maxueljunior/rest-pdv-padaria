package br.com.leuxam.data.pk.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.leuxam.data.vo.v1.EstoqueVO;
import br.com.leuxam.data.vo.v1.VendasVO;

public class VendaEstoquePKVO extends RepresentationModel<VendaEstoquePKVO> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private VendasVO vendas;
	private EstoqueVO estoque;
	
	public VendasVO getVendas() {
		return vendas;
	}
	public EstoqueVO getEstoque() {
		return estoque;
	}
	public void setVendas(VendasVO vendas) {
		this.vendas = vendas;
	}
	public void setEstoque(EstoqueVO estoque) {
		this.estoque = estoque;
	}
	@Override
	public int hashCode() {
		return Objects.hash(estoque, vendas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaEstoquePKVO other = (VendaEstoquePKVO) obj;
		return Objects.equals(estoque, other.estoque) && Objects.equals(vendas, other.vendas);
	}
}
