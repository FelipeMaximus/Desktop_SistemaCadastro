package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import dao.GeraRelatorio;

import javax.swing.JScrollPane;

import model.Cliente;
import model.ModeloTabela;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldBusca;
	private JTable table;
	private ArrayList<Cliente> clientes; // Lista de clientes , coloque o import correto para ArrayList e Cliente
	private JPrincipal jPrincipal;
	private TableRowSorter<ModeloTabela> rowSorter;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPrincipal frame = new JPrincipal();
					frame.setLocationRelativeTo(null); // Centraliza a janela na tela
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Inicializa a lista de clientes
	public JPrincipal() {
		this.jPrincipal = this;
		DAO dao = new DAO();
		try {
			clientes = dao.listarCliente();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 805, 423);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//alteração aqui
		JButton btnNewButton = new JButton("Cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCadastro jCadastro = new JCadastro(null, jPrincipal);
				//setando a localização da tela de cadastro
				jCadastro.setLocationRelativeTo(jCadastro);
				//para fechar somente a tela de cadastro
				jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				//deixandoa visivel
				jCadastro.setVisible(true);
			
				
				
			}
		});
		btnNewButton.setBounds(22, 24, 106, 21);
		contentPane.add(btnNewButton);
		
		textFieldBusca = new JTextField();
		textFieldBusca.addKeyListener(new KeyAdapter() {
			//fazendo o filtro na tela
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}
		});
		textFieldBusca.setBounds(138, 25, 448, 19);
		contentPane.add(textFieldBusca);
		textFieldBusca.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 59, 665, 291);
		contentPane.add(scrollPane);
		
		ModeloTabela modeloTabela = new ModeloTabela(clientes); // Cria o modelo da tabela com a lista de clientes
		
		//alteração feita
		table = new JTable();
		table.setModel(modeloTabela);
		//evento do mouse
		table.addMouseListener(new MouseAdapter(){
			//metodo para ter a opção de alterar a tela de cadastro
			//logica para saber se foi clicado com o botao esquerdo do mouse
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1) {
					try {
						Cliente clienteSelecionado = dao.consultarCliente(modeloTabela.getValueAt(table.getSelectedRow(),0).toString());
						//isntanciado o cadastro
						JCadastro jCadastro = new JCadastro(clienteSelecionado, jPrincipal);
						//setando a localização da tela de cadastro
						jCadastro.setLocationRelativeTo(jCadastro);
						//para fechar somente a tela de cadastro
						jCadastro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						//deixandoa visivel
						jCadastro.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		//rowSorter recebe o modelo da tabela
		rowSorter = new TableRowSorter<>(modeloTabela);
		table.setRowSorter(rowSorter);
		scrollPane.setViewportView(table);
		
		//alteração aqui
		JButton btnNewButton_1 = new JButton("Gerar relatório");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aqui tras o relatorio pronto ao apertar o botao
				new GeraRelatorio();
			}
		});
		btnNewButton_1.setBounds(596, 24, 123, 21);
		contentPane.add(btnNewButton_1);
	}
	
	//metodo de filtro nas busca
	private void filtrar() {
		String busca = textFieldBusca.getText().trim();
		
		//testando o comprimento do texto digitado ,  se nao for igual a 0 faça a logica do else
		//FAÇAO TESTE DESTE METODO DE BUSCA
		if(busca.length()==0) {
			rowSorter.setRowFilter(null);
		}else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+busca));
		}
	}
}
