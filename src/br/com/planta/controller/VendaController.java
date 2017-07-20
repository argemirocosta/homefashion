package br.com.planta.controller;

import br.com.planta.dao.ClienteDAO;
import br.com.planta.dao.UsuarioDAO;
import br.com.planta.dao.VendaDAO;
import br.com.planta.model.BuscaRelatorioBean;
import br.com.planta.model.ClienteBean;
import br.com.planta.model.PagamentoBean;
import br.com.planta.model.VendaBean;

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

	private ClienteBean p;
	private VendaBean venda;
	private List<VendaBean> listaVendas;
	private PagamentoBean pagamento;
	private List<PagamentoBean> listaPagamentos;
	private Double emAberto;
	private List<VendaBean> listaVendasPorCliente;
	private List<VendaBean> listaVendasEmAberto;
	private BuscaRelatorioBean busca;
	private Double soma_geral;
	private Double receber_geral;
	private Double receber_geral_total;

	public VendaController() {
		p = new ClienteBean();
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
		soma_geral = 0.0;
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
		System.out.println("CODVENDA: " + venda.getId());
		VendaDAO vdao = new VendaDAO();
		emAberto = vdao.valorEmAberto(venda.getId());
		System.out.println("aberto final: " + emAberto);
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
		System.out.println("ID CLIENTE: " + venda.getCliente().getId());
		System.out.println("VALOR: " + venda.getValor());
		System.out.println("DATA: "
				+ new java.sql.Date(venda.getData().getTime()));
		System.out.println("QUANTIDADE: " + venda.getQtd());

		VendaDAO pd = new VendaDAO();
		boolean cadastrou = pd.insereVenda(venda);

		if (cadastrou) {

			limparCampos();

			RequestContext.getCurrentInstance().execute(
					"PF('dlgVender').hide();");

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
		System.out.println("ID VENDA: " + venda.getId());
		System.out.println("VALOR VENDA: " + venda.getValor());
		System.out.println("DATA VENDA: "
				+ new java.sql.Date(venda.getData().getTime()));
		System.out.println("QUANTIDADE VENDA: " + venda.getQtd());
		System.out.println("VALOR PAGAMENTO: " + pagamento.getValor());
		System.out.println("DATA VENDA: " + pagamento.getData());

		VendaDAO pd = new VendaDAO();
		int pagamentoVenda = pd.semPagamentos(venda.getId());
		emAberto = pd.valorEmAberto(venda.getId());
		System.out.println("ABERTO: " + emAberto);
		System.out.println("QTD PAGAMENTOS: " + pagamentoVenda);
		if (pagamento.getValor() > emAberto) {
			System.out.println("Entrou aqui");
			FacesMessage msg = new FacesMessage(
					"Pagamento maior que a venda não é permitido!");
			FacesContext ct = FacesContext.getCurrentInstance();
			ct.addMessage(null, msg);

		} else if (emAberto > 0 || pagamentoVenda == 0) {
			boolean cadastrou = pd.inserePagamento(venda, pagamento);

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
				FacesMessage msg = new FacesMessage(
						"Erro ao realizar Pagamento");
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
		VendaDAO pd = new VendaDAO();
		soma_geral = pd.vendasPorPeriodo(busca);
	}
	
	public void somaGeralTotal() {
		VendaDAO pd = new VendaDAO();
		soma_geral = pd.vendasTotal();
		
		receber_geral_total = soma_geral - receber_geral;
	}
	
	public void recebidoGeral() {
		VendaDAO pd = new VendaDAO();
		receber_geral = pd.receberGeral();
		
		somaGeralTotal();
	}

	public ClienteBean getP() {
		return p;
	}

	public void setP(ClienteBean p) {
		this.p = p;
	}

	public VendaBean getVenda() {
		return venda;
	}

	public void setVenda(VendaBean venda) {
		this.venda = venda;
	}

	public List<VendaBean> getListaVendas() {
		System.out.println("LISTA DE VENDAS: " + listaVendas);
		VendaDAO pd = new VendaDAO();
		if (listaVendas == null) {
			listaVendas = pd.listarVendas(venda);
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
		VendaDAO pd = new VendaDAO();

		if (listaPagamentos == null) {
			System.out.println("ID DA VENDA: " + venda.getId());
			System.out.println("ID DO CLIENTE: " + venda.getCliente().getId());
			listaPagamentos = pd.listarPagamentos(venda.getId());
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
		VendaDAO pd = new VendaDAO();

		if (listaVendasPorCliente == null) {
			listaVendasPorCliente = pd.vendasPorCliente();
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

	public Double getSoma_geral() {
		return soma_geral;
	}

	public void setSoma_geral(Double soma_geral) {
		this.soma_geral = soma_geral;
	}

	public List<VendaBean> getListaVendasEmAberto() {
		VendaDAO pd = new VendaDAO();

		if (listaVendasEmAberto == null) {
			listaVendasEmAberto = pd.aReceber();

		}
		return listaVendasEmAberto;
	}

	public void setListaVendasEmAberto(List<VendaBean> listaVendasEmAberto) {
		this.listaVendasEmAberto = listaVendasEmAberto;
	}

	public Double getReceber_geral() {
		return receber_geral;
	}

	public void setReceber_geral(Double receber_geral) {
		this.receber_geral = receber_geral;
	}

	public Double getReceber_geral_total() {
		return receber_geral_total;
	}

	public void setReceber_geral_total(Double receber_geral_total) {
		this.receber_geral_total = receber_geral_total;
	}

}