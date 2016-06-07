package g1.detran.igu.dialogo.cadastro;

import g1.detran.logico.Deposito;
import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.PainelJanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroCondutor;

public class DialogoCadastroCondutor extends Dialogos {

	private PainelJanelaPrincipal painelJanelaPrincipal;

	public DialogoCadastroCondutor(Deposito deposito,JanelaPrincipal janelaPrincipal) {
		super("Cadastro Condutor", new PainelCadastroCondutor(janelaPrincipal, deposito));
 
	}

}
