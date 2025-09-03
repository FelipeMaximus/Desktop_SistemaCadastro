package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.LineBorder;

import dao.DAO;
import model.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNome;
	private JTextField textFieldCpfCnpj;
	private JTextField textFieldTelefone;
	private JTextField textFieldEmail;
	private JTextArea textAreaEndereco;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JCadastro frame = new JCadastro(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JCadastro(Cliente clienteSelecionado, JPrincipal jPrincipal) {
		//alteração aqui
		DAO dao =new DAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 326);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 10, 45, 13);
		contentPane.add(lblNewLabel);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(10, 25, 396, 19);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("CPF/CNPJ");
		lblNewLabel_1.setBounds(10, 65, 85, 13);
		contentPane.add(lblNewLabel_1);
		
		textFieldCpfCnpj = new JTextField();
		textFieldCpfCnpj.setBounds(10, 83, 202, 19);
		contentPane.add(textFieldCpfCnpj);
		textFieldCpfCnpj.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Telefone");
		lblNewLabel_2.setBounds(222, 65, 85, 13);
		contentPane.add(lblNewLabel_2);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setBounds(222, 83, 184, 19);
		contentPane.add(textFieldTelefone);
		textFieldTelefone.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(10, 126, 85, 13);
		contentPane.add(lblNewLabel_3);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(10, 141, 396, 19);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setBounds(10, 170, 85, 13);
		contentPane.add(lblNewLabel_4);
		
		textAreaEndereco = new JTextArea();
		textAreaEndereco.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaEndereco.setBounds(10, 187, 396, 44);
		contentPane.add(textAreaEndereco);
		
		JButton btnNewButton = new JButton(clienteSelecionado==null?"Incluir":"Alterar"); //condição se e se não
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DEPOIS DE TESTAR O METODO ALTERAR ABAIXO, TESTE ESSE AQUI NA TELA JPrincipal, PARA DE FATO FAZER ALTERAÇÃO, FEITO ABRA DE NOVO A TELA PARA
				//VER A ALTERAÇÃO DA TELA
			
				//String id, String nome, String cpfCnpj, String email, String telefone, String endereco
				//chamando a classe cadastrar
				Cliente cliente = new Cliente(null, textFieldNome.getText(), textFieldCpfCnpj.getText(),
						textFieldEmail.getText(), textFieldTelefone.getText(), textAreaEndereco.getText());
				if (clienteSelecionado == null) {
					//condição para inserir os valore de nome e cpf
					//TESTE ESTE METODO NA TELA JPrincipal
					if(!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
						dao.cadastrarCliente(cliente);
						abrirTelaPrincipal(jPrincipal);
					}else {
						JOptionPane.showMessageDialog(null, "Confira os campos Nome e CPF/CNPJ");
					}
				}else {
					if(!"".equalsIgnoreCase(textFieldNome.getText()) && !"".equalsIgnoreCase(textFieldCpfCnpj.getText())) {
						dao.alterarCliente(clienteSelecionado.getId(), cliente);
						abrirTelaPrincipal(jPrincipal);
					}else {
						JOptionPane.showMessageDialog(null, "Confira os campos Nome e CPF/CNPJ");
					}
					
				}
			}
		});
		btnNewButton.setBounds(324, 241, 85, 21);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Excluir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//metodo para excluir
				dao.excluirCliente(clienteSelecionado.getId());
				//teste se esta excluindo na tela de JPrincipal
				abrirTelaPrincipal(jPrincipal);
				//teste na tela JPrincipal que ao fazer a exclusao a tela atualiza sozinha
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setBounds(10, 241, 85, 21);
		//aqui o botao excluir fica nao visivel
		btnNewButton_1.setVisible(false);
		contentPane.add(btnNewButton_1);
		
		//chamando o metodo preencherCampo
		if(clienteSelecionado!=null) {
			preencherCampos(clienteSelecionado);
			//caso seja feita uma alteraçao na janela o botao excluir fica visivel
			btnNewButton_1.setVisible(true);
		}

	}
	//fazendo preencheminto dos campos  quando for alterar a tela cadastro
	//depois dessa parte execute a tela JPrincipal para testar a alteracao ao clicalo
	private void preencherCampos(Cliente clienteSelecionado) {
		textFieldNome.setText(clienteSelecionado.getNome());
		textFieldCpfCnpj.setText(clienteSelecionado.getCpfCnpj());
		textFieldEmail.setText(clienteSelecionado.getEmail());
		textFieldTelefone.setText(clienteSelecionado.getTelefone());
		textAreaEndereco.setText(clienteSelecionado.getEndereco());
	}
	
	//metodo para atualizar a tela ao fazer uma cadastro
	// FAÇA O TESTE NA TELA JPrincipal
	private void abrirTelaPrincipal(JPrincipal jPrincipal) {
		jPrincipal.dispose();
		dispose();
		jPrincipal = new JPrincipal();
		jPrincipal.setLocationRelativeTo(jPrincipal);
		jPrincipal.setVisible(true);
	}
}
