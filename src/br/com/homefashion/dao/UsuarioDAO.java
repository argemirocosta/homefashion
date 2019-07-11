package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.SessionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

	private Connection conexao;

	public Usuario login(Usuario usuario) {

		conexao = ConnectionFactory.getConnection();
		
		String sql = "SELECT id, nome, login, senha, ativo FROM vendas.usuario WHERE login = ? AND senha = ?";

		Usuario u = null;

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getLogin().toUpperCase());
			ps.setString(2, usuario.getSenha());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u = new Usuario();
				u.setId(rs.getInt("id"));
				u.setNome(rs.getString("nome"));
				u.setLogin(rs.getString("login"));
				u.setSenha(rs.getString("senha"));
				u.setAtivo(rs.getBoolean("ativo"));
				
			}
			
			if(u != null){
				SessionUtil.adicionarNaSessao(u.getId(), "usuario");
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
		return u;
	}
	
	public Boolean inserirUsuario(Usuario usuario) {

		conexao = ConnectionFactory.getConnection();

		Boolean retorno = false;

		String sql = "INSERT INTO vendas.usuario (nome, login, senha, ativo) VALUES (?,?,?,TRUE)";

		try {
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, usuario.getNome().toUpperCase());
			ps.setString(2, usuario.getLogin().toUpperCase());
			ps.setString(3, usuario.getSenha().toUpperCase());

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