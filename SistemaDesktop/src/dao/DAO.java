package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Conexao;
import model.Cliente;
import model.Usuario;

//conexao com o banco de dados
public class DAO {

	
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	//querys
	private static String CADASTRAR_CLIENTE = "INSERT INTO CLIENTE "
			+ " (ID, NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO) "
			+ " VALUES (NULL, ?, ?, ?, ?, ?) ";
	
	private static String CONSULTAR_CLIENTE = "SELECT * FROM CLIENTE " + " WHERE ID = ? ";
	
	private static String ALTERAR_CLIENTE = "UPDATE CLIENTE SET " 
			+ " NOME = ?, CPFCNPJ = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ? " + " WHERE ID = ? ";
	
	private static String EXCLUIR_CLIENTE = "DELETE FROM CLIENTE " + " WHERE ID = ? ";
	
	private static String LISTAR_CLIENTES = "SELECT * FROM CLIENTE " + " WHERE 1=1 ";
	
	private static String CONSULTAR_USUARIO = "SELECT USUARIO, SENHA " + " FROM USUARIO " + " WHERE USUARIO = ? "
			+ " AND SENHA = ? ";
	
	//OBS: os metodos abaixo estao referenciando todas as querys acima com os seus mesmos valores
	
	// Construtor vazio
	public DAO() {
		
	}
	
	// Método para cadastrar um cliente no banco de dados
	//este metodo abaixo sera replicado mais cinco vezes
	public void cadastrarCliente(Cliente cliente) {
		Connection connection = Conexao.getInstancia().abrirConexao();
		
		String query = CADASTRAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			//NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO
			int i = 1;
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getCpfCnpj());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());

			preparedStatement.execute(); //executa a querry no sistema
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Cliente incluido com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}
	
	public Cliente consultarCliente(String id) throws Exception {
		Connection connection = Conexao.getInstancia().abrirConexao();
		Cliente cliente = null;
		String query = CONSULTAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			//NOME, CPFCNPJ, EMAIL, TELEFONE, ENDERECO
			int i = 1;
			preparedStatement.setString(i++, id);
			
			resultSet = preparedStatement.executeQuery();

			//condicao para verificar se encontrou o cliente
			while (resultSet.next()) {
				cliente = new Cliente(  resultSet.getString("ID"),
										resultSet.getString("nome"),
										resultSet.getString("CPFCNPJ"),
										resultSet.getString("EMAIL"),
										resultSet.getString("TELEFONE"),
										resultSet.getString("ENDERECO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		if (cliente == null) {
			JOptionPane.showMessageDialog(null, "Não foi possível encontrar o cliente selecionado ", "", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Não foi possível localizar o cliente selecionado ");
		}
		return cliente;
	}
	
	public void alterarCliente(String id, Cliente cliente) {
		Connection connection = Conexao.getInstancia().abrirConexao();
		
		String query = ALTERAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getCpfCnpj());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());
			preparedStatement.setString(i++, id);

			preparedStatement.execute();
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}
	
	public void excluirCliente(String id) {
		Connection connection = Conexao.getInstancia().abrirConexao();
		
		String query = EXCLUIR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			int i = 1;
			
			preparedStatement.setString(i++, id);

			preparedStatement.execute();
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
	}
	
	public ArrayList<Cliente> listarCliente() throws Exception {
		Connection connection = Conexao.getInstancia().abrirConexao();
		ArrayList<Cliente> clientes = new ArrayList<>();
		String query = LISTAR_CLIENTES;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();

			//condicao para verificar se encontrou o cliente
			while (resultSet.next()) {
				clientes.add( new Cliente(  resultSet.getString("ID"),
										resultSet.getString("nome"),
										resultSet.getString("CPFCNPJ"),
										resultSet.getString("EMAIL"),
										resultSet.getString("TELEFONE"),
										resultSet.getString("ENDERECO")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		if (clientes.size() < 0) {
			JOptionPane.showMessageDialog(null, "Não ha clientes cadastrados ", "", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Não ha clientes cadastrados ");
		}
		return clientes;
	}
	
	public Usuario consultarUsuario(String nomeUsuario, String senhaCriptografada) throws Exception {
		Connection connection = Conexao.getInstancia().abrirConexao();
		Usuario usuario = null;
		String query = CONSULTAR_USUARIO;
		try {
			preparedStatement = connection.prepareStatement(query);
			
			
			int i = 1;
			
			preparedStatement.setString(i++, nomeUsuario);
			preparedStatement.setString(i++, senhaCriptografada);
			
			resultSet = preparedStatement.executeQuery();

			//condicao para verificar se encontrou o cliente
			while (resultSet.next()) {
				usuario = new Usuario(  resultSet.getInt("ID"),
										resultSet.getString("USARIO"),
										resultSet.getString("SENHA"));
										
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		if (usuario == null) {
			JOptionPane.showMessageDialog(null, "Não foi possível encontrar o usuario selecionado ", "", JOptionPane.ERROR_MESSAGE);
			throw new Exception("Não foi possível localizar o cliente selecionado ");
		}
		return usuario;
	}
	
	private void fecharConexao() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			Conexao.getInstancia().fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
