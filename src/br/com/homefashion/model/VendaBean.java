package br.com.homefashion.model;

import java.util.Date;

public class VendaBean {

	private Integer id;
	private Double valor;
	private Date data;
	private Integer qtd;
	private ClienteBean cliente;
	private Double total_pago;
	private Double em_aberto;
	private Double soma_total;
	private Double receber_geral;
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

	public Double getTotal_pago() {
		return total_pago;
	}

	public void setTotal_pago(Double total_pago) {
		this.total_pago = total_pago;
	}

	public Double getEm_aberto() {
		return em_aberto;
	}

	public void setEm_aberto(Double em_aberto) {
		this.em_aberto = em_aberto;
	}

	public Double getSoma_total() {
		return soma_total;
	}

	public void setSoma_total(Double soma_total) {
		this.soma_total = soma_total;
	}

	public Double getReceber_geral() {
		return receber_geral;
	}

	public void setReceber_geral(Double receber_geral) {
		this.receber_geral = receber_geral;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
