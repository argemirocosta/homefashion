package br.com.homefashion.model;

import java.util.Date;

public class PagamentoBean {

	private Integer id;
	private Double valor;
	private Date data;
	private VendaBean venda;

	public PagamentoBean() {
		venda = new VendaBean();
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

	public VendaBean getVenda() {
		return venda;
	}

	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}

}
