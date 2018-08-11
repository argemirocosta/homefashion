package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.BuscaRelatorioBean;
import br.com.homefashion.model.PagamentoBean;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.model.VendaBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

public class VendaDAO {

	Connection conexao = null;

	// PEGA O ID DO USUÁRIO LOGADO
	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario_session");

	public boolean insereVenda(VendaBean venda) {

		conexao = ConnectionFactory.getConnection();

		String sql = "insert into vendas.venda (id_cliente, valor, qtd, data, usuario) values (?,?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, venda.getCliente().getId());
			ps.setDouble(2, venda.getValor());
			ps.setInt(3, venda.getQtd());
			ps.setDate(4, new java.sql.Date(venda.getData().getTime()));
			ps.setInt(5, us.getId());

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

	public List<VendaBean> listarVendas(VendaBean vendaB) {

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

	public boolean inserePagamento(VendaBean venda, PagamentoBean pagamento) {

		conexao = ConnectionFactory.getConnection();

		String sql = "insert into vendas.pagamentos (id_venda, valor_pago, data_pagamento, usuario) values (?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, venda.getId());
			ps.setDouble(2, pagamento.getValor());
			ps.setDate(3, new java.sql.Date(pagamento.getData().getTime()));
			ps.setInt(4, us.getId());

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
				+ "where v.id = ? " + "order by v.data desc, p.data_pagamento desc";

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
				+ "where v.id = ? and v.usuario = ? group by v.id ";

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

	public Integer semPagamentos(Integer codvenda) {

		conexao = ConnectionFactory.getConnection();
		Integer valor = 0;
		String sql = "select count(id_venda) as qtd from vendas.pagamentos where id_venda = ?";

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

	public List<VendaBean> vendasPorCliente() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select v.id_cliente, c.nome, sum(v.valor) as total "
				+ "from vendas.venda v "
				+ "left join vendas.clientes c on (v.id_cliente = c.id) "
				+ "where v.usuario = ?"
				+ "group by v.id_cliente, c.nome order by total desc";

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

	public Double vendasPorPeriodo(BuscaRelatorioBean busca) {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor) as soma from vendas.venda where data between ? and ? and usuario = ?";
		Double valor = 0.0;

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

	public Double vendasTotal() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor) as soma from vendas.venda where usuario = ?";
		Double valor = 0.0;

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

	public Double receberGeral() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select sum(valor_pago) as valor from vendas.pagamentos where usuario = ?";
		Double valor = 0.0;

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

	public List<VendaBean> aReceber() {

		conexao = ConnectionFactory.getConnection();
		String sql = "select v.id, v.data, v.id_cliente, c.nome, v.valor, "
				+ "coalesce((v.valor - sum(p.valor_pago)), v.valor) as em_aberto "
				+ "from vendas.venda v "
				+ "left join vendas.clientes c on (v.id_cliente = c.id) "
				+ "left join vendas.pagamentos p on (v.id = p.id_venda) "
				+ "group by v.id, v.id_cliente, c.nome, v.valor, v.data "
				+ "having coalesce(v.valor - sum(p.valor_pago), v.valor) > 0 and v.usuario = ? "
				+ "order by em_aberto desc ";

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
