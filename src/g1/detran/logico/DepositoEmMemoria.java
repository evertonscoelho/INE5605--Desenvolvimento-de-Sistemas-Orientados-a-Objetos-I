package g1.detran.logico;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import g1.detran.logico.Condutor;
import g1.detran.logico.Infracao;
import g1.detran.logico.Veiculo;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorExistente;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorIdadeValida;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorInexistente;
import g1.detran.logico.excecoes.ExcecaoDepositoInfracaoDataInvalida;
import g1.detran.logico.excecoes.ExcecaoDepositoPlacasIguais;
import g1.detran.logico.excecoes.ExcecaoDepositoVeiculoInexistente;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

public class DepositoEmMemoria implements Deposito {

	private Map<Long, Condutor> mapCondutores;
	private Map<String, Veiculo> mapVeiculos;
	private Map<Integer, Infracao> mapInfracoes;

	public DepositoEmMemoria() {
		mapCondutores = new HashMap<>();
		mapVeiculos = new HashMap<>();
		mapInfracoes = new HashMap<>();
	}

	public void armazenaCondutor(Condutor condutor)
			throws ExcecaoDepositoCondutorExistente,
			ExcecaoDepositoCondutorIdadeValida, ExcecaoErroDeAcesso {

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		String data = formatador.format(condutor.getDataNascimento());

		VerificaData verificaData = new VerificaData(data);
		if (condutorExistente(condutor.getCPF())) {
			throw new ExcecaoDepositoCondutorExistente();
		}
		if (!verificaData.idadeValida()) {
			throw new ExcecaoDepositoCondutorIdadeValida();
		}
		mapCondutores.put(condutor.getCPF(), condutor);
	}

	public void armazenaInfracao(Infracao infracao)
			throws ExcecaoDepositoInfracaoDataInvalida,
			ExcecaoDepositoVeiculoInexistente, ExcecaoErroDeAcesso {
		if (!dataInfracaoPosteriorAnoVeiculo(infracao)) {
			throw new ExcecaoDepositoInfracaoDataInvalida();
		}

		if (!veiculoExistente(infracao)) {
			throw new ExcecaoDepositoVeiculoInexistente();
		}

		mapInfracoes.put(infracao.getIdentificador(), infracao);

	}

	public boolean dataInfracaoPosteriorAnoVeiculo(Infracao infracao)
			throws ExcecaoErroDeAcesso {
		boolean retorno = false;
		Veiculo veiculo = mapVeiculos.get(infracao.getPlacaVeiculo());
		if (veiculo.getAnoFabricacao() <= Integer.parseInt(infracao
				.getDataInfracao().substring(6))) {
			retorno = true;
		}
		return retorno;
	}

	public boolean veiculoExistente(Infracao infracao)
			throws ExcecaoErroDeAcesso {
		if (mapVeiculos.containsKey(infracao.getPlacaVeiculo())) {
			return true;
		}
		return false;
	}

	public void armazenaVeiculo(Veiculo veiculo)
			throws ExcecaoDepositoPlacasIguais,
			ExcecaoDepositoCondutorInexistente, ExcecaoErroDeAcesso {
		if (!condutorExistente(veiculo.getCPFCondutor())) {
			throw new ExcecaoDepositoCondutorInexistente();
		}
		if (comparaVeiculo(veiculo)) {
			throw new ExcecaoDepositoPlacasIguais();
		}
		mapVeiculos.put(veiculo.getPlaca(), veiculo);
	}

	public boolean condutorExistente(long cpf) throws ExcecaoErroDeAcesso {
		if (mapCondutores.containsKey(cpf)) {
			return true;
		}
		return false;
	}

	public boolean comparaVeiculo(Veiculo veiculo) throws ExcecaoErroDeAcesso {
		if (mapVeiculos.containsKey(veiculo.getPlaca())) {
			return true;
		}
		return false;
	}

	public int getAnoPelaPlaca(String placa) throws ExcecaoErroDeAcesso {
		if (mapVeiculos.containsKey(placa)) {
			return mapVeiculos.get(placa).getAnoFabricacao();
		}
		return 0;
	}

	public int getQntdCondutoresCadastrados() throws ExcecaoErroDeAcesso {
		return mapCondutores.size();
	}

	public int getQntdInfracoesCadastrados() throws ExcecaoErroDeAcesso {
		return mapInfracoes.size();
	}

	public int getQntdVeiculosCadastrados() throws ExcecaoErroDeAcesso {
		return mapVeiculos.size();
	}

	public Condutor[] getCondutoresEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {
		Condutor[] condutores = new Condutor[proximos];
		int valor = 1;
		int indice = 0;
		for (Condutor condutor : mapCondutores.values()) {
			if (indice >= proximos) {
				break;
			}
			if (valor >= ultimo) {
				condutores[indice++] = condutor;
			}
			valor++;
		}
		return condutores;
	}

	public Infracao[] getInfracoesEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {
		Infracao[] infracoes = new Infracao[proximos];
		for (int indice = 0; indice < proximos; indice++) {
			infracoes[indice] = mapInfracoes.get(ultimo++);
		}
		return infracoes;
	}

	public Veiculo[] getVeiculosEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {
		Veiculo[] veiculos = new Veiculo[proximos];
		int valor = 1;
		int indice = 0;
		for (Veiculo veiculo : mapVeiculos.values()) {
			if (indice >= proximos) {
				break;
			}
			if (valor >= ultimo) {
				veiculos[indice++] = veiculo;
			}
			valor++;
		}
		return veiculos;
	}

	public String getPlacaSorteada() throws ExcecaoErroDeAcesso {
		boolean achou = false;
		Random gerador = new Random();
		Veiculo[] veiculoSorteado = null;
		while (!achou) {
			veiculoSorteado = getVeiculosEmArray(1,
					gerador.nextInt(getQntdVeiculosCadastrados()));
			if (veiculoSorteado[0] != null) {
				achou = true;
			}
		}
		return veiculoSorteado[0].getPlaca();
	}

	public long getCPFSorteado()throws ExcecaoErroDeAcesso {
		boolean achou = false;
		Random gerador = new Random();
		Condutor[] condutorSorteado = null;
		while (!achou) {
			condutorSorteado = getCondutoresEmArray(1,
					gerador.nextInt(getQntdCondutoresCadastrados()));
			if (condutorSorteado[0] != null) {
				achou = true;
			}
		}
		return condutorSorteado[0].getCPF();
	}

	public boolean PlacaPertenceAoCpf(long CPF, String placa)throws ExcecaoErroDeAcesso {
		if (mapVeiculos.get(placa).getCPFCondutor() == CPF) {
			return true;
		}
		return false;

	}


	public int leiaIdSequencial() throws ExcecaoErroDeAcesso {

		return 0;
	}


	public void escrevaIdSequencial(String ID) throws ExcecaoErroDeAcesso {
		
	}
	
	public int getInfracoesDoCPF (long CPF)throws ExcecaoErroDeAcesso{
		
		int numeroDeInfracoes = 0;
		for (Infracao infracao : mapInfracoes.values()) {
			String placa = infracao.getPlacaVeiculo();
			if (mapVeiculos.get(placa).getCPFCondutor() == CPF) { 
			numeroDeInfracoes++;
			}
		}
		return numeroDeInfracoes;
	}
	public String getNomePeloCpf(long cpf)throws ExcecaoErroDeAcesso{
		return mapCondutores.get(cpf).getNome();
	}
}
