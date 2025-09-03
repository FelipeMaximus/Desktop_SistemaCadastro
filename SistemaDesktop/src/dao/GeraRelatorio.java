package dao;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import controller.Conexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class GeraRelatorio {
	
	//instanciando a conexao
	public GeraRelatorio() {
		Connection connection = Conexao.getInstancia().abrirConexao();
		
		//logica para pega o caminho absoluto do relatorio
		File file = new File("GeraRelatorio.java");
		String pathAbsoluto = file.getAbsolutePath();
		//aqui pega o começo do caminho do banco e a parte do caminho do relatorio usando o metodo lastIndexOf
		String pathAbsolutoParcial = pathAbsoluto.substring(0,pathAbsoluto.lastIndexOf('\\'))+"\\relatorios\\Coffee.jrxml";
		
		try {
			//criando objeto e usando metodo para compilalo, adicionando o caminho relativo do banco
			JasperReport jasperReport = JasperCompileManager.compileReport(pathAbsolutoParcial);
			//objeto do jasper
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(),connection);
			
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			//para nao fechar o sistema
			jasperViewer.setVisible(true);
			
			//OBS: SEMPRE PRECISAMOS FECHAR A CONEXAO PARA QUE TUDO FUNCIONE
			Conexao.getInstancia().fecharConexao();
			
			//COM TUDO PRONTO FAPA O TESTE DO RELATORIO NA JANELA PRINCIPAL
			
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
