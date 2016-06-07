package g1.detran.logico;

public class Infratores {

	private int numeroDeInfracoes;
	private long cpf;
	
	public Infratores (int numeroDeInfracoes, long cpf){
		this.numeroDeInfracoes = numeroDeInfracoes;
		this.cpf = cpf;
	}

	public int getNumeroDeInfracoes() {
		return numeroDeInfracoes;
	}

	public long getCpf() {
		return cpf;
	}
	
}
