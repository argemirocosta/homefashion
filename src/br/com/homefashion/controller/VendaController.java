package br.com.homefashion.controller;

import br.com.homefashion.dao.VendaDAO;
import br.com.homefashion.model.BuscaRelatorioBean;
import br.com.homefashion.model.PagamentoBean;
import br.com.homefashion.model.VendaBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean
public class VendaController {

	private VendaBean venda;
	private List<VendaBean> listaVendas;
	private PagamentoBean pagamento;
	private List<PagamentoBean> listaPagamentos;
	private Double emAberto;
	private List<VendaBean> listaVendasPorCliente;
	private List<VendaBean> listaVendasEmAberto;
	private BuscaRelatorioBean busca;
	private Double somaGeral;
	private Double receberGeral;
	private Double receberGeralTotal;
	private VendaDAO vDao = new VendaDAO();

	public VendaController() {
		venda = new VendaBean();
		pagamento = new PagamentoBean();
		listaVendas = new ArrayList<VendaBean>();
		venda.getCliente().setId(0);
		venda.setId(0);
		emAberto = 0.0;
		listaVendasPorCliente = new ArrayList<VendaBean>();
		busca = new BuscaRelatorioBean();
		busca.setPeriodoinicial(new java.util.Date(System.currentTimeMillis()));
		busca.setPeriodofinal(new java.util.Date(System.currentTimeMillis()));
		somaGeral = 0.0;
		listaVendasEmAberto = new ArrayList<VendaBean>();
	}

	public void limparCampos() {
		venda.setQtd(null);
		venda.setValor(null);
		listaVendas = null;
		getListaVendas();
	}

	public void limparListaPagamentos() {
		listaPagamentos = null;
		getListaPagamentos();
	}

	public void limparRelatorios() {
		listaVendasPorCliente = null;
		getListaVendasPorCliente();
		listaVendasEmAberto = null;
		getListaVendasEmAberto();

	}

	public void verificarVendaPaga() {	
		emAberto = vDao.valorEmAberto(venda.getId());
		if (emAberto > 0) {
			RequestContext.getCurrentInstance().execute(
					"PF('dlgPagamentos').show();");
		} else {
			FacesMessage msg = new FacesMessage("Essa venda já foi paga!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}
	}

	public void insereVenda() {
		boolean cadastrou = vDao.insereVenda(venda);

		if (cadastrou) {
			limparCampos();
			RequestContext.getCurrentInstance().execute("PF('dlgVender').hide();");

			FacesMessage msg = new FacesMessage("Venda Realizada");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Erro ao realizar Venda");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);
		}

	}

	public void inserePagamento() {
		int pagamentoVenda = vDao.semPagamentos(venda.getId());
		emAberto = vDao.valorEmAberto(venda.getId());

		if (pagamento.getValor() > emAberto) {
			FacesMessage msg = new FacesMessage("Pagamento maior que a venda não é permitido!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);

		} else if (emAberto > 0 || pagamentoVenda == 0) {
			boolean cadastrou = vDao.inserePagamento(venda, pagamento);

			if (cadastrou) {
				listaPagamentos = null;
				getListaPagamentos();
				listaVendas = null;
				getListaVendas();
				
				FacesMessage msg = new FacesMessage("Pagamento Realizado");
				FacesContext ct = FacesContext.getCurrentInstance();
				ct.addMessage(null, msg);
				pagamento.setValor(null);
			} else {
				FacesMessage msg = new FacesMessage("Erro ao realizar Pagamento");
				FacesContext ct = FacesContext.getCurrentInstance();
				ct.addMessage(null, msg);
			}

		} else {
			FacesMessage msg = new FacesMessage("Essa venda já foi paga!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);

		}

	}

	public void somaGeral() {
		somaGeral = vDao.vendasPorPeriodo(busca);
	}

	public void somaGeralTotal() {
		somaGeral = vDao.vendasTotal();
		receberGeralTotal = somaGeral - receberGeral;
	}

	public void recebidoGeral() {
		receberGeral = vDao.receberGeral();
		somaGeralTotal();
	}

	public VendaBean getVenda() {
		return venda;
	}

	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}

	public List<VendaBean> getListaVendas() {
		if (listaVendas == null) {
			listaVendas = vDao.listarVendas(venda);
		}
		return listaVendas;
	}

	public void setListaVendas(List<VendaBean> listaVendas) {
		this.listaVendas = listaVendas;
	}

	public PagamentoBean getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoBean pagamento) {
		this.pagamento = pagamento;
	}

	public List<PagamentoBean> getListaPagamentos() {
		if (listaPagamentos == null) {
			listaPagamentos = vDao.listarPagamentos(venda.getId());
		}
		return listaPagamentos;
	}

	public void setListaPagamentos(List<PagamentoBean> listaPagamentos) {
		this.listaPagamentos = listaPagamentos;
	}

	public Double getEmAberto() {
		return emAberto;
	}

	public void setEmAberto(Double emAberto) {
		this.emAberto = emAberto;
	}

	public List<VendaBean> getListaVendasPorCliente() {
		if (listaVendasPorCliente == null) {
			listaVendasPorCliente = vDao.vendasPorCliente();
		}
		return listaVendasPorCliente;
	}

	public void setListaVendasPorCliente(List<VendaBean> listaVendasPorCliente) {
		this.listaVendasPorCliente = listaVendasPorCliente;
	}

	public BuscaRelatorioBean getBusca() {
		return busca;
	}

	public void setBusca(BuscaRelatorioBean busca) {
		this.busca = busca;
	}

	public Double getSomaGeral() {
		return somaGeral;
	}

	public void setSomaGeral(Double somaGeral) {
		this.somaGeral = somaGeral;
	}

	public List<VendaBean> getListaVendasEmAberto() {
		if (listaVendasEmAberto == null) {
			listaVendasEmAberto = vDao.aReceber();

		}
		return listaVendasEmAberto;
	}

	public void setListaVendasEmAberto(List<VendaBean> listaVendasEmAberto) {
		this.listaVendasEmAberto = listaVendasEmAberto;
	}

	public Double getReceberGeral() {
		return receberGeral;
	}

	public void setReceberGeral(Double receberGeral) {
		this.receberGeral = receberGeral;
	}

	public Double getReceberGeralTotal() {
		return receberGeralTotal;
	}

	public void setReceberGeralTotal(Double receberGeralTotal) {
		this.receberGeralTotal = receberGeralTotal;
	}
}