package br.com.homefashion.dao;

import br.com.homefashion.connection.ConnectionFactory;
import br.com.homefashion.model.BuscaRelatorioBean;
import br.com.homefashion.model.ClienteBean;
import br.com.homefashion.model.PagamentoBean;
import br.com.homefashion.model.VendaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

	Connection conexao = null;

	public boolean insereVenda(VendaBean p) {

		conexao = ConnectionFactory.getConnection();

		String sql = "insert into vendas.venda (id_cliente, valor, qtd, data) values (?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, p.getCliente().getId());
			ps.setDouble(2, p.getValor());
			ps.setInt(3, p.getQtd());
			ps.setDate(4, new java.sql.Date(p.getData().getTime()));

			ps.execute();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public List<VendaBean> listarVendas(VendaBean venda) {

		conexao = ConnectionFactory.getConnection();

		String sql = "select v.id, v.id_cliente, c.nome, v.data, v.valor, v.qtd, "
				+ "coalesce(sum(p.valor_pago),0) as total_pago, coalesce((v.valor - sum(p.valor_pago)),v.valor) as em_aberto, "
				+ "case when coalesce((v.valor - sum(p.valor_pago)),v.valor) = 0 then 'PAGO' "
				+ "when coalesce((v.valor - sum(p.valor_pago)),v.valor) > 0 then 'ABERTO' "
				+ "end as situacao "
				+ "from vendas.venda v "
				+ "left join vendas.clientes c on (v.id_cliente = c.id) "
				+ "left join vendas.pagamentos p on (v.id = p.id_venda) "
				+ "where v.id_cliente = ? "
				+ "group by v.id, v.id_cliente, c.nome, v.valor, v.qtd, v.data "
				+ "order by v.data desc";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setInt(1, venda.getCliente().getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.setId(rs.getInt("id"));
				p.getCliente().setNome(rs.getString("nome"));
				p.setData(rs.getDate("data"));
				p.setValor(rs.getDouble("valor"));
				p.setQtd(rs.getInt("qtd"));
				p.getCliente().setId(rs.getInt("id_cliente"));
				p.setTotal_pago(rs.getDouble("total_pago"));
				p.setEm_aberto(rs.getDouble("em_aberto"));
				p.setSituacao(rs.getString("situacao"));

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

	public boolean inserePagamento(VendaBean v, PagamentoBean p) {

		conexao = ConnectionFactory.getConnection();

		String sql = "insert into vendas.pagamentos (id_venda, valor_pago, data_pagamento) values (?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, v.getId());
			ps.setDouble(2, p.getValor());
			ps.setDate(3, new java.sql.Date(p.getData().getTime()));

			ps.execute();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

	public List<PagamentoBean> listarPagamentos(Integer codcliente) {

		conexao = ConnectionFactory.getConnection();

		String sql = "select p.id_venda, p.valor_pago, p.data_pagamento, v.data "
				+ "from vendas.pagamentos p "
				+ "left join vendas.venda v on (p.id_venda = v.id) "
				+ "where v.id = ? " + "order by v.data";

		List<PagamentoBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, codcliente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PagamentoBean p = new PagamentoBean();
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

	public Double valorEmAberto(Integer codvenda) {

		conexao = ConnectionFactory.getConnection();
		Double valor = 0.0;
		String sql = "select v.id, (v.valor - sum(coalesce(p.valor_pago, 0))) as em_aberto "
				+ "from vendas.venda v "
				+ "left join vendas.pagamentos p on (v.id = p.id_venda) "
				+ "where v.id = ? group by v.id ";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, codvenda);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.setEm_aberto(rs.getDouble("em_aberto"));
				valor = p.getEm_aberto();
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

	public Integer semPagamentos(Integer codvenda) {

		conexao = ConnectionFactory.getConnection();
		Integer valor = 0;
		String sql = "select count(id_venda) as qtd from vendas.pagamentos where id_venda = ?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, codvenda);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PagamentoBean p = new PagamentoBean();
				p.getVenda().setId(rs.getInt("qtd"));
				valor = p.getVenda().getId();
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

	public List<VendaBean> vendasPorCliente() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select v.id_cliente, c.nome, sum(v.valor) as total "
				+ "from vendas.venda v "
				+ "left join vendas.clientes c on (v.id_cliente = c.id) "
				+ "group by v.id_cliente, c.nome order by total desc";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.getCliente().setNome(rs.getString("nome"));
				p.setValor(rs.getDouble("total"));

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

	public Double vendasPorPeriodo(BuscaRelatorioBean busca) {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor) as soma from vendas.venda where data between ? and ?";
		Double valor = 0.0;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setDate(1,
					new java.sql.Date(busca.getPeriodoinicial().getTime()));
			ps.setDate(2, new java.sql.Date(busca.getPeriodofinal().getTime()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.setSoma_total(rs.getDouble("soma"));
				valor = p.getSoma_total();

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

	public Double vendasTotal() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor) as soma from vendas.venda";
		Double valor = 0.0;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.setSoma_total(rs.getDouble("soma"));
				valor = p.getSoma_total();

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

	public Double receberGeral() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor_pago) as valor from vendas.pagamentos";
		Double valor = 0.0;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.setReceber_geral(rs.getDouble("valor"));
				valor = p.getReceber_geral();

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

	public List<VendaBean> aReceber() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select v.id, v.data, v.id_cliente, c.nome, v.valor, "
				+ "coalesce((v.valor - sum(p.valor_pago)), v.valor) as em_aberto "
				+ "from vendas.venda v "
				+ "left join vendas.clientes c on (v.id_cliente = c.id) "
				+ "left join vendas.pagamentos p on (v.id = p.id_venda) "
				+ "group by v.id, v.id_cliente, c.nome, v.valor, v.data "
				+ "having coalesce(v.valor - sum(p.valor_pago), v.valor) > 0 "
				+ "order by em_aberto desc ";

		List<VendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				VendaBean p = new VendaBean();
				p.getCliente().setNome(rs.getString("nome"));
				p.setEm_aberto(rs.getDouble("em_aberto"));
				p.setData(rs.getDate("data"));
				p.setValor(rs.getDouble("valor"));

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

}
