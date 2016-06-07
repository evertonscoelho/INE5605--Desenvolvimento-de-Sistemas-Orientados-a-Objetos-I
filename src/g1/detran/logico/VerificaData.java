package g1.detran.logico;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerificaData {

	String dataAnterior = "";
	String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date())
			.toString();
	String data;

	public VerificaData(String data) {
		this.data = data;
	}

	public boolean verifica() {
		String texto = data;
		if (texto.indexOf("_") == -1) {
			Date date = null;
			try {
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				formatter.setLenient(false);
				date = (java.util.Date) formatter.parse(texto);
			} catch (ParseException e) {
				if (texto.length() == 10 && date == null) {
					return false;
				}

			}
			return true;

		}
		return false;
	}

	public boolean comparaDataFutura() {
		String texto = data;
		String conversor = texto.substring(6);
		int comparadorAno = Integer.parseInt(conversor);
		String verificadorAno = dataHoje.substring(6);
		int comparaAnoAtual = Integer.parseInt(verificadorAno);
		int totalAno = comparaAnoAtual - comparadorAno;
		if (totalAno < 0) {
			return false;
		} else if (comparaAnoAtual == comparadorAno) {
			String mes = texto.substring(3, 5);
			int comparadorMes = Integer.parseInt(mes);
			String verificadorMes = dataHoje.substring(3, 5);
			int comparaMesAtual = Integer.parseInt(verificadorMes);
			int totalMes = comparaMesAtual - comparadorMes;
			if (totalMes < 0) {
				return false;
			} else {
				String dia = texto.substring(0, 2);
				int comparadorDia = Integer.parseInt(dia);
				String verificadorDia = dataHoje.substring(0, 2);
				int comparaDiaAtual = Integer.parseInt(verificadorDia);
				int totalDia = comparaDiaAtual - comparadorDia;
				if (totalDia < 0) {
					return false;
				}
			}

		}

		return true;
	}

	public boolean idadeValida() {
		String texto = data;
		String conversor = texto.substring(6);
		int comparadorAno = Integer.parseInt(conversor);
		String verificadorAno = dataHoje.substring(6);
		int comparaAnoAtual = Integer.parseInt(verificadorAno);
		int total = comparaAnoAtual - comparadorAno;
		if (total < 18 || total > 100) {
			return false;
		}
		return true;

	}
}
