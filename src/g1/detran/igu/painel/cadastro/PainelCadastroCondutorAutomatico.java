package g1.detran.igu.painel.cadastro;

import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroCondutorAutomatico;
import g1.detran.logico.Condutor;
import g1.detran.logico.Deposito;
import g1.detran.logico.GeradorDeCondutores;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorExistente;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorIdadeValida;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class PainelCadastroCondutorAutomatico extends JPanel implements
		ActionListener {

	Deposito deposito;
	JLabel lQntDados;
	JLabel lresultado;
	JTextField tfQntDados;
	JButton btCadastrar;
	JTextField tfResultado;
	DialogoCadastroCondutorAutomatico cadastroCondutorAutomatico;
	JProgressBar barraProgresso = new JProgressBar();
	JanelaPrincipal janelaPrincipal;

	public PainelCadastroCondutorAutomatico(Deposito deposito,
			JanelaPrincipal janelaPrincipal) {
		definaPosicoes();
		posicionePosicoes();
		this.deposito = deposito;
		this.janelaPrincipal = janelaPrincipal;
		barraProgresso.setForeground(Color.green);
	}

	private void definaPosicoes() {
		tfResultado = new JTextField(30);
		tfResultado.setBackground(Color.black);
		tfResultado.setForeground(Color.green);
		tfResultado.setEditable(false);
		lQntDados = new JLabel("Quantidade de dados:");
		lresultado = new JLabel("Resultado:");

		tfQntDados = new JTextField(10);
		tfQntDados.setMaximumSize(tfQntDados.getPreferredSize());

		btCadastrar = new JButton("Cadastrar");
		btCadastrar.addActionListener(this);

	}

	// Define e posiciona os grupos do painel.
	private void posicionePosicoes() {
		GroupLayout gl = new GroupLayout(this);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		setLayout(gl);

		// Horizontal
		{
			SequentialGroup sgDados = gl.createSequentialGroup();
			SequentialGroup sgProgresso = gl.createSequentialGroup();

			ParallelGroup pg = gl.createParallelGroup(Alignment.LEADING);

			sgDados.addComponent(lQntDados).addComponent(tfQntDados)
					.addComponent(btCadastrar).addComponent(barraProgresso);
			sgProgresso.addComponent(lresultado).addComponent(tfResultado);
			pg.addGroup(sgDados).addGroup(sgProgresso);
			gl.setHorizontalGroup(pg);

		}

		// Vertical
		{
			ParallelGroup pgDados = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgResultado = gl
					.createParallelGroup(Alignment.LEADING);

			SequentialGroup sg = gl.createSequentialGroup();

			pgDados.addComponent(lQntDados).addComponent(tfQntDados)
					.addComponent(btCadastrar).addComponent(barraProgresso);
			pgResultado.addComponent(lresultado).addComponent(tfResultado);

			sg.addGroup(pgDados).addGroup(pgResultado);

			gl.setVerticalGroup(sg);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			int quantidade = Integer.parseInt(tfQntDados.getText());
			int i = 1;
			barraProgresso.setMinimum(0);
			barraProgresso.setMaximum(quantidade);
			barraProgresso.setValue(0);
			Condutor condutor;

			while (i <= quantidade) {
				try {
					GeradorDeCondutores gerador = new GeradorDeCondutores();
					condutor = gerador.getCondutorGerado();
					deposito.armazenaCondutor(condutor);
					barraProgresso.setValue(i);
					i++;
				} catch (ExcecaoDepositoCondutorExistente e1) {

				} catch (ExcecaoDepositoCondutorIdadeValida e2) {

				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");

				}
			}

			tfResultado.setForeground(Color.green);
			tfResultado.setText("Cadastrado com sucesso");
			janelaPrincipal.atualizaPainel();

		} catch (NumberFormatException e1) {
			tfResultado.setForeground(Color.red);
			tfResultado.setText("Digite um valor inteiro");

		}
	}
}
