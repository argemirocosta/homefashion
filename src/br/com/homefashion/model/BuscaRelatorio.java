package br.com.homefashion.model;

import java.util.Date;

public class BuscaRelatorio {

	private Date periodoinicial;
	private Date periodofinal;
	private Cliente cliente;

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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}