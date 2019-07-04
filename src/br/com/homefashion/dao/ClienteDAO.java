package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.ClienteBean;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.SessionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {

	Connection conexao = null;

	// PEGA O ID DO USUÁRIO LOGADO
	Usuario us = (Usuario) SessionUtil.resgatarDaSessao("usuario_session");

	public List<ClienteBean> buscaNome(String nome) {

		conexao = ConnectionFactory.getConnection();

		String sql = "select id, nome, telefone1, telefone2 from vendas.clientes where upper(nome) like upper(?) and usuario = ? order by nome";

		List<ClienteBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%");
			ps.setInt(2, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClienteBean cliente = new ClienteBean();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome").toUpperCase());
				cliente.setTelefone1(rs.getInt("telefone1"));
				cliente.setTelefone2(rs.getInt("telefone2"));

				lista.add(cliente);
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

	public boolean deleteCliente(ClienteBean cliente) {

		conexao = ConnectionFactory.getConnection();

		String sql = "delete from vendas.clientes where id=?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, cliente.getId());

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

	public boolean editeCliente(ClienteBean cliente) {

		conexao = ConnectionFactory.getConnection();

		String sql = "update vendas.clientes set nome=?, telefone1=?, telefone2=? where id=?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cliente.getNome().toUpperCase());
			ps.setInt(2, cliente.getTelefone1());
			ps.setInt(3, cliente.getTelefone2());
			ps.setInt(4, cliente.getId());

			ps.executeUpdate();

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

	public boolean insereCliente(ClienteBean cliente) {

		conexao = ConnectionFactory.getConnection();

		String sql = "insert into vendas.clientes (nome, telefone1, telefone2, usuario) values (?,?,?,?)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cliente.getNome().toUpperCase());

			if (cliente.getTelefone1() == null) {
				ps.setNull(2, Types.NULL);
			} else {
				ps.setInt(2, cliente.getTelefone1());
			}

			if (cliente.getTelefone2() == null) {
				ps.setNull(3, Types.NULL);
			} else {
				ps.setInt(3, cliente.getTelefone2());
			}

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

	public List<ClienteBean> listarClientes() {

		conexao = ConnectionFactory.getConnection();

		String sql = "select id, nome, telefone1, telefone2 from vendas.clientes where usuario = ? order by nome";

		List<ClienteBean> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ClienteBean cliente = new ClienteBean();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setTelefone1(rs.getInt("telefone1"));
				cliente.setTelefone2(rs.getInt("telefone2"));

				lista.add(cliente);
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