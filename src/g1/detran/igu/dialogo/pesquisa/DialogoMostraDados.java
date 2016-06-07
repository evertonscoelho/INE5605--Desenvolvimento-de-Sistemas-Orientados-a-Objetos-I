package g1.detran.igu.dialogo.pesquisa;

import g1.detran.igu.Dialogos;
import g1.detran.igu.painel.pesquisa.PainelMostraDados;
import g1.detran.logico.Deposito;

public class DialogoMostraDados extends Dialogos{

	public DialogoMostraDados(Deposito deposito) {
		super("Mostrar dados", new PainelMostraDados(deposito));		
	}

}
