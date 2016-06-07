package g1.detran.igu.dialogo.cadastro;

import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroInfracaoAutomatico;
import g1.detran.logico.Deposito;

public class DialogoCadastroInfracaoAutomatico extends Dialogos {

	public DialogoCadastroInfracaoAutomatico(Deposito deposito,
			JanelaPrincipal janelaPrincipal) {
		super("Cadastro Infração Automatico", new PainelCadastroInfracaoAutomatico(janelaPrincipal, deposito));
	}

}
