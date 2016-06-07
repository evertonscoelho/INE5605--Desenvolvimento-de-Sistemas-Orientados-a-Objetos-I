package g1.detran.logico;

import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class GeradorDeVeiculo {
	Veiculo veiculo;
	Random gerador = new Random();
	String[] letras = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	String[] numeros = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	public GeradorDeVeiculo(Deposito deposito) {
		try {
			String placa = geraPlaca();
			Long cpf = deposito.getCPFSorteado();
			int anoVeiculo = geraAno();
			veiculo = new Veiculo(placa, anoVeiculo, cpf);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");
		}
	}

	public Veiculo getVeiculoGerado() {
		return veiculo;
	}

	private String geraPlaca() {

		String retorno = "";
		for (int i = 1; i <= 3; i++) {
			retorno += letras[gerador.nextInt(letras.length - 1)];
		}
		for (int i = 1; i <= 4; i++) {
			retorno += numeros[gerador.nextInt(numeros.length - 1)];
		}

		return retorno;

	}

	private int parametro(int min, int max) {
		return min + (int) Math.round(Math.random() * (max - min));
	}

	private int geraAno() {
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date())
				.toString();
		int anoAtual = Integer.parseInt(dataHoje.substring(6));
		return parametro(1960, anoAtual);

	}

}
