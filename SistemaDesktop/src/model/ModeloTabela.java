package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel{

	
	private static final String[] colunas = {
			"ID", "CPF/CNPJ", "Nome", "E-mail", "Telefone", "Endere\u00E7o"
		}; //nome das coulunas da tabela
	
	private ArrayList<Cliente> clientes; //array de clientes
	
	//construtor automatico
	public ModeloTabela(ArrayList<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}

	//metodos automaticos 
	@Override
	public int getRowCount() { //vendo a lista de clientes
		return clientes.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length; //quantidade de colunas e comprimento;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente cliente = clientes.get(rowIndex);
		if (columnIndex == 0) {
			return cliente.getId();
		} else if (columnIndex == 1) {
			return cliente.getCpfCnpj();
		}else if (columnIndex == 2) {
			return cliente.getNome();
		}else if (columnIndex == 3) {
			return cliente.getEmail();
		}else if (columnIndex == 4) {
			return cliente.getTelefone();
		}else if (columnIndex == 5) {
			return cliente.getEndereco();
		}else {
			return null; //se não for nenhuma das colunas, retorna nulo
		}
	
	}
	
	//metodo para aparecer os nomes das colunas na janela
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

}
