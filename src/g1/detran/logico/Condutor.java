package g1.detran.logico;

import java.util.Date;

public class Condutor{
	private long CPF;
	private String nome;
	private Date dataNascimento; 
	
	public Condutor(long CPF, String nome, Date dataNascimento){
		this.CPF = CPF;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	
	public long getCPF(){
		return CPF;
	}
	
	public String getNome(){
		return nome;
	}
	
	public Date getDataNascimento(){
		return dataNascimento;
	}

}
