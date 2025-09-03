package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.Backup;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JBackup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> arquivosBackup;
	private Backup backup;
	private String[] nomesBackup;
	private String itemSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JBackup frame = new JBackup();
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
	public JBackup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 592, 187);
		contentPane.add(scrollPane);
		
		//alterações
		backup = new Backup();
		arquivosBackup = new ArrayList<>();
		arquivosBackup = backup.listarArquivos(); //chamando o metodo listar arquivos
		nomesBackup = arquivosBackup.toArray(new String[arquivosBackup.size()]);
		
		JList list = new JList();
		//adiciona lista de dados
		list.setListData(nomesBackup); //setando lista de dados
		
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Gerar Backup");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//confirmação para gerar o backup
				if(JOptionPane.showConfirmDialog(btnNewButton, "Deseja gerar o backup? ")==JOptionPane.YES_NO_OPTION) {
					backup.gerarBackup();
					arquivosBackup = backup.listarArquivos();
					nomesBackup = arquivosBackup.toArray(new String[arquivosBackup.size()]);
					list.setListData(nomesBackup); //setando o JList
					revalidate(); //metodo para gerar a tela
					repaint(); //metodo para gerar a tela
				}
			}
		});
		btnNewButton.setBounds(10, 232, 120, 21);
		contentPane.add(btnNewButton);
		
		JButton btnRestaurarBackup = new JButton("Restaurar Backup");
		btnRestaurarBackup.setEnabled(false);//botao vem desabilitado
		btnRestaurarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento para restaurar o backup
				if(JOptionPane.showConfirmDialog(btnRestaurarBackup, "Deseja restaurar o backup? ")==JOptionPane.YES_NO_OPTION) {
					try {
						backup.restaurarBackup(itemSelecionado);
					} catch(FileNotFoundException  e1) {
						e1.printStackTrace();
					}catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnRestaurarBackup.setBounds(152, 232, 150, 21);
		contentPane.add(btnRestaurarBackup);
		
		//saber o caminho do backup
		list.addListSelectionListener(new ListSelectionListener() {
			
			
			//metodo para alterar
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					//logica para trocaar de item
					if(list.getSelectedIndex() == -1) {
						list.setSelectedIndex(e.getFirstIndex());
					}
					itemSelecionado = ((JList<String>)e.getSource()).getSelectedValue();
					if(itemSelecionado != null) {
						btnRestaurarBackup.setEnabled(true);//habilitar o botao
					}
				}
			}
		});
		
	}
}
