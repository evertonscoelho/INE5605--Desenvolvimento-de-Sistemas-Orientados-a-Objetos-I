package g1.detran.igu.dialogo.pesquisa;

import g1.detran.igu.Dialogos;
import g1.detran.igu.painel.pesquisa.PainelPesquisaInfracaoPorCondutor;
import g1.detran.logico.Deposito;

public class DialogoPesquisaInfracaoPorCondutor extends Dialogos {

	public DialogoPesquisaInfracaoPorCondutor(Deposito deposito){
		super("Pesquisar Infrações de Condutor", new PainelPesquisaInfracaoPorCondutor(deposito));		
	}

}
