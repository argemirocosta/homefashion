package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.Cliente;
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

	Usuario us = (Usuario) SessionUtil.resgatarDaSessao("usuario_session");

	public List<Cliente> buscaClientePorNome(String nome) {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT id, nome, telefone1, telefone2 FROM vendas.clientes WHERE upper(nome) LIKE upper(?) AND usuario = ? ORDER BY nome";

		List<Cliente> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%");
			ps.setInt(2, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente();
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

	public Boolean deletarCliente(Cliente cliente) {

		Boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		String sql = "DELETE FROM vendas.clientes WHERE id=?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, cliente.getId());

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

	public Boolean alterarCliente(Cliente cliente) {

		Boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		String sql = "UPDATE vendas.clientes SET nome=?, telefone1=?, telefone2=? WHERE id=?";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, cliente.getNome().toUpperCase());
			ps.setInt(2, cliente.getTelefone1());
			ps.setInt(3, cliente.getTelefone2());
			ps.setInt(4, cliente.getId());

			ps.executeUpdate();

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

	public Boolean inserirCliente(Cliente cliente) {

		Boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		String sql = "INSERT INTO vendas.clientes (nome, telefone1, telefone2, usuario) VALUES (?,?,?,?)";

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

	public List<Cliente> listarClientes() {

		conexao = ConnectionFactory.getConnection();

		String sql = "SELECT id, nome, telefone1, telefone2 FROM vendas.clientes WHERE usuario = ? ORDER BY nome";

		List<Cliente> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, us.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente();
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