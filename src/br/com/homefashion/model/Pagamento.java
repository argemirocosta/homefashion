package br.com.homefashion.model;

import java.util.Date;

public class Pagamento {

	private Integer id;
	private Double valor;
	private Date data;
	private Venda venda;

	public Pagamento() {
		venda = new Venda();
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

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}