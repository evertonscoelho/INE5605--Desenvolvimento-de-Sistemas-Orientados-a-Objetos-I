package g1.detran.igu.dialogo.cadastro;

import g1.detran.logico.Deposito;
import g1.detran.igu.Dialogos;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.painel.cadastro.PainelCadastroVeiculo;

public class DialogoCadastroVeiculo extends Dialogos {
	

	public DialogoCadastroVeiculo(Deposito deposito, JanelaPrincipal janelaPrincipal) {
		super("Cadastro Veículo", new PainelCadastroVeiculo(janelaPrincipal, deposito));
	}

}
