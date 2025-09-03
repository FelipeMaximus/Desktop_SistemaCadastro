package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

	public static final String SHA256 = "SHA-256"; //algoritmo de criptografia SHA-256
	public static final String MD5 = "MD5"; //algoritmo de criptografia MD5
	
	protected String informacao;
	protected String padrao;
	
	// Construtor automatico
	public Criptografia(String informacao, String padrao) {
		super();
		this.informacao = informacao;
		this.padrao = padrao;
	}

	
	// Get e Set automaticos de informação e padrao
	public String getInformacao() {
		return informacao;
	}

	public void setInformacao(String informacao) {
		this.informacao = informacao;
	}

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	} 
	
	
	//metodo de criptografia
	public String criptografar() {
		String informacao = getInformacao();
		
		MessageDigest messageDigest;
		StringBuilder hexString = null;
		
		try {
			messageDigest = MessageDigest.getInstance(getPadrao()); //instancia o algoritmo de criptografia
			byte[] hash = messageDigest.digest(
			informacao.getBytes(StandardCharsets.UTF_8)); //gera o hash da informação
			hexString = new StringBuilder(2* hash.length); //converte o hash para hexadecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]); //converte o byte para hexadecimal
				if (hex.length() == 1) {
					hexString.append('0'); // adiciona um zero a esquerda se o tamanho for 1
				}
				hexString.append(hex); //adiciona o hexadecimal a string
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
			return hexString.toString().toUpperCase(); //retorna a string hexadecimal;
	}
	
	
}
