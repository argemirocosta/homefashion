package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.BuscaRelatorio;
import br.com.homefashion.model.Pagamento;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.model.VendaBean;
import br.com.homefashion.util.SessionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

	Connection conexao = null;

	Usuario us = (Usuario) SessionUtil.resgatarDaSessao("usuario_session");

	public Boolean inserirVenda(VendaBean venda) {

		Boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		String sql = "INSERT INTO vendas.venda (id_cliente, valor, qtd, data, usuario) VALUES (?,?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, venda.getCliente().getId());
			ps.setDouble(2, venda.getValor());
			ps.setInt(3, venda.getQtd());
			ps.setDate(4, new java.sql.Date(venda.getData().getTime()));
			ps.setInt(5, us.getId());

			ps.execute();

			retorno =  true;

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

	public List<VendaBean> listarVendas(VendaBean vendaB) {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT v.id, v.id_cliente, c.nome, v.data, v.valor, v.qtd, "
				+ "COALESCE(sum(p.valor_pago),0) AS total_pago, COALESCE((v.valor - sum(p.valor_pago)),v.valor) AS em_aberto, "
				+ "CASE WHEN COALESCE((v.valor - sum(p.valor_pago)),v.valor) = 0 THEN 'PAGO' "
				+ "WHEN COALESCE((v.valor - sum(p.valor_pago)),v.valor) > 0 THEN 'ABERTO' "
				+ "END AS situacao "
				+ "FROM vendas.venda v "
				+ "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
				+ "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
				+ "WHERE v.id_cliente = ? "
				+ "GROUP BY v.id, v.id_cliente, c.nome, v.valor, v.qtd, v.data "
				+ "ORDER BY v.data DESC";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, vendaB.getCliente().getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean venda = new VendaBean();
				venda.setId(rs.getInt("id"));
				venda.getCliente().setNome(rs.getString("nome"));
				venda.setData(rs.getDate("data"));
				venda.setValor(rs.getDouble("valor"));
				venda.setQtd(rs.getInt("qtd"));
				venda.getCliente().setId(rs.getInt("id_cliente"));
				venda.setTotalPago(rs.getDouble("total_pago"));
				venda.setEmAberto(rs.getDouble("em_aberto"));
				venda.setSituacao(rs.getString("situacao"));

				lista.add(venda);
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
		return lista;
	}

	public Boolean inserirPagamento(VendaBean venda, Pagamento pagamento) {

		Boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		String sql = "INSERT INTO vendas.pagamentos (id_venda, valor_pago, data_pagamento, usuario) VALUES (?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, venda.getId());
			ps.setDouble(2, pagamento.getValor());
			ps.setDate(3, new java.sql.Date(pagamento.getData().getTime()));
			ps.setInt(4, us.getId());

			ps.execute();

			retorno = true;
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

	public List<Pagamento> listarPagamentos(Integer codcliente) {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT p.id_venda, p.valor_pago, p.data_pagamento, v.data "
				+ "FROM vendas.pagamentos p "
				+ "LEFT JOIN vendas.venda v ON (p.id_venda = v.id) "
				+ "WHERE v.id = ? "
				+ "ORDER BY v.data DESC, p.data_pagamento DESC";

		List<Pagamento> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, codcliente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Pagamento p = new Pagamento();
				p.getVenda().setId(rs.getInt("id_venda"));
				p.setData(rs.getDate("data_pagamento"));
				p.setValor(rs.getDouble("valor_pago"));
				p.getVenda().setData(rs.getDate("data"));

				lista.add(p);
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
		return lista;
	}

	public Double calcularValorEmAberto(Integer codvenda) {

		conexao = ConnectionFactory.getConnection();

		Double valor = 0.0;

		String sql = "SELECT v.id, (v.valor - sum(COALESCE(p.valor_pago, 0))) AS em_aberto "
				+ "FROM vendas.venda v "
				+ "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
				+ "WHERE v.id = ? AND v.usuario = ? GROUP BY v.id ";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, codvenda);
			ps.setInt(2, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				valor = rs.getDouble("em_aberto");
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

	public Integer verificarSemPagamentos(Integer codvenda) {

		conexao = ConnectionFactory.getConnection();

		Integer valor = 0;

		String sql = "SELECT count(id_venda) AS qtd FROM vendas.pagamentos WHERE id_venda = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
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

	public List<VendaBean> listarVendasPorCliente() {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT v.id_cliente, c.nome, sum(v.valor) AS total "
				+ "FROM vendas.venda v "
				+ "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
				+ "WHERE v.usuario = ?"
				+ "GROUP BY v.id_cliente, c.nome "
				+ "ORDER BY total DESC";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean venda = new VendaBean();
				venda.getCliente().setNome(rs.getString("nome"));
				venda.setValor(rs.getDouble("total"));

				lista.add(venda);
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
		return lista;
	}

	public Double consultarVendasPorPeriodo(BuscaRelatorio busca) {

		conexao = ConnectionFactory.getConnection();

		Double valor = 0.0;

		String sql = "SELECT sum(valor) AS soma FROM vendas.venda WHERE DATA BETWEEN ? AND ? AND usuario = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setDate(1,
					new java.sql.Date(busca.getPeriodoinicial().getTime()));
			ps.setDate(2, new java.sql.Date(busca.getPeriodofinal().getTime()));
			ps.setInt(3, us.getId());
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

		Double valor = 0.0;

		String sql = "SELECT sum(valor) AS soma FROM vendas.venda WHERE usuario = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
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

		Double valor = 0.0;

		String sql = "SELECT sum(valor_pago) AS valor FROM vendas.pagamentos WHERE usuario = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
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

	public List<VendaBean> listarValorAReceber() {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT v.id, v.data, v.id_cliente, c.nome, v.valor, "
				+ "COALESCE((v.valor - sum(p.valor_pago)), v.valor) AS em_aberto "
				+ "FROM vendas.venda v "
				+ "LEFT JOIN vendas.clientes c ON (v.id_cliente = c.id) "
				+ "LEFT JOIN vendas.pagamentos p ON (v.id = p.id_venda) "
				+ "GROUP BY v.id, v.id_cliente, c.nome, v.valor, v.data "
				+ "HAVING COALESCE(v.valor - sum(p.valor_pago), v.valor) > 0 AND v.usuario = ? "
				+ "ORDER BY em_aberto DESC ";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean venda = new VendaBean();
				venda.getCliente().setNome(rs.getString("nome"));
				venda.setEmAberto(rs.getDouble("em_aberto"));
				venda.setData(rs.getDate("data"));
				venda.setValor(rs.getDouble("valor"));

				lista.add(venda);
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
		return lista;
	}

}