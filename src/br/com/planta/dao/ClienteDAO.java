package br.com.planta.dao;

import br.com.planta.factory.ConnectionFactory;
import br.com.planta.model.ClienteBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {
    
    Connection conexao = null;
            
    
     public List<ClienteBean> buscaNome(String nome){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "select * from vendas.clientes where upper(nome) like upper(?) order by nome";
        
        List<ClienteBean> lista = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%"+nome+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                ClienteBean p = new ClienteBean();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome").toUpperCase());
                p.setTelefone1(rs.getInt("telefone1"));
                p.setTelefone2(rs.getInt("telefone2"));
                
                lista.add(p);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return lista;
    }
    
    public boolean deleteCliente(ClienteBean p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "delete from vendas.clientes where id=?";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setInt(1, p.getId());
            
            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public boolean editeCliente(ClienteBean p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "update vendas.clientes set nome=?, telefone1=?, telefone2=? where id=?";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome().toUpperCase());
            ps.setInt(2, p.getTelefone1());
            ps.setInt(3, p.getTelefone2());
            ps.setInt(4, p.getId());
            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public boolean insereCliente(ClienteBean p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "insert into vendas.clientes (nome, telefone1, telefone2) values (?,?,?)";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome().toUpperCase());
            
            if (p.getTelefone1() == null) {
				ps.setNull(2, Types.NULL);
			} else {
				ps.setInt(2, p.getTelefone1());
			}
            
            if (p.getTelefone2() == null) {
				ps.setNull(3, Types.NULL);
			} else {
				ps.setInt(3, p.getTelefone2());
			}
            
            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public List<ClienteBean> listarClientes(){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "select * from vendas.clientes order by nome";
        
        List<ClienteBean> lista = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                ClienteBean p = new ClienteBean();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTelefone1(rs.getInt("telefone1"));
                p.setTelefone2(rs.getInt("telefone2"));
                
                lista.add(p);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return lista;
    }
    
}
