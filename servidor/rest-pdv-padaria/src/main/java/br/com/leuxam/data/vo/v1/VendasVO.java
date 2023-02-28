package br.com.leuxam.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.leuxam.model.enums.CondicaoPagamento;

@JsonPropertyOrder({"id", "dataVenda", "valorTotal", "condicaoPagamento", "cliente", "items"})
public class VendasVO extends RepresentationModel<VendasVO> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private Date dataVenda;
	private Double valorTotal;
	private Integer condicaoPagamento;
	private ClienteVO cliente;
	private Set<VendaEstoqueVO> items = new HashSet<>();
	
	public VendasVO() {}

	public VendasVO(Long key, Date dataVenda, Double valorTotal, CondicaoPagamento condicaoPagamento, ClienteVO cliente) {
		this.key = key;
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

	public Long getKey() {
		return key;
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

	public void setKey(Long key) {
		this.key = key;
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
		return Objects.hash(cliente, condicaoPagamento, dataVenda, key, items, valorTotal);
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
				&& Objects.equals(dataVenda, other.dataVenda) && Objects.equals(key, other.key)
				&& Objects.equals(items, other.items) && Objects.equals(valorTotal, other.valorTotal);
	}
}
