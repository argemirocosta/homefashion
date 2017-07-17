package br.com.planta.dao;

import br.com.planta.factory.ConnectionFactory;
import br.com.planta.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    private Connection conexao;
    
    public Usuario login(Usuario usuario) {
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "select * from vendas.usuario where login = ? and senha = ?";
        
        Usuario u = null;
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getLogin().toUpperCase());
            ps.setString(2, usuario.getSenha());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setAtivo(rs.getBoolean("ativo"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
}