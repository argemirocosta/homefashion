package br.com.homefashion.dao;

import br.com.homefashion.factory.ConnectionFactory;
import br.com.homefashion.model.Usuario;
import br.com.homefashion.util.SessaoUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static br.com.homefashion.shared.Queries.*;
import static br.com.homefashion.shared.Sessao.*;

public class UsuarioDAO {

	private Connection conexao;

	public Usuario login(Usuario usuario) {

		conexao = ConnectionFactory.getConnection();

		Usuario u = null;

		try {
			PreparedStatement ps = conexao.prepareStatement(SELECT_LOGIN);
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
				SessaoUtil.adicionarNaSessao(u, USUARIO_SESSAO);
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

		boolean retorno = false;

		try {
			PreparedStatement ps = conexao.prepareStatement(INSERIR_USUARIO);
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