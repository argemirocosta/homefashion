package br.com.planta.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String DRIVER_CLASS = "org.postgresql.Driver";

    
    private static final String URL = "jdbc:postgresql://localhost:5432/vendasEmCasa";
    private static final String USER = "postgres";
    private static final String PASS = "post";

	public static Connection getConnection() {

		Connection conexao = null;

		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		try {
			System.out.println("Conectando ao banco de dados...");
			conexao = DriverManager.getConnection(URL, USER, PASS);
			conexao.setAutoCommit(true);
			System.out.println("Conexão realizada com sucesso!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return conexao;
	}
}