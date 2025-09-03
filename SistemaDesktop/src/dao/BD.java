package dao;

import java.sql.Connection;

import controller.Conexao;

//associando conexao
public class BD {
	private static Connection connection = null;
	
	public static void main(String[] args) {
		//condicao para abrir a conexao
		try {
			connection = Conexao.getInstancia().abrirConexao();
			System.out.println("Base criada com sucesso!");
			Conexao.getInstancia().fecharConexao();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
}
