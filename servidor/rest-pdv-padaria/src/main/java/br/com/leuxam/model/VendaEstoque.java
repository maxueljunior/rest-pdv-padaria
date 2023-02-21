package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leuxam.model.pk.VendaEstoquePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_venda_estoque")
public class VendaEstoque implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private VendaEstoquePK id = new VendaEstoquePK();
	
	private Double quantidade;
	private Double preco;
	
	public VendaEstoque() {}

	public VendaEstoque(Vendas venda, Estoque estoque, Double quantidade, Double preco) {
		id.setVendas(venda);
		id.setEstoque(estoque);
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public Vendas getVendas() {
		return id.getVendas();
	}
	
	public void setVendas(Vendas venda) {
		id.setVendas(venda);
	}
	
	public Estoque getEstoque() {
		return id.getEstoque();
	}
	
	public void setEstoque(Estoque estoque) {
		id.setEstoque(estoque);
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		VendaEstoque other = (VendaEstoque) obj;
		return Objects.equals(id, other.id);
	}
}
