package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.Cliente;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.SessaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import static br.com.homefashion.shared.Queries.*;
import static br.com.homefashion.shared.Sessao.*;

public class ClienteDAO {

	private Connection conexao = null;

	private Usuario us = (Usuario) SessaoUtil.resgatarDaSessao(USUARIO_SESSAO);

	public List<Cliente> listarClientes() {

		conexao = ConnectionFactory.getConnection();

		List<Cliente> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_CLIENTES);
			ps.setInt(1, us.getId());
			ResultSet rs = ps.executeQuery();

			lista = mapearResultSetIniciarListaClientes(rs);

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

	public List<Cliente> buscarClientePorNome(String nome) {

		conexao = ConnectionFactory.getConnection();

		List<Cliente> lista = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(SELECT_BUSCAR_CLIENTE_POR_NOME);
			ps.setString(1, "%" + nome + "%");
			ps.setInt(2, us.getId());
			ResultSet rs = ps.executeQuery();

			lista = mapearResultSetIniciarListaClientes(rs);

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

	private ArrayList<Cliente> mapearResultSetIniciarListaClientes(ResultSet rs) {

		ArrayList<Cliente> listaClientes = new ArrayList<>();

		try {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setTelefone1(rs.getInt("telefone1"));
				cliente.setTelefone2(rs.getInt("telefone2"));

				listaClientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaClientes;
	}

	public Boolean inserirCliente(Cliente cliente) {

		boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		try {
			PreparedStatement ps = conexao.prepareStatement(INSERIR_CLIENTE);
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

	public Boolean alterarCliente(Cliente cliente) {

		boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		try {
			PreparedStatement ps = conexao.prepareStatement(ALTERAR_CLIENTE);
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

	public Boolean deletarCliente(Cliente cliente) {

		boolean retorno = false;

		conexao = ConnectionFactory.getConnection();

		try {
			PreparedStatement ps = conexao.prepareStatement(DELETAR_CLIENTE);

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

}