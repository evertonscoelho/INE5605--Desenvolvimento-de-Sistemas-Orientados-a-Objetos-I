package g1.detran.igu.dialogo.cadastro;

import g1.detran.logico.Deposito;
import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroInfracao;

public class DialogoCadastroInfracao extends Dialogos  {

	public DialogoCadastroInfracao(Deposito deposito, JanelaPrincipal janelaPrincipal){
		super("Cadastro Infração",new PainelCadastroInfracao(janelaPrincipal, deposito));
		}
	

}
