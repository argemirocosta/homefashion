package br.com.homefashion.model;

import java.util.Date;

public class BuscaRelatorioBean {

	private Date periodoinicial;
	private Date periodofinal;
	private ClienteBean cliente;

	public Date getPeriodoinicial() {
		return periodoinicial;
	}

	public void setPeriodoinicial(Date periodoinicial) {
		this.periodoinicial = periodoinicial;
	}

	public Date getPeriodofinal() {
		return periodofinal;
	}

	public void setPeriodofinal(Date periodofinal) {
		this.periodofinal = periodofinal;
	}

	public ClienteBean getCliente() {
		return cliente;
	}

	public void setCliente(ClienteBean cliente) {
		this.cliente = cliente;
	}
}