package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.Cliente;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.DataUtil;
import br.com.homefashion.util.SessaoUtil;
import br.com.homefashion.util.StringUtil;
import br.com.homefashion.util.VerificadorUtil;

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

	private Usuario usuarioSessao = (Usuario) SessaoUtil.resgatarDaSessao(USUARIO_SESSAO);

	public List<Cliente> listarClientes() {

		conexao = ConnectionFactory.getConnection();

		List<Cliente> listaClientes = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(SELECT_LISTAR_CLIENTES);
			ps.setInt(1, usuarioSessao.getId());
			ResultSet rs = ps.executeQuery();

			listaClientes = mapearResultSetIniciarListaClientes(rs);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return listaClientes;
	}

	public List<Cliente> buscarClientePorNome(String nomeCliente) {

		conexao = ConnectionFactory.getConnection();

		List<Cliente> listaClientes = new ArrayList<>();

		try {
			PreparedStatement ps = conexao.prepareStatement(SELECT_BUSCAR_CLIENTE_POR_NOME);
			ps.setString(1, "%" + nomeCliente + "%");
			ps.setInt(2, usuarioSessao.getId());
			ResultSet rs = ps.executeQuery();

			listaClientes = mapearResultSetIniciarListaClientes(rs);

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return listaClientes;
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
				cliente.setDataNascimento(rs.getDate("data_nascimento"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setRg(rs.getString("rg"));
				cliente.getEndereco().setCep(rs.getString("cep"));
				cliente.getEndereco().setEstado(rs.getString("estado"));
				cliente.getEndereco().setCidade(rs.getString("cidade"));
				cliente.getEndereco().setBairro(rs.getString("bairro"));
				cliente.getEndereco().setLogradouro(rs.getString("logradouro"));
				cliente.getEndereco().setNumero(rs.getInt("numero"));
				cliente.getEndereco().setCodIBGE(rs.getInt("cod_ibge"));

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

			if (VerificadorUtil.verificarSeObjetoNulo(cliente.getTelefone1())) {
				ps.setNull(2, Types.NULL);
			} else {
				ps.setInt(2, cliente.getTelefone1());
			}

			if (VerificadorUtil.verificarSeObjetoNulo(cliente.getTelefone2())) {
				ps.setNull(3, Types.NULL);
			} else {
				ps.setInt(3, cliente.getTelefone2());
			}

			ps.setInt(4, usuarioSessao.getId());

			if(VerificadorUtil.verificarSeObjetoNulo(cliente.getDataNascimento())){
				ps.setNull(5, Types.NULL);
			}
			else {
				ps.setDate(5, DataUtil.converterDateUtilParaDateSql(cliente.getDataNascimento()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getCpf())){
				ps.setNull(6, Types.CHAR);
			}
			else {
				ps.setString(6, StringUtil.retirarCaracteresEspeciais(cliente.getCpf()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getRg())){
				ps.setNull(7, Types.CHAR);
			}
			else {
				ps.setString(7, cliente.getRg());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCep())){
				ps.setNull(8, Types.CHAR);
			}
			else {
				ps.setString(8, StringUtil.retirarCaracteresEspeciais(cliente.getEndereco().getCep()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getEstado())){
				ps.setNull(9, Types.CHAR);
			}
			else {
				ps.setString(9, cliente.getEndereco().getEstado());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCidade())){
				ps.setNull(10, Types.CHAR);
			}
			else {
				ps.setString(10, cliente.getEndereco().getCidade());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getBairro())){
				ps.setNull(11, Types.CHAR);
			}
			else {
				ps.setString(11, cliente.getEndereco().getBairro());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getLogradouro())){
				ps.setNull(12, Types.CHAR);
			}
			else {
				ps.setString(12, cliente.getEndereco().getLogradouro());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getNumero())){
				ps.setNull(13, Types.NULL);
			}
			else {
				ps.setInt(13, cliente.getEndereco().getNumero());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCodIBGE())){
				ps.setNull(14, Types.NULL);
			}
			else {
				ps.setInt(14, cliente.getEndereco().getCodIBGE());
			}

			ps.execute();

			conexao.commit();

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

			if(VerificadorUtil.verificarSeObjetoNulo(cliente.getDataNascimento())){
				ps.setNull(4, Types.NULL);
			}
			else {
				ps.setDate(4, DataUtil.converterDateUtilParaDateSql(cliente.getDataNascimento()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getCpf())){
				ps.setNull(5, Types.CHAR);
			}
			else {
				ps.setString(5, StringUtil.retirarCaracteresEspeciais(cliente.getCpf()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getRg())){
				ps.setNull(6, Types.CHAR);
			}
			else {
				ps.setString(6, cliente.getRg());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCep())){
				ps.setNull(7, Types.CHAR);
			}
			else {
				ps.setString(7, StringUtil.retirarCaracteresEspeciais(cliente.getEndereco().getCep()));
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getEstado())){
				ps.setNull(8, Types.CHAR);
			}
			else {
				ps.setString(8, cliente.getEndereco().getEstado());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCidade())){
				ps.setNull(9, Types.CHAR);
			}
			else {
				ps.setString(9, cliente.getEndereco().getCidade());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getBairro())){
				ps.setNull(10, Types.CHAR);
			}
			else {
				ps.setString(10, cliente.getEndereco().getBairro());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getLogradouro())){
				ps.setNull(11, Types.CHAR);
			}
			else {
				ps.setString(11, cliente.getEndereco().getLogradouro());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getNumero())){
				ps.setNull(12, Types.NULL);
			}
			else {
				ps.setInt(12, cliente.getEndereco().getNumero());
			}

			if(VerificadorUtil.verificarSeObjetoNuloOuVazio(cliente.getEndereco().getCodIBGE())){
				ps.setNull(13, Types.NULL);
			}
			else {
				ps.setInt(13, cliente.getEndereco().getCodIBGE());
			}

			ps.setInt(14, cliente.getId());

			ps.executeUpdate();

			retorno = true;

			conexao.commit();

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

			conexao.commit();

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