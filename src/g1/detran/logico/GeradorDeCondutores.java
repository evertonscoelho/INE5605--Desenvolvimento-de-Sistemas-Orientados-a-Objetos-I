package g1.detran.logico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GeradorDeCondutores {

	String[] nome = { "Everton", "Yuri", "Gustavo", "Jô", "Kelly'n", "Alise",
			"Jorge", "H. Romeu", "Kika", "Seu", "Thiago", "Paul", "José",
			"Bruna", "Bruno", "César", "Marcelo", "Laura", "Alan", "Lucas",
			"Carlos", "Giselle", "Amanda", "Thais", "Pedro", "João", "Maria",
			"Jesus", "Junior", "Fulano", "Cumpade", "Power", "Kakarotto",
			"Capirotto", "Mijaro", "Goku", "Kagaro", "Jalim", "Jascinto",
			"Cuca", "Benjamim", "Picollo", "Adão", "Eva", "Artur", "Carol",
			"Nicolle", "Marina", "Augusto", "Fernanda", "Carla", "Paulo",
			"Luan", "Saimon", "Antônio", "Matheus", "Diego", "Daniel",
			"Daniela", "Roberta", "Rebeca", "Paula", "Julia", "Larissa",
			"Leandro", "Isadora", "Neymar", "Romarino", "Ronaldo" };
	String[] sobrenome = { "Coelho", "Kayser", "Deon", "Moura", "de Souza",
			"Guiça", "Lima", "Guido", "Baço", "Jorge", "Duro", "Forah",
			"Soares", "Camilo", "Nascimento", "Gado", "Martins", "Prado",
			"Dutra", "Rosa", "Matos", "Xavier", "Cruz", "Aguiar", "Pinto",
			"Paz", "Cardoso", "Junior", "de Tal", "Uóchintom", "Rabei",
			"Ranger", "Happica", "Beludo", "Silva", "Nomuro", "Nakombi" };
	Random gerador = new Random();
	Condutor condutor;

	
	public GeradorDeCondutores() {
		String nomeCondutor = geraNome();
		Long cpf = geraCPF();
		Date data = geraData();
		condutor = new Condutor(cpf, nomeCondutor, data);
	}
	
	public Condutor getCondutorGerado(){
		return condutor;
	}	
	

	private Date geraData() {

		Date retorno = null;
		int ano = parametro(1933, 2013);
		int mes = parametro(1, 12);
		int dia = parametro(1, 31);
		String data = dia + "/" + mes + "/" + ano;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			retorno = (java.util.Date) formatter.parse(data);
		} catch (ParseException e) {

		}

		return retorno;

	}

	private int parametro(int min, int max) {

		return min + (int) Math.round(Math.random() * (max - min));

	}

	private Long geraCPF() {

		return 10000000000l + (long) Math.round(Math.random()
				* (99999999999l - 10000000000l));

	}

	private String geraNome() {

		return nome[gerador.nextInt(nome.length)] + " "
				+ sobrenome[gerador.nextInt(sobrenome.length)];

	}

}
