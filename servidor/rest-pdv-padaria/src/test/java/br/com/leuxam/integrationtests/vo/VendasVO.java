package br.com.leuxam.integrationtests.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.leuxam.model.enums.CondicaoPagamento;

public class VendasVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date dataVenda;
	private Double valorTotal;
	private Integer condicaoPagamento;
	private ClienteVO cliente;
	private Set<VendaEstoqueVO> items = new HashSet<>();
	
	public VendasVO() {}

	public VendasVO(Long id, Date dataVenda, Double valorTotal, CondicaoPagamento condicaoPagamento, ClienteVO cliente) {
		this.id = id;
		this.dataVenda = dataVenda;
		this.valorTotal = valorTotal;
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

	public Double getValorTotal() {
		return valorTotal;
	}

	public ClienteVO getCliente() {
		return cliente;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}
	
	@JsonIgnore
	public Set<VendaEstoqueVO> getItems(){
		return items;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, condicaoPagamento, dataVenda, id, items, valorTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendasVO other = (VendasVO) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(condicaoPagamento, other.condicaoPagamento)
				&& Objects.equals(dataVenda, other.dataVenda) && Objects.equals(id, other.id)
				&& Objects.equals(items, other.items) && Objects.equals(valorTotal, other.valorTotal);
	}
}
