package g1.detran.igu.painel;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import g1.detran.logico.Deposito;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

public class PainelJanelaPrincipal extends JPanel {
	JLabel lCondutor;
	JLabel lVeiculo;
	JLabel lInfracao;
	JTextField tfCondutor;
	JTextField tfVeiculo;
	JTextField tfInfracao;

	public PainelJanelaPrincipal() {

		setBorder(BorderFactory.createTitledBorder("Dados Cadastrados"));
		setBackground(new Color(255, 255, 255));
		setVisible(false);
		setSize(50, 50);
		definaPosicoes();
		posicionePosicoes();
	}

	public void definaPosicoes() {
		lVeiculo = new JLabel("Número de Veículos Cadastrados:");
		lInfracao = new JLabel("Número de Infrações Cadastrados:");
		lCondutor = new JLabel("Número de Condutores Cadastrados:");
		tfCondutor = new JTextField(5);
		tfCondutor.setMaximumSize(tfCondutor.getPreferredSize());
		tfCondutor.setText("0");
		tfVeiculo = new JTextField(5);
		tfVeiculo.setMaximumSize(tfVeiculo.getPreferredSize());
		tfVeiculo.setText("0");
		tfInfracao = new JTextField(5);
		tfInfracao.setMaximumSize(tfInfracao.getPreferredSize());
		tfInfracao.setText("0");
		tfCondutor.setEditable(false);
		tfVeiculo.setEditable(false);
		tfInfracao.setEditable(false);
	}

	// Define e posiciona os grupos do painel.
	private void posicionePosicoes() {
		GroupLayout gl = new GroupLayout(this);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		setLayout(gl);

		// Horizontal
		{
			SequentialGroup sg = gl.createSequentialGroup();

			ParallelGroup pgLabels = gl.createParallelGroup(Alignment.LEADING);
			ParallelGroup pgCampos = gl.createParallelGroup(Alignment.TRAILING);
			sg.addGroup(pgLabels).addGroup(pgCampos);
			gl.setHorizontalGroup(sg);

			pgLabels.addComponent(lCondutor).addComponent(lVeiculo)
					.addComponent(lInfracao);

			pgCampos.addComponent(tfCondutor).addComponent(tfVeiculo)
					.addComponent(tfInfracao);
		}

		// Vertical
		{
			SequentialGroup sg = gl.createSequentialGroup();

			ParallelGroup pgCondutor = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgVeiculo = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgInfracao = gl
					.createParallelGroup(Alignment.TRAILING);

			sg.addGroup(pgCondutor).addGroup(pgVeiculo).addGroup(pgInfracao);

			gl.setVerticalGroup(sg);

			pgCondutor.addComponent(lCondutor).addComponent(tfCondutor);
			pgVeiculo.addComponent(lVeiculo).addComponent(tfVeiculo);
			pgInfracao.addComponent(lInfracao).addComponent(tfInfracao);
		}
	}

	public void visivel() {
		setVisible(true);
	}

	public void atualizaPanel(Deposito deposito) {
		try {
			int condutor = deposito.getQntdCondutoresCadastrados();

			String condutorTexto = "" + condutor;
			tfCondutor.setText(condutorTexto);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}

		try {
			int veiculo = deposito.getQntdVeiculosCadastrados();
			String veiculoTexto = "" + veiculo;
			tfVeiculo.setText(veiculoTexto);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}

		try {
			int infracao = deposito.getQntdInfracoesCadastrados();
			String infracaoTexto = "" + infracao;
			tfInfracao.setText(infracaoTexto);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}
	}
}
