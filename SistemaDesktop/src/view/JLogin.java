package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Criptografia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario; //alteração aqui
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLogin frame = new JLogin();
					frame.setLocationRelativeTo(null); //centraliza a janela na tela
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
	public JLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 365);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 12, 237)); //alterar a cor RBG do fundo geral
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(93, 35, 341, 243);
		panel.setBackground(new Color(204, 207, 208)); //alterar a cor RBG do janela
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuário");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(51, 51, 45, 13);
		panel.add(lblNewLabel);
		
		textFieldUsuario = new JTextField(); //alteração aqui
		textFieldUsuario.setBounds(51, 74, 219, 19);
		panel.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Bem vindo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(128, 10, 98, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_2.setBounds(51, 119, 45, 13);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Criptografia criptografia = new Criptografia(passwordField.getText(), Criptografia.MD5); //chama o método de criptografia com a senha digitada e o algoritmo MD5
				System.out.println(criptografia.criptografar()); //VEJA O CODIGOO CONSOLE , exibe a senha criptografada no console
				//ESTA CONDICIONANTE VERIFICA SE OS CAMPOS ESTÃO PREENCHIDOS 
				if(textFieldUsuario.getText()!=null &&
						!textFieldUsuario.getText().isEmpty() &&
						passwordField.getText()!=null &&
						!passwordField.getText().isEmpty()) {
					if(criptografia.criptografar().equals("E10ADC3949BA59ABBE56E057F20F883E")) { //verifica se a senha e usuarios criptografada é igual a "5F4DCC3B5AA765D61D8327DEB882CF99" (senha padrão "123456")
					JOptionPane.showMessageDialog(btnNewButton, "Informações Válidas");
					dispose(); //fecha a janela de login
				//LIGANDO A TELA DE LOGIN A JANELA DE TABELA
				JPrincipal jPrincipal = new JPrincipal(); //chama a janela principal
				jPrincipal.setLocationRelativeTo(null); //centraliza a janela principal na tela
				jPrincipal.setVisible(true); //torna a janela principal visível
				}
			}else {
					JOptionPane.showMessageDialog(btnNewButton, "Verifique as informações", "Aviso", JOptionPane.WARNING_MESSAGE); //icone de aviso
				}
			}
		});
		btnNewButton.setBackground(new Color(143, 12, 237)); //alterar a cor RBG do botão
		btnNewButton.setForeground(Color.WHITE); //alterar a cor do texto do botão
		btnNewButton.setBounds(135, 197, 85, 21);
		panel.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(51, 142, 219, 19);
		panel.add(passwordField);
	}
}
