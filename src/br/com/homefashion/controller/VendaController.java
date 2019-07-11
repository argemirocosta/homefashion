package br.com.homefashion.controller;

import br.com.homefashion.dao.VendaDAO;
import br.com.homefashion.model.BuscaRelatorio;
import br.com.homefashion.model.Pagamento;
import br.com.homefashion.model.Venda;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import static br.com.homefashion.shared.Dialogs.*;
import br.com.homefashion.util.JSFUtil;

@ViewScoped
@ManagedBean
public class VendaController {

	private Venda venda;
	private List<Venda> listaVendas;
	private Pagamento pagamento;
	private List<Pagamento> listaPagamentos;
	private Double valorEmAberto;
	private List<Venda> listaVendasPorCliente;
	private List<Venda> listaVendasEmAberto;
	private BuscaRelatorio busca;
	private Double somaGeral;
	private Double valorReceberGeral;
	private Double valorReceberGeralTotal;
	private VendaDAO vendaDAO = new VendaDAO();

	public VendaController() {
		venda = new Venda();
		pagamento = new Pagamento();
		listaVendas = new ArrayList<Venda>();
		venda.getCliente().setId(0);
		venda.setId(0);
		valorEmAberto = 0.0;
		listaVendasPorCliente = new ArrayList<Venda>();
		busca = new BuscaRelatorio();
		busca.setPeriodoinicial(new java.util.Date(System.currentTimeMillis()));
		busca.setPeriodofinal(new java.util.Date(System.currentTimeMillis()));
		somaGeral = 0.0;
		listaVendasEmAberto = new ArrayList<Venda>();
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
		valorEmAberto = vendaDAO.calcularValorEmAberto(venda.getId());
		if (valorEmAberto > 0) {
			JSFUtil.abrirDialog(DIALOG_PAGAMENTOS);
		} else {
			JSFUtil.adicionarMensagemAdvertencia("Essa venda já foi paga!", "Aviso");
		}
	}

	public void inserirVenda() {
		boolean cadastrou = vendaDAO.inserirVenda(venda);

		if (cadastrou) {
			limparCampos();
			JSFUtil.fecharDialog(DIALOG_VENDER);
			JSFUtil.adicionarMensagemSucesso("Venda realizada com sucesso!", "Sucesso");
		} else {
			JSFUtil.adicionarMensagemErro("Erro ao realizar Venda", "Erro");
		}

	}

	public void inserirPagamento() {
		int pagamentoVenda = vendaDAO.verificarSemPagamentos(venda.getId());
		valorEmAberto = vendaDAO.calcularValorEmAberto(venda.getId());

		if (pagamento.getValor() > valorEmAberto) {
			JSFUtil.adicionarMensagemAdvertencia("Pagamento maior que a venda não é permitido!", "Aviso");
		} else if (valorEmAberto > 0 || pagamentoVenda == 0) {
			boolean cadastrou = vendaDAO.inserirPagamento(venda, pagamento);

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
		somaGeral = vendaDAO.consultarVendasPorPeriodo(busca);
	}

	public void somarGeralTotal() {
		somaGeral = vendaDAO.calcularVendasTotal();
		valorReceberGeralTotal = somaGeral - valorReceberGeral;
	}

	public void calcularRecebidoGeral() {
		valorReceberGeral = vendaDAO.calcularValorReceberGeral();
		somarGeralTotal();
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Venda> getListaVendas() {
		if (listaVendas == null) {
			listaVendas = vendaDAO.listarVendas(venda);
		}
		return listaVendas;
	}

	public void setListaVendas(List<Venda> listaVendas) {
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
			listaPagamentos = vendaDAO.listarPagamentos(venda.getId());
		}
		return listaPagamentos;
	}

	public void setListaPagamentos(List<Pagamento> listaPagamentos) {
		this.listaPagamentos = listaPagamentos;
	}

	public Double getValorEmAberto() {
		return valorEmAberto;
	}

	public void setValorEmAberto(Double valorEmAberto) {
		this.valorEmAberto = valorEmAberto;
	}

	public List<Venda> getListaVendasPorCliente() {
		if (listaVendasPorCliente == null) {
			listaVendasPorCliente = vendaDAO.listarVendasPorCliente();
		}
		return listaVendasPorCliente;
	}

	public void setListaVendasPorCliente(List<Venda> listaVendasPorCliente) {
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

	public List<Venda> getListaVendasEmAberto() {
		if (listaVendasEmAberto == null) {
			listaVendasEmAberto = vendaDAO.listarValorAReceber();

		}
		return listaVendasEmAberto;
	}

	public void setListaVendasEmAberto(List<Venda> listaVendasEmAberto) {
		this.listaVendasEmAberto = listaVendasEmAberto;
	}


	public Double getValorReceberGeral() {
		return valorReceberGeral;
	}

	public void setValorReceberGeral(Double valorReceberGeral) {
		this.valorReceberGeral = valorReceberGeral;
	}

	public Double getValorReceberGeralTotal() {
		return valorReceberGeralTotal;
	}

	public void setValorReceberGeralTotal(Double valorReceberGeralTotal) {
		this.valorReceberGeralTotal = valorReceberGeralTotal;
	}
}