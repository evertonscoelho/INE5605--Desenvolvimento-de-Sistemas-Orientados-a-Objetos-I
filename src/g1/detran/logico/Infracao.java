package g1.detran.logico;

public class Infracao {
	
	private int identificador;
	private String placaVeiculo;
	private String dataInfracao;
	
	public Infracao(int identificador, String placaVeiculo,String dataInfracao){
		this.identificador = identificador;
		this.placaVeiculo = placaVeiculo;
		this.dataInfracao = dataInfracao;		
	}

	public int getIdentificador() {
		return identificador;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public String getDataInfracao() {
		return dataInfracao;
	}
	
	
}
