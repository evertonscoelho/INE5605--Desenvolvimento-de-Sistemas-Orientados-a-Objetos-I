package g1.detran.logico;

public class Veiculo {
	
	private String placa;
	private int anoFabricacao;
	private long CPFCondutor;
	
	public Veiculo(String placa, int anoFabricacao,long CPFCondutor){
		this.placa = placa;
		this.anoFabricacao = anoFabricacao;
		this.CPFCondutor = CPFCondutor;		
	}

	public String getPlaca() {
		return placa;
	}

	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public long getCPFCondutor() {
		return CPFCondutor;
	}


	
	
	
	
	

}
