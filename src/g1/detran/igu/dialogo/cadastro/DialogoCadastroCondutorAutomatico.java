package g1.detran.igu.dialogo.cadastro;

import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroCondutorAutomatico;
import g1.detran.logico.Deposito;

public class DialogoCadastroCondutorAutomatico extends Dialogos {

	public DialogoCadastroCondutorAutomatico(Deposito deposito,
			JanelaPrincipal janelaPrincipal) {
		super("Cadastro Condutor Automatico", new PainelCadastroCondutorAutomatico(deposito, janelaPrincipal));
	}

	

}
