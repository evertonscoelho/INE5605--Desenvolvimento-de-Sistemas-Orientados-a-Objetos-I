package g1.detran.igu.painel.pesquisa;

import g1.detran.igu.painel.tabela.TabelaMostrarDadosCondutor;
import g1.detran.igu.painel.tabela.TabelaMostrarDadosInfracao;
import g1.detran.igu.painel.tabela.TabelaMostrarDadosVeiculos;
import g1.detran.logico.Deposito;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JTabbedPane;

public class PainelMostraDados extends JPanel {

	Deposito deposito;
	JTabbedPane mostrar;

	public PainelMostraDados(Deposito deposito) {
		this.deposito = deposito;
		definaPosicoes();
		posicionePosicoes();		
	}

	private void posicionePosicoes() {
		GroupLayout gl = new GroupLayout(this);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		setLayout(gl);

		// Horizontal
		{
			ParallelGroup pg = gl.createParallelGroup(Alignment.LEADING);
			pg.addComponent(mostrar);
			gl.setHorizontalGroup(pg);
		}
		// Vertical
		{

			SequentialGroup sg = gl.createSequentialGroup();
			sg.addComponent(mostrar);
			gl.setVerticalGroup(sg);
		}

	}

	private void definaPosicoes() {

		JPanel condutor = new TabelaMostrarDadosCondutor(deposito);
		JPanel veiculo =  new TabelaMostrarDadosVeiculos(deposito);
		JPanel infracao = new TabelaMostrarDadosInfracao(deposito);
		mostrar = new JTabbedPane();
		mostrar.addTab("Condutor", condutor);
		mostrar.addTab("Veículo", veiculo);
		mostrar.addTab("Infração", infracao);
	}

}
