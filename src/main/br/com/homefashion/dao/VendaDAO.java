package br.com.homefashion.dao;

import br.com.homefashion.exception.ProjetoException;
import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.BuscaRelatorio;
import br.com.homefashion.model.Pagamento;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.model.Venda;
import br.com.homefashion.model.builder.ClienteBuilder;
import br.com.homefashion.model.builder.PagamentoBuilder;
import br.com.homefashion.model.builder.VendaBuilder;
import br.com.homefashion.util.DataUtil;
import br.com.homefashion.util.SessaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static br.com.homefashion.shared.Sessao.USUARIO_SESSAO;
import static br.com.homefashion.shared.queries.VendaDAOQueries.*;

public class VendaDAO {

    private Connection conexao = null;

    private Usuario usuarioSessao = (Usuario) SessaoUtil.resgatarDaSessao(USUARIO_SESSAO);

    public void inserirVenda(Venda venda) throws ProjetoException {

        conexao = ConnectionFactory.getConnection();

        try {
            PreparedStatement ps = conexao.prepareStatement(INSERIR_VENDA);
            ps.setInt(1, venda.getCliente().getId());
            ps.setDouble(2, venda.getValor());
            ps.setInt(3, venda.getQtd());
            ps.setDate(4, DataUtil.converterDateUtilParaDateSql(venda.getData()));
            ps.setInt(5, usuarioSessao.getId());

            ps.execute();

            conexao.commit();

        } catch (SQLException ex) {
            throw new ProjetoException(ex);
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Venda> listarVendas(Venda vendaParametro) {

        conexao = ConnectionFactory.getConnection();

        List<Venda> listaVendas = new ArrayList<>();

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_VENDAS);

            ps.setInt(1, vendaParametro.getCliente().getId());
            ResultSet rs = ps.executeQuery();

            listaVendas = mapearResultSetListaVendas(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaVendas;
    }

    private List<Venda> mapearResultSetListaVendas(ResultSet rs) {

        ArrayList<Venda> listaVendas = new ArrayList<>();

        try {
            while (rs.next()) {
                VendaBuilder vendaBuilder = new VendaBuilder();
                listaVendas.add(vendaBuilder.comId(rs.getInt("id"))
                        .comData(rs.getDate("data"))
                        .comValor(rs.getDouble("valor"))
                        .comQtd(rs.getInt("qtd"))
                        .comTotalPago(rs.getDouble("total_pago"))
                        .comEmAberto(rs.getDouble("em_aberto"))
                        .comSituacao(rs.getString("situacao"))
                        .comCliente(new ClienteBuilder().comId(rs.getInt("id_cliente")).comNome(rs.getString("nome")).construir())
                        .construir());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaVendas;
    }

    public void inserirPagamento(Venda venda, Pagamento pagamento) throws ProjetoException {

        conexao = ConnectionFactory.getConnection();

        try {
            PreparedStatement ps = conexao.prepareStatement(INSERIR_PAGAMENTOS);
            ps.setInt(1, venda.getId());
            ps.setDouble(2, pagamento.getValor());
            ps.setDate(3, DataUtil.converterDateUtilParaDateSql(pagamento.getData()));
            ps.setInt(4, usuarioSessao.getId());

            ps.execute();

            conexao.commit();

        } catch (SQLException ex) {
            throw new ProjetoException(ex);
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Pagamento> listarPagamentos(Integer codcliente) {

        conexao = ConnectionFactory.getConnection();

        List<Pagamento> listaPagamentos = new ArrayList<>();

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_PAGAMENTOS);
            ps.setInt(1, codcliente);
            ResultSet rs = ps.executeQuery();

            listaPagamentos = mapearResultSetListaPagamentos(rs);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaPagamentos;
    }

    private List<Pagamento> mapearResultSetListaPagamentos(ResultSet rs) {

        ArrayList<Pagamento> listaPagamentos = new ArrayList<>();

        try {
            while (rs.next()) {
                PagamentoBuilder pagamentoBuilder = new PagamentoBuilder();
                listaPagamentos.add(pagamentoBuilder
                        .comId(rs.getInt("id"))
                        .comValor(rs.getDouble("valor_pago"))
                        .comData(rs.getDate("data_pagamento"))
                        .comVenda(new VendaBuilder().comId(rs.getInt("id")).comData(rs.getDate("data")).construir())
                        .construir());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPagamentos;
    }

    public Double calcularValorEmAberto(Integer codvenda) {

        conexao = ConnectionFactory.getConnection();

        double valorEmAberto = 0.0;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_CALCULAR_VALOR_EM_ABERTO);
            ps.setInt(1, codvenda);
            ps.setInt(2, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                valorEmAberto = rs.getDouble("em_aberto");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valorEmAberto;
    }

    public Integer verificarSemPagamentos(Integer codvenda) {

        conexao = ConnectionFactory.getConnection();

        int valor = 0;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_VERIFICAR_SEM_PAGAMENTOS);
            ps.setInt(1, codvenda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                valor = rs.getInt("qtd");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valor;
    }

    public List<Venda> listarVendasPorCliente() {

        conexao = ConnectionFactory.getConnection();

        List<Venda> listaVendasPorCliente = new ArrayList<>();

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_VENDAS_POR_CLIENTE);
            ps.setInt(1, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.getCliente().setNome(rs.getString("nome"));
                venda.setValor(rs.getDouble("total"));

                listaVendasPorCliente.add(venda);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaVendasPorCliente;
    }

    public Double consultarVendasPorPeriodo(BuscaRelatorio busca) {

        conexao = ConnectionFactory.getConnection();

        double valor = 0.0;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_CONSULTAR_VENDAS_POR_PERIODO);
            ps.setDate(1,
                    new java.sql.Date(busca.getPeriodoinicial().getTime()));
            ps.setDate(2, DataUtil.converterDateUtilParaDateSql(busca.getPeriodofinal()));
            ps.setInt(3, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                valor = rs.getDouble("soma");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valor;
    }

    public Double calcularVendasTotal() {

        conexao = ConnectionFactory.getConnection();

        double valor = 0.0;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_CALCULAR_VENDAS_TOTAL);
            ps.setInt(1, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                valor = rs.getDouble("soma");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valor;
    }

    public Double calcularValorReceberGeral() {

        conexao = ConnectionFactory.getConnection();

        double valor = 0.0;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_CALCULAR_VALOR_RECEBER_GERAL);
            ps.setInt(1, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                valor = rs.getDouble("valor");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return valor;
    }

    public List<Venda> listarValorAReceber() {

        conexao = ConnectionFactory.getConnection();

        List<Venda> listaValoresAReceber = new ArrayList<>();

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_VALOR_A_RECEBER);
            ps.setInt(1, usuarioSessao.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.getCliente().setNome(rs.getString("nome"));
                venda.setEmAberto(rs.getDouble("em_aberto"));
                venda.setData(rs.getDate("data"));
                venda.setValor(rs.getDouble("valor"));

                listaValoresAReceber.add(venda);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listaValoresAReceber;
    }

    public Boolean verificarSeExistePagamentoParaVenda(Integer idVenda) {

        conexao = ConnectionFactory.getConnection();

        boolean retorno = false;

        try {
            PreparedStatement ps = conexao.prepareStatement(SELECT_VERIFICAR_SE_EXISTE_PAGAMENTO_PARA_VENDA);
            ps.setInt(1, idVenda);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                retorno = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return retorno;
    }

    public void cancelarVenda(Integer idVenda) {
        try {
            realizarCancelamento(idVenda, ALTERAR_CANCELAR_VENDA);
        } catch (ProjetoException e) {
            e.printStackTrace();
        }
    }

    public void cancelarPagamento(Integer idPagamento) {
        try {
            realizarCancelamento(idPagamento, ALTERAR_CANCELAR_PAGAMENTO);
        } catch (ProjetoException e) {
            e.printStackTrace();
        }
    }

    private void realizarCancelamento(Integer idParaCancelar, String sqlCancelamento) throws ProjetoException {

        conexao = ConnectionFactory.getConnection();

        try {
            PreparedStatement ps = conexao.prepareStatement(sqlCancelamento);
            ps.setInt(1, idParaCancelar);

            ps.execute();

            conexao.commit();

        } catch (SQLException ex) {
            throw new ProjetoException(ex);
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}