package g1.detran.logico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import g1.detran.logico.excecoes.ExcecaoDepositoCondutorExistente;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorIdadeValida;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorInexistente;
import g1.detran.logico.excecoes.ExcecaoDepositoInfracaoDataInvalida;
import g1.detran.logico.excecoes.ExcecaoDepositoPlacasIguais;
import g1.detran.logico.excecoes.ExcecaoDepositoVeiculoInexistente;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

public class DepositoEmArquivo implements Deposito {

	String caminho;

	public DepositoEmArquivo(String caminho) throws ExcecaoErroDeAcesso {
		this.caminho = caminho;
		Charset charset = Charset.forName("ISO8859-1");
		Path infracao = Paths.get(caminho + "\\infracao.detran");
		Path veiculo = Paths.get(caminho + "\\veiculo.detran");
		Path condutor = Paths.get(caminho + "\\condutor.detran");
		Path sequencial = Paths.get(caminho + "\\sequencial.detran");
		BufferedWriter bwCondutor = null;
		BufferedWriter bwInfracao = null;
		BufferedWriter bwVeiculo = null;
		BufferedWriter bwSequencial = null;

		try {
			bwCondutor = Files.newBufferedWriter(condutor, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			bwInfracao = Files.newBufferedWriter(infracao, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			bwSequencial = Files.newBufferedWriter(sequencial, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			bwVeiculo = Files.newBufferedWriter(veiculo, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			try {
				bwInfracao.close();
				bwCondutor.close();
				bwVeiculo.close();
				bwSequencial.close();
			} catch (IOException e) {
				throw new ExcecaoErroDeAcesso();
			}
		}
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

		BufferedWriter bw = null;
		try {

			Charset charset = Charset.forName("ISO8859-1");
			Path condutorPath = Paths.get(caminho + "\\condutor.detran");
			bw = Files.newBufferedWriter(condutorPath, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			String CPF = "" + condutor.getCPF();
			while (CPF.length() != 11) {
				CPF = "0" + CPF;
			}

			String nome = condutor.getNome();
			while (nome.length() != 50) {
				nome += " ";
			}
			String condutorEmTexto = "" + CPF + "," + nome + "," + data;
			bw.write(condutorEmTexto);
			bw.newLine();

		} catch (IOException e) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
		}
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

		BufferedWriter bwInfracao = null;

		try {
			int id = leiaIdSequencial();
			String identificador = "" + (id);
			escrevaIdSequencial(identificador);
			identificador = "" + id;

			Charset charset = Charset.forName("ISO8859-1");
			Path infracaoPath = Paths.get(caminho + "\\infracao.detran");
			bwInfracao = Files.newBufferedWriter(infracaoPath, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			while (identificador.length() != 9) {
				identificador = "0" + identificador;
			}
			String dataInfracao = infracao.getDataInfracao();
			String placaVeiculo = infracao.getPlacaVeiculo();
			String infracaoEmTexto = "" + identificador + "," + dataInfracao
					+ "," + placaVeiculo;
			bwInfracao.write(infracaoEmTexto);
			bwInfracao.newLine();
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (bwInfracao != null) {
				try {
					bwInfracao.close();

				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
	}

	public int leiaIdSequencial() throws ExcecaoErroDeAcesso {

		BufferedReader br = null;
		int retorno;
		try {
			Charset charsetSequencial = Charset.forName("ISO8859-1");

			Path caminhoPathSequencial = Paths.get(caminho
					+ "\\sequencial.detran");

			br = Files.newBufferedReader(caminhoPathSequencial,
					charsetSequencial);
			String idInfracao = br.readLine();
			if (idInfracao == null) {
				idInfracao = "0";
			}
			retorno = Integer.parseInt(idInfracao) + 1;
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					throw new ExcecaoErroDeAcesso();
				}
			}

		}
		return retorno;
	}

	public void escrevaIdSequencial(String ID) throws ExcecaoErroDeAcesso {
		Charset charsetSequencial = Charset.forName("ISO8859-1");
		Path caminhoPathSequencial = Paths.get(caminho + "\\sequencial.detran");
		BufferedWriter bwSequencial = null;

		try {

			bwSequencial = Files.newBufferedWriter(caminhoPathSequencial,
					charsetSequencial, StandardOpenOption.CREATE);
			bwSequencial.write(ID);
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (bwSequencial != null) {
				try {
					bwSequencial.close();
				} catch (IOException e1) {
					throw new ExcecaoErroDeAcesso();
				}
			}

		}

	}

	public boolean dataInfracaoPosteriorAnoVeiculo(Infracao infracao)
			throws ExcecaoErroDeAcesso {
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		String ano;
		String placa;
		String textoLinha;
		boolean achou = false;
		boolean retorno = false;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				textoLinha = br.readLine();
				if (textoLinha != null) {
					placa = textoLinha.substring(0, 7);
					if (placa.equals(infracao.getPlacaVeiculo())) {
						ano = textoLinha.substring(8, 12);
						if (Integer.parseInt(ano) <= Integer.parseInt(infracao
								.getDataInfracao().substring(6))) {
							retorno = true;
							achou = true;
						}
					}
				} else {
					achou = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
	}

	public boolean veiculoExistente(Infracao infracao)
			throws ExcecaoErroDeAcesso {
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		String placa;
		String textoLinha;
		boolean achou = false;
		boolean retorno = false;
		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				textoLinha = br.readLine();
				if (textoLinha != null) {
					if (!textoLinha.equals("")) {
						placa = textoLinha.substring(0, 7);
						if (placa.equals(infracao.getPlacaVeiculo())) {
							achou = true;
							retorno = true;
						}
					}
				} else {
					achou = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
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

		BufferedWriter bw = null;
		try {
			Charset charset = Charset.forName("ISO8859-1");
			Path infracaoPath = Paths.get(caminho + "\\veiculo.detran");
			bw = Files.newBufferedWriter(infracaoPath, charset,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			String veiculoEmTexto = veiculo.getPlaca() + ","
					+ veiculo.getAnoFabricacao() + ",";
			String CPF = "" + veiculo.getCPFCondutor();
			while (CPF.length() != 11) {
				CPF = "0" + CPF;
			}
			veiculoEmTexto += CPF;
			bw.write(veiculoEmTexto);
			bw.newLine();
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
	}

	public boolean condutorExistente(long cpf) throws ExcecaoErroDeAcesso {
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\condutor.detran");
		boolean achou = false;
		boolean retorno = false;
		String CPF = null;
		String linha;
		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				linha = br.readLine();
				if (linha != null) {
					if (!linha.equals("")) {
						CPF = linha.substring(0, 11);
						long longCPF = Long.parseLong(CPF);
						if (longCPF == cpf) {
							achou = true;
							retorno = true;
						}
					}
				} else {
					achou = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
					achou = true;
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
	}

	public boolean comparaVeiculo(Veiculo veiculo) throws ExcecaoErroDeAcesso {
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		boolean achou = false;
		boolean retorno = false;
		String placa;
		String linha;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {

				linha = br.readLine();
				if (linha != null) {
					placa = linha.substring(0, 7);
					if (placa.equals(veiculo.getPlaca())) {
						achou = true;
						retorno = true;
					}
				} else {
					achou = true;
				}

			}
		} catch (EOFException e) {
			achou = true;
			retorno = false;
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
	}

	public int getAnoPelaPlaca(String placa) throws ExcecaoErroDeAcesso {
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		boolean achou = false;
		int retorno = 0;
		String placaLinha;
		String linha;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				linha = br.readLine();
				if (linha != null) {
					if (linha != ""){
					placaLinha = linha.substring(0, 7);
					if (placa.equals(placaLinha)) {
						achou = true;
						int ano = Integer.parseInt(linha.substring(8, 12));
						retorno = ano;
					}
				}
				}else{
					achou = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
	}

	public int getQntdCondutoresCadastrados() throws ExcecaoErroDeAcesso {
		File arquivo = new File(caminho + "\\condutor.detran");
		int quantidade = (int) (arquivo.length() / 74);
		return quantidade;
	}

	public int getQntdInfracoesCadastrados() throws ExcecaoErroDeAcesso {
		File arquivo = new File(caminho + "\\infracao.detran");
		int quantidade = (int) (arquivo.length() / 29);
		return quantidade;
	}

	public int getQntdVeiculosCadastrados() throws ExcecaoErroDeAcesso {
		File arquivo = new File(caminho + "\\veiculo.detran");
		int quantidade = (int) (arquivo.length() / 25);
		return quantidade;
	}

	public Condutor[] getCondutoresEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {

		Condutor[] condutores = new Condutor[proximos];
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\condutor.detran");
		int indice = 0;
		int valor = 1;
		long CPF;
		String nome;
		String linha;
		Condutor condutor;
		Date dataNascimento = null;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (indice < proximos) {
				linha = br.readLine();
				if (linha != null) {
					if (!linha.equals("")) {
						if (valor >= ultimo) {
							CPF = Long.parseLong(linha.substring(0, 11));
							DateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy");
							formatter.setLenient(false);
							dataNascimento = (java.util.Date) formatter
									.parse(linha.substring(63));
							nome = linha.substring(12, 62).trim();
							condutor = new Condutor(CPF, nome, dataNascimento);
							condutores[indice++] = condutor;
						}
						valor++;
					}
				} else {
					break;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} catch (ParseException e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return condutores;

	}

	public Infracao[] getInfracoesEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {
		Infracao[] infracoes = new Infracao[proximos];
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\infracao.detran");
		int indice = 0;
		int valor = 1;
		int id;
		String dataInfracao;
		String placa;
		String linha;
		Infracao infracao = null;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (indice < proximos) {
				linha = br.readLine();
				if (linha != null) {
					if (!linha.equals("")) {
						if (valor >= ultimo) {
							id = Integer.parseInt(linha.substring(0, 9));
							dataInfracao = linha.substring(10, 20);
							placa = linha.substring(21);
							infracao = new Infracao(id, placa, dataInfracao);
							infracoes[indice++] = infracao;
						}
						valor++;
					}
				} else {
					break;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return infracoes;
	}

	@Override
	public Veiculo[] getVeiculosEmArray(int proximos, int ultimo)
			throws ExcecaoErroDeAcesso {
		Veiculo[] veiculos = new Veiculo[proximos];
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		int indice = 0;
		int valor = 1;
		String placa;
		int anoFabricacao;
		long CPF;
		String linha;
		Veiculo veiculo = null;

		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (indice < proximos) {
				linha = br.readLine();
				if (linha != null) {
					if (!linha.equals("")) {
						if (valor >= ultimo) {
							CPF = Long.parseLong(linha.substring(13));
							placa = linha.substring(0, 7);
							anoFabricacao = Integer.parseInt(linha.substring(8,
									12));
							veiculo = new Veiculo(placa, anoFabricacao, CPF);
							veiculos[indice++] = veiculo;
						}
						valor++;
					}
				} else {
					break;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return veiculos;

	}

	@Override
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

	@Override
	public long getCPFSorteado() throws ExcecaoErroDeAcesso {
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

	@Override
	public boolean PlacaPertenceAoCpf(long CPF, String placa)
			throws ExcecaoErroDeAcesso {
		BufferedReader bw = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\veiculo.detran");
		boolean achou = false;
		boolean retorno = false;
		String placaLinha;
		String linha;
		String cpf = "" + CPF;
		try {
			bw = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				linha = bw.readLine();
				if (linha != null) {
					if (linha != "") {
						placaLinha = linha.substring(0, 7);
						if (placa.equals(placaLinha)) {
							achou = true;
							String CPFLinha = linha.substring(13, 24);
							if (CPFLinha.equals(cpf)) {
								retorno = true;
							}
						}
					}
				} else {
					achou = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return retorno;
	}

	public int getInfracoesDoCPF(long CPF) throws ExcecaoErroDeAcesso {
		BufferedReader brVeiculo = null;
		BufferedReader brInfracao = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPathVeiculo = Paths.get(caminho + "\\veiculo.detran");
		Path caminhoPathInfracao = Paths.get(caminho + "\\infracao.detran");
		boolean terminouVeiculo = false;
		boolean terminouInfracao = false;
		String placaLinha;
		String linha;
		String cpf = "" + CPF;
		int infracoesDoCPF = 0;
		String linhaInfracao;
		String placaInfracao;
		try {
			brVeiculo = Files.newBufferedReader(caminhoPathVeiculo, charset);

			while (!terminouVeiculo) {
				linha = brVeiculo.readLine();
				if (linha != null) {
					if (linha != "") {

						String CPFLinha = linha.substring(13);
						if (CPFLinha.equals(cpf)) {
							placaLinha = linha.substring(0, 7);
							terminouInfracao = false;
							try {
								brInfracao = Files.newBufferedReader(
										caminhoPathInfracao, charset);
								while (!terminouInfracao) {
									linhaInfracao = brInfracao.readLine();
									if (linhaInfracao != null) {
										if (linhaInfracao != "") {
											placaInfracao = linhaInfracao
													.substring(21);
											if (placaInfracao
													.equals(placaLinha)) {
												infracoesDoCPF++;
											}
										}
									} else {
										terminouInfracao = true;
									}
								}
							} catch (IOException e) {
								throw new ExcecaoErroDeAcesso();
							} finally {
								if (brInfracao != null) {
									try {
										brInfracao.close();
									} catch (IOException e) {
										throw new ExcecaoErroDeAcesso();
									}
								}
							}
						}
					}

				} else {
					terminouVeiculo = true;
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (brVeiculo != null) {
				try {
					brVeiculo.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return infracoesDoCPF;
	}

	public String getNomePeloCpf(long cpf) throws ExcecaoErroDeAcesso {
		BufferedReader br = null;
		Charset charset = Charset.forName("ISO8859-1");
		Path caminhoPath = Paths.get(caminho + "\\condutor.detran");
		boolean achou = false;
		String cpfLinha;
		String linha;
		String nome = "";
		String CPF = "" + cpf;
		try {
			br = Files.newBufferedReader(caminhoPath, charset);
			while (!achou) {
				linha = br.readLine();
				cpfLinha = linha.substring(0, 11);
				if (CPF.equals(cpfLinha)) {
					achou = true;
					nome = linha.substring(12, 62).trim();
				}
			}
		} catch (IOException e1) {
			throw new ExcecaoErroDeAcesso();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new ExcecaoErroDeAcesso();
				}
			}
		}
		return nome;
	}
}
