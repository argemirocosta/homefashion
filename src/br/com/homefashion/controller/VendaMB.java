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
import static br.com.homefashion.shared.Mensagens.*;

import br.com.homefashion.util.DataUtil;
import br.com.homefashion.util.JSFUtil;

@ViewScoped
@ManagedBean
public class VendaMB {

    private Venda venda;
    private List<Venda> listaVendas;
    private Pagamento pagamento;
    private List<Pagamento> listaPagamentos;
    private List<Venda> listaVendasPorCliente;
    private List<Venda> listaVendasEmAberto;
    private BuscaRelatorio busca;
    private Double totalVendidoNoPeriodo;
    private Double valorReceberGeral;
    private Double valorReceberGeralTotal;
    private VendaDAO vendaDAO = new VendaDAO();

    public VendaMB() {
        venda = new Venda();
        pagamento = new Pagamento();
        listaVendas = new ArrayList<>();
        listaVendasPorCliente = new ArrayList<>();
        busca = new BuscaRelatorio();
        busca.setPeriodoinicial(DataUtil.retornarDataAtual());
        busca.setPeriodofinal(DataUtil.retornarDataAtual());
        totalVendidoNoPeriodo = 0.0;
        listaVendasEmAberto = new ArrayList<>();
    }

    private void limparCampos() {
        venda.setQtd(null);
        venda.setValor(null);
        listaVendas = null;
    }

    public void inserirVenda() {
        boolean cadastrou = vendaDAO.inserirVenda(venda);

        if (cadastrou) {
            limparCampos();
            JSFUtil.fecharDialog(DIALOG_VENDER);
            JSFUtil.adicionarMensagemSucesso(VENDA_SUCESSO, SUCESSO);
        } else {
            JSFUtil.adicionarMensagemErro(VENDA_ERRO, ERRO);
        }

    }

    public void inserirPagamento() {
        int pagamentoVenda = vendaDAO.verificarSemPagamentos(venda.getId());
        Double valorEmAberto = vendaDAO.calcularValorEmAberto(venda.getId());

        if (pagamento.getValor() > valorEmAberto) {
            JSFUtil.adicionarMensagemAdvertencia(PAGAMENTO_MAIOR_QUE_VENDA, AVISO);
        } else if (valorEmAberto > 0 || pagamentoVenda == 0) {
            boolean cadastrou = vendaDAO.inserirPagamento(venda, pagamento);

            if (cadastrou) {
                listaPagamentos = null;
                listaVendas = null;

                JSFUtil.adicionarMensagemSucesso(PAGAMENTO_SUCESSO, SUCESSO);
                pagamento.setValor(null);
            } else {
                JSFUtil.adicionarMensagemErro(PAGAMENTO_ERRO, ERRO);
            }

        } else {
            JSFUtil.adicionarMensagemAdvertencia(VENDA_JA_PAGA, AVISO);

        }

    }

    public void totalVendidoPeriodo() {
        totalVendidoNoPeriodo = vendaDAO.consultarVendasPorPeriodo(busca);
    }

    private void somarGeralTotal() {
        totalVendidoNoPeriodo = vendaDAO.calcularVendasTotal();
        valorReceberGeralTotal = totalVendidoNoPeriodo - valorReceberGeral;
    }

    public void calcularRecebidoGeral() {
        valorReceberGeral = vendaDAO.calcularValorReceberGeral();
        somarGeralTotal();
    }

    public void listarRankingDosClientes() {
        listaVendasPorCliente = vendaDAO.listarVendasPorCliente();
    }

    public void listarPagamentos() {
        listaPagamentos = vendaDAO.listarPagamentos(venda.getId());
    }

    public void listarValorAReceberPorPessoa() {
        listaVendasEmAberto = vendaDAO.listarValorAReceber();
    }

    public void listarVendas(){
        listaVendas = vendaDAO.listarVendas(venda);
    }

    //GETTERS E SETTERS

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Venda> getListaVendas() {
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
        return listaPagamentos;
    }

    public void setListaPagamentos(List<Pagamento> listaPagamentos) {
        this.listaPagamentos = listaPagamentos;
    }

    public List<Venda> getListaVendasPorCliente() {
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


    public List<Venda> getListaVendasEmAberto() {
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

    public Double getTotalVendidoNoPeriodo() {
        return totalVendidoNoPeriodo;
    }

    public void setTotalVendidoNoPeriodo(Double totalVendidoNoPeriodo) {
        this.totalVendidoNoPeriodo = totalVendidoNoPeriodo;
    }
}