package g1.detran.igu.dialogo.cadastro;

import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.PainelJanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroVeiculoAutomatico;
import g1.detran.logico.Deposito;

public class DialogoCadastroVeiculoAutomatico  extends Dialogos {

	private PainelJanelaPrincipal painelJanelaPrincipal;
	
	
	public DialogoCadastroVeiculoAutomatico(Deposito deposito, JanelaPrincipal janelaPrincipal){
		super("Cadastro Veículo Automatico", new PainelCadastroVeiculoAutomatico(janelaPrincipal, deposito));
	}
}
