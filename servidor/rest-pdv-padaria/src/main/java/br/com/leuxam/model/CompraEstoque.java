package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leuxam.model.pk.CompraEstoquePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_compra_estoque")
public class CompraEstoque implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CompraEstoquePK id = new CompraEstoquePK();
	
	private Double preco;
	private Double quantidade;
	
	public CompraEstoque() {}
	
	public CompraEstoque(Compras compra, Estoque estoque, Double preco, Double quantidade) {
		id.setCompras(compra);
		id.setEstoque(estoque);
		this.preco = preco;
		this.quantidade = quantidade;
	}

	public Compras getCompras() {
		return id.getCompras();
	}
	
	public void setCompras(Compras compra) {
		id.setCompras(compra);
	}
	
	public Estoque getEstoque() {
		return id.getEstoque();
	}
	
	public void setEstoque(Estoque estoque) {
		id.setEstoque(estoque);
	}

	public Double getPreco() {
		return preco;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompraEstoque other = (CompraEstoque) obj;
		return Objects.equals(id, other.id);
	}
}
