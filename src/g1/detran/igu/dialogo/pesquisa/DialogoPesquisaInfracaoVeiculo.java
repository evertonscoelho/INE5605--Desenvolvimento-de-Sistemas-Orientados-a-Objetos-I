package g1.detran.igu.dialogo.pesquisa;

import g1.detran.igu.Dialogos;
import g1.detran.igu.painel.pesquisa.PainelPesquisaInfracaoVeiculo;
import g1.detran.logico.Deposito;

public class DialogoPesquisaInfracaoVeiculo extends Dialogos {
	
	public DialogoPesquisaInfracaoVeiculo(Deposito deposito){
		super("Pesquisar infrações de Veículo", new PainelPesquisaInfracaoVeiculo(deposito));
	}


}
