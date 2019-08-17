package br.com.homefashion.service;

import br.com.homefashion.dao.VendaDAO;
import br.com.homefashion.model.BuscaRelatorio;
import br.com.homefashion.model.Pagamento;
import br.com.homefashion.model.Venda;

import java.util.List;

public class VendaService {

    private VendaDAO vendaDAO;

    public VendaService() {
        vendaDAO = new VendaDAO();
    }

    public Boolean inserirVenda(Venda venda) {
        return vendaDAO.inserirVenda(venda);
    }

    public Integer verificarSemPagamentos(Integer codVenda) {
        return vendaDAO.verificarSemPagamentos(codVenda);
    }

    public Double calcularValorEmAberto(Integer codVenda) {
        return vendaDAO.calcularValorEmAberto(codVenda);
    }

    public Boolean inserirPagamento(Venda venda, Pagamento pagamento) {
        return vendaDAO.inserirPagamento(venda, pagamento);
    }

    public Double consultarVendasPorPeriodo(BuscaRelatorio busca) {
        return vendaDAO.consultarVendasPorPeriodo(busca);
    }

    public Double calcularVendasTotal() {
        return vendaDAO.calcularVendasTotal();
    }

    public Double calcularValorReceberGeral() {
        return vendaDAO.calcularValorReceberGeral();
    }

    public List<Venda> listarRankingDosClientes() {
        return vendaDAO.listarVendasPorCliente();
    }

    public List<Pagamento> listarPagamentos(Integer codVenda) {
        return vendaDAO.listarPagamentos(codVenda);
    }

    public List<Venda> listarValorAReceberPorPessoa() {
        return vendaDAO.listarValorAReceber();
    }

    public List<Venda> listarVendas(Venda venda) {
        return vendaDAO.listarVendas(venda);
    }

    public Boolean cancelarVenda(Integer codVenda) {
        return vendaDAO.cancelarVenda(codVenda);
    }

    public Boolean cancelarPagamento(Integer codPagamento) {
        return vendaDAO.cancelarPagamento(codPagamento);
    }

    public Boolean verificarSeExistePagamentoParaVenda(Integer codVenda) {
        return vendaDAO.verificarSeExistePagamentoParaVenda(codVenda);
    }

    public Double calcularValorEmAbertoDaVenda(Integer codVenda) {
        return vendaDAO.calcularValorEmAberto(codVenda);
    }
}