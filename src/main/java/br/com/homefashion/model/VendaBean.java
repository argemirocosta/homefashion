package br.com.homefashion.model;

import java.util.Date;

public class VendaBean {

	private Integer id;
	private Double valor;
	private Date data;
	private Integer qtd;
	private ClienteBean cliente;
	private Double totalPago;
	private Double emAberto;
	private Double somaTotal;
	private Double receberGeral;
	private String situacao;

	public VendaBean() {
		cliente = new ClienteBean();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public ClienteBean getCliente() {
		return cliente;
	}

	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}

	public Double getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(Double totalPago) {
		this.totalPago = totalPago;
	}

	public Double getEmAberto() {
		return emAberto;
	}

	public void setEmAberto(Double emAberto) {
		this.emAberto = emAberto;
	}

	public Double getSomaTotal() {
		return somaTotal;
	}

	public void setSomaTotal(Double somaTotal) {
		this.somaTotal = somaTotal;
	}

	public Double getReceberGeral() {
		return receberGeral;
	}

	public void setReceberGeral(Double receberGeral) {
		this.receberGeral = receberGeral;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}