package g1.detran.igu.dialogo.pesquisa;

import g1.detran.igu.Dialogos;
import g1.detran.igu.painel.pesquisa.PainelPesquisaInfracoesPorPeriodo;
import g1.detran.logico.Deposito;

public class DialogoPesquisaInfracaoPorPeriodo extends Dialogos{
	
	public DialogoPesquisaInfracaoPorPeriodo(Deposito deposito){
		super("Pesquisar Infrações Por Período", new PainelPesquisaInfracoesPorPeriodo(deposito));
	}

}
