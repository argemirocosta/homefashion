package br.com.homefashion.controller;

import br.com.homefashion.dao.VendaDAO;
import br.com.homefashion.model.BuscaRelatorio;
import br.com.homefashion.model.Pagamento;
import br.com.homefashion.model.VendaBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.homefashion.shared.Dialogs;
import br.com.homefashion.util.JSFUtil;

@ViewScoped
@ManagedBean
public class VendaController extends Dialogs {

	private VendaBean venda;
	private List<VendaBean> listaVendas;
	private Pagamento pagamento;
	private List<Pagamento> listaPagamentos;
	private Double emAberto;
	private List<VendaBean> listaVendasPorCliente;
	private List<VendaBean> listaVendasEmAberto;
	private BuscaRelatorio busca;
	private Double somaGeral;
	private Double receberGeral;
	private Double receberGeralTotal;
	private VendaDAO vDao = new VendaDAO();

	public VendaController() {
		venda = new VendaBean();
		pagamento = new Pagamento();
		listaVendas = new ArrayList<VendaBean>();
		venda.getCliente().setId(0);
		venda.setId(0);
		emAberto = 0.0;
		listaVendasPorCliente = new ArrayList<VendaBean>();
		busca = new BuscaRelatorio();
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
			JSFUtil.abrirDialog(DIALOG_PAGAMENTOS);
		} else {
			JSFUtil.adicionarMensagemAdvertencia("Essa venda já foi paga!", "Aviso");
		}
	}

	public void insereVenda() {
		boolean cadastrou = vDao.insereVenda(venda);

		if (cadastrou) {
			limparCampos();
			JSFUtil.fecharDialog(DIALOG_VENDER);
			JSFUtil.adicionarMensagemSucesso("Venda realizada com sucesso!", "Sucesso");
		} else {
			JSFUtil.adicionarMensagemErro("Erro ao realizar Venda", "Erro");
		}

	}

	public void inserePagamento() {
		int pagamentoVenda = vDao.semPagamentos(venda.getId());
		emAberto = vDao.valorEmAberto(venda.getId());

		if (pagamento.getValor() > emAberto) {
			JSFUtil.adicionarMensagemAdvertencia("Pagamento maior que a venda não é permitido!", "Aviso");
		} else if (emAberto > 0 || pagamentoVenda == 0) {
			boolean cadastrou = vDao.inserePagamento(venda, pagamento);

			if (cadastrou) {
				listaPagamentos = null;
				getListaPagamentos();
				listaVendas = null;
				getListaVendas();

				JSFUtil.adicionarMensagemSucesso("Pagamento realizado com sucesso", "Sucesso");
				pagamento.setValor(null);
			} else {
				JSFUtil.adicionarMensagemErro("Erro ao realizar pagamento!", "Erro");
			}

		} else {
			JSFUtil.adicionarMensagemAdvertencia("Essa venda já foi paga!", "Aviso");

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

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public List<Pagamento> getListaPagamentos() {
		if (listaPagamentos == null) {
			listaPagamentos = vDao.listarPagamentos(venda.getId());
		}
		return listaPagamentos;
	}

	public void setListaPagamentos(List<Pagamento> listaPagamentos) {
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

	public BuscaRelatorio getBusca() {
		return busca;
	}

	public void setBusca(BuscaRelatorio busca) {
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