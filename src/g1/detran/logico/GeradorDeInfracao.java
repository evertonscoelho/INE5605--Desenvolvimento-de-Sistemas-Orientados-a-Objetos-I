package g1.detran.logico;

import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class GeradorDeInfracao {

	Random gerador = new Random();
	Infracao infracao;	

	public GeradorDeInfracao(Deposito deposito) {
          
		try{
			int id = deposito.getQntdInfracoesCadastrados() + 1;
		String placaVeiculo = deposito.getPlacaSorteada();
		String dataIfracao = geraData();
		infracao = new Infracao(id, placaVeiculo, dataIfracao);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");
		}
	}
	
	public Infracao getInfracaoGerada(){
		return infracao;
	}

	private String geraData() {

		Date criaDataValida = null;
		int ano = parametro(1933, 2013);
		int mes = parametro(1, 12);
		int dia = parametro(1, 31);
		String data = dia + "/" + mes + "/" + ano;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			criaDataValida = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {

		}

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		data = formatador.format(criaDataValida);

		return data;

	}

	private int parametro(int min, int max) {

		return min + (int) Math.round(Math.random() * (max - min));

	}

}
