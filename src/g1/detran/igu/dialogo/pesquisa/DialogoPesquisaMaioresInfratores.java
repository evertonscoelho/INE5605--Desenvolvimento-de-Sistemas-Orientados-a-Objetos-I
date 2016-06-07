package g1.detran.igu.dialogo.pesquisa;

import g1.detran.igu.Dialogos;
import g1.detran.igu.painel.pesquisa.PainelPesquisaMaioresInfratores;
import g1.detran.logico.Deposito;

public class DialogoPesquisaMaioresInfratores extends Dialogos {
	
	public DialogoPesquisaMaioresInfratores(Deposito deposito){
		super("Pesquisar os N maiores infratores", new PainelPesquisaMaioresInfratores(deposito));
	}

}
