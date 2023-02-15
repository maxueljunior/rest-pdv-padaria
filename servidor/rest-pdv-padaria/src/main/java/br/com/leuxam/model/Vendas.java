package br.com.leuxam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.leuxam.model.enums.CondicaoPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendas")
public class Vendas implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date dataVenda;
	
	@Column(nullable = false)
	private Double valorUnitario;
	
	@Column(nullable = false)
	private Double valorTotal;
	
	@Column(nullable = false)
	private Double quantidade;
	
	@Column(nullable = false)
	private Integer condicaoPagamento;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public Vendas() {}

	public Vendas(Long id, Date dataVenda, Double valorUnitario, Double valorTotal, Double quantidade,
			CondicaoPagamento condicaoPagamento, Cliente cliente) {
		this.id = id;
		this.dataVenda = dataVenda;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
		this.quantidade = quantidade;
		setCondicaoPagamento(condicaoPagamento);
		this.cliente = cliente;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return CondicaoPagamento.valueOf(condicaoPagamento);
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		if(condicaoPagamento != null) {
			this.condicaoPagamento = condicaoPagamento.getCode();
		}
	}

	public Long getId() {
		return id;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, condicaoPagamento, dataVenda, id, quantidade, valorTotal, valorUnitario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendas other = (Vendas) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(condicaoPagamento, other.condicaoPagamento)
				&& Objects.equals(dataVenda, other.dataVenda) && Objects.equals(id, other.id)
				&& Objects.equals(quantidade, other.quantidade) && Objects.equals(valorTotal, other.valorTotal)
				&& Objects.equals(valorUnitario, other.valorUnitario);
	}
}
