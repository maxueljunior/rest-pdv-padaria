package br.com.leuxam.model.pk;

import java.io.Serializable;
import java.util.Objects;

import br.com.leuxam.model.Compras;
import br.com.leuxam.model.Estoque;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CompraEstoquePK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Estoque estoque;
	
	@ManyToOne
	@JoinColumn(name = "compra_id")
	private Compras compras;
	
	public Estoque getEstoque() {
		return estoque;
	}
	public Compras getCompras() {
		return compras;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public void setCompras(Compras compras) {
		this.compras = compras;
	}
	@Override
	public int hashCode() {
		return Objects.hash(compras, estoque);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraEstoquePK other = (CompraEstoquePK) obj;
		return Objects.equals(compras, other.compras) && Objects.equals(estoque, other.estoque);
	}
}
