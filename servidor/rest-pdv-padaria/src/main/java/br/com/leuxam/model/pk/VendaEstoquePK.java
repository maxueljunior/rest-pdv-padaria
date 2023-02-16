package br.com.leuxam.model.pk;

import java.io.Serializable;
import java.util.Objects;

import br.com.leuxam.model.Estoque;
import br.com.leuxam.model.Vendas;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class VendaEstoquePK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "venda_id")
	private Vendas vendas;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Estoque estoque;
	
	public Vendas getVendas() {
		return vendas;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	public void setEstoque(Estoque estoque) {
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
		VendaEstoquePK other = (VendaEstoquePK) obj;
		return Objects.equals(estoque, other.estoque) && Objects.equals(vendas, other.vendas);
	}
}
