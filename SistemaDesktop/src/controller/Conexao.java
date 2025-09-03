package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	//variavel de conexao com o banco
	private static Conexao instancia;
	//varivael de base de dados
	private static String DRIVER = "org.sqlite.JDBC"; //BANCO EMBARCADONA IDE
	private static String BD = "jdbc:sqlite:resources./bdclientes.db"; 
	private static Connection conexao;
	
	//construtor
	private Conexao() {

	}
	
	public static Conexao getInstancia() {
		if (instancia == null) {
			instancia = new Conexao();
		}
		return instancia;
	}
	
	
	
	//metodo para abrir a conexao
	public Connection abrirConexao() {
		try {
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(BD);
				conexao.setAutoCommit(false);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
		}
		return conexao;
	}
	
	//metodo para fechar a conexao
	public void fecharConexao() {
		try {
			if (conexao != null && !conexao.isClosed()) {
				conexao.close();//se aceitar esta condição encerra a conexão
			}
		} catch (SQLException e) {
			System.out.println("Erro ao fechar a conexao: " + e.getMessage());
		} finally {
			conexao = null;
		}
		
	}
}
