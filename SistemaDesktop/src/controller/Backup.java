package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

public class Backup {
	
	//metodo para formato de data e hora
	private static final SimpleDateFormat dataHora = new SimpleDateFormat("ddMMyyyy_HHmmss");
	
	private String pathAbsolutoParcial() {
		File file = new File("Backup.java");
		
		String pathAbsolutoAtual = file.getAbsolutePath();
		
		String pathAbsolutoParcial = null;
		
		pathAbsolutoParcial = pathAbsolutoAtual.substring(0, pathAbsolutoAtual.lastIndexOf('\\')); //caminho para o backup
		
		return pathAbsolutoParcial;
	}
	
	//metodo para listar todos os arquivos de backup
	public ArrayList<String> listarArquivos(){
		String pathDiretorio = pathAbsolutoParcial();
		File diretorio = new File(pathDiretorio);
		
		ArrayList<String> arquivosBackups = new ArrayList<>(); //onde contem os arquivos
		
		//verifica se tem arquivo no diretorio
		if(diretorio.exists()) {
			File[] arquivosDiretorio = diretorio.listFiles(); //lista todos os arquivos com vetores
			
			//ve se tem arquivo no diretorio e o comprimento e maior que zeero
			if(arquivosDiretorio != null && arquivosDiretorio.length>0) {
				//for percorre cada arquivo
				for(File arquivo: arquivosDiretorio) {
					//verifica se é um arquivo
					if(arquivo.isFile()) {
						//verifica se contem o nome BACKUP e adiciona todos os arquivos e direciona o caminho de arquivo de backup
						if(arquivo.getName().contains("backup")) {
							arquivosBackups.add(arquivo.getAbsolutePath());
						}
					}
				}
			}
		}
	
	return arquivosBackups; //reetorna o caminho dos arquivos

}
	
	//metodo para gerar o backup
	public void gerarBackup() {
		StringBuilder pathDiretorio = new StringBuilder(pathAbsolutoParcial());//caminho para colocar o backup
		
		StringBuilder zipPath = new StringBuilder();
		
		pathDiretorio.append("\\");//adicionando texto
		
		zipPath.append(pathDiretorio);
		zipPath.append("backup");
		zipPath.append(dataHora.format(new Date())); //adicionando data e hora atual
		zipPath.append(".zip");//formato .zip
		
		FileOutputStream fos = null;
		ZipOutputStream zip = null;
		
		try {
			fos = new FileOutputStream(zipPath.toString());
			zip = new ZipOutputStream(fos);
			
			pathDiretorio.append("resources");
			adicionarAoZip("", pathDiretorio.toString(), zip); //chamando metodo que adiciona o arquivo ao diretorio
			
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			
			try {
				//fechando o file e o zip
				zip.close();
				fos.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JOptionPane.showMessageDialog(null, "Backup gerado com sucesso");
		
	}
		
	//metodo para adicionar o arquivo zip
		private void adicionarAoZip(String caminhoZip, String diretorioPath, ZipOutputStream zip) throws IOException{
			File diretorio = new File(diretorioPath);
			
			//percorre todos os arquivos do diretorio
			for(String nomeArquivo : diretorio.list()) {
				String caminhoCompletoArquivo = diretorioPath + "/" + nomeArquivo;
				
				//verifica se é um arquivo ou diretorio, se for diretorio adiciona ao zip
				if(new File(caminhoCompletoArquivo).isDirectory()) {
					adicionarAoZip(caminhoZip+nomeArquivo + "/", caminhoCompletoArquivo, zip);
					continue;
				}
				
				//nova entrada ao arquivo zip
				ZipEntry zipEntry = new ZipEntry(caminhoZip + nomeArquivo);
				
				zip.putNextEntry(zipEntry);
				
				FileInputStream fileInputStream = new FileInputStream(caminhoCompletoArquivo);
				
				byte[] buffer = new byte[1024];
				
				int i;
				
				while((i = fileInputStream.read(buffer))>0) {
					zip.write(buffer, 0, i);
				}
				
				fileInputStream.close(); //fechando o file
			}
	}
		
		//metodo para restaurar o backup
		public void restaurarBackup(String caminhoArquivoZip) throws FileNotFoundException, IOException {
			byte[] buffer = new byte[1024];
			
			try(ZipInputStream zipInputStream =new ZipInputStream(new FileInputStream(caminhoArquivoZip))) {
				ZipEntry entry;
				
				//percorrendo todos os arquivos do diretorio
				while((entry = zipInputStream.getNextEntry())!=null) {
					String nomeArquivo = entry.getName();
					File arquivo = new File(pathAbsolutoParcial()+ "\\resources" + File.separator + nomeArquivo);
					
					//verifica se a entrada é um diretorio
					if(entry.isDirectory()) {
						arquivo.mkdirs();
						continue;
					}
					
					File parent = arquivo.getParentFile();
					
					//verificação
					if(!parent.exists()) {
						parent.mkdirs();
					}
					
					try(FileOutputStream fileOutputStream = new FileOutputStream(arquivo)){
						int i;
						//le o arquivo  e escreve
						while((i = zipInputStream.read(buffer))>0) {
							fileOutputStream.write(buffer, 0, i);
						}
					}
;				}
				JOptionPane.showMessageDialog(null, "Backup restaurado com sucesso");
			}
		}
	
}
