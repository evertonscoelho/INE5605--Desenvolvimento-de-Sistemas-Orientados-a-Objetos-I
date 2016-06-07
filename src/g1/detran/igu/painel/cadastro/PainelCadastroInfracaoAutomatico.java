package g1.detran.igu.painel.cadastro;

import g1.detran.igu.JanelaPrincipal;
import g1.detran.logico.Deposito;
import g1.detran.logico.GeradorDeInfracao;
import g1.detran.logico.Infracao;
import g1.detran.logico.excecoes.ExcecaoDepositoInfracaoDataInvalida;
import g1.detran.logico.excecoes.ExcecaoDepositoVeiculoInexistente;
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

public class PainelCadastroInfracaoAutomatico extends JPanel implements
		ActionListener {
	Deposito deposito;
	JLabel lQntDados;
	JLabel lresultado;
	JButton btCadastrar;
	JTextField tfQntDados;
	JTextField tfResultado;
	JanelaPrincipal janelaPrincipal;
	JProgressBar barraProgresso = new JProgressBar();

	public PainelCadastroInfracaoAutomatico(JanelaPrincipal janelaPrincipal,
			Deposito deposito) {
		definaPosicoes();
		this.janelaPrincipal = janelaPrincipal;
		posicionePosicoes();
		this.deposito = deposito;
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
			Infracao infracao;

			while (i <= quantidade) {
				try {
					GeradorDeInfracao gerador = new GeradorDeInfracao(deposito);
					infracao = gerador.getInfracaoGerada();
					deposito.armazenaInfracao(infracao);
					barraProgresso.setValue(i);
					i++;
				} catch (ExcecaoDepositoInfracaoDataInvalida e1) {

				} catch (ExcecaoDepositoVeiculoInexistente e2) {

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
