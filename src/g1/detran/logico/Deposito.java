package g1.detran.logico;

import g1.detran.logico.excecoes.ExcecaoDepositoCondutorExistente;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorIdadeValida;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorInexistente;
import g1.detran.logico.excecoes.ExcecaoDepositoInfracaoDataInvalida;
import g1.detran.logico.excecoes.ExcecaoDepositoPlacasIguais;
import g1.detran.logico.excecoes.ExcecaoDepositoVeiculoInexistente;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

public interface Deposito {
	
	public void armazenaCondutor(Condutor condutor)throws ExcecaoDepositoCondutorExistente,
			ExcecaoDepositoCondutorIdadeValida, ExcecaoErroDeAcesso;

	public void armazenaInfracao(Infracao infracao)
			throws ExcecaoDepositoInfracaoDataInvalida,
			ExcecaoDepositoVeiculoInexistente, ExcecaoErroDeAcesso;
	
	public boolean dataInfracaoPosteriorAnoVeiculo(Infracao infracao)throws ExcecaoErroDeAcesso;
	
	public boolean veiculoExistente(Infracao infracao)throws ExcecaoErroDeAcesso;
	
	public void armazenaVeiculo(Veiculo veiculo)
			throws ExcecaoDepositoPlacasIguais,
			ExcecaoDepositoCondutorInexistente , ExcecaoErroDeAcesso;
	
	public boolean condutorExistente(long cpf)throws ExcecaoErroDeAcesso;
	
	public boolean comparaVeiculo(Veiculo veiculo)throws ExcecaoErroDeAcesso;
	
	public int getAnoPelaPlaca(String placa) throws ExcecaoErroDeAcesso;
	
	public int getQntdCondutoresCadastrados()throws ExcecaoErroDeAcesso;
	
	public int getQntdInfracoesCadastrados()throws ExcecaoErroDeAcesso;
	
	public int getQntdVeiculosCadastrados() throws ExcecaoErroDeAcesso;
	
	public Condutor[] getCondutoresEmArray(int proximos, int ultimo)throws ExcecaoErroDeAcesso;
	
	public Infracao[] getInfracoesEmArray(int proximos, int ultimo)throws ExcecaoErroDeAcesso;
	
	public Veiculo[] getVeiculosEmArray(int proximos, int ultimo)throws ExcecaoErroDeAcesso;

	public String getPlacaSorteada()throws ExcecaoErroDeAcesso;
	
	public long getCPFSorteado()throws ExcecaoErroDeAcesso;

	public boolean PlacaPertenceAoCpf(long CPF, String placa)throws ExcecaoErroDeAcesso;
	
	public int leiaIdSequencial() throws ExcecaoErroDeAcesso;
	
	public void escrevaIdSequencial(String ID)throws ExcecaoErroDeAcesso;
	
	public int getInfracoesDoCPF(long CPF)throws ExcecaoErroDeAcesso;

	public String getNomePeloCpf(long cpf)throws ExcecaoErroDeAcesso;
	
}
