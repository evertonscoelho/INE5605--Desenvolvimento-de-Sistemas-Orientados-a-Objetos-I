package g1.detran.igu.painel.cadastro;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import g1.detran.logico.Deposito;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.OpcaoBotoes;
import java.awt.event.ActionListener;
import g1.detran.logico.Veiculo;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorInexistente;
import g1.detran.logico.excecoes.ExcecaoDepositoPlacasIguais;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class PainelCadastroVeiculo extends JPanel implements ActionListener,
		FocusListener {
	JLabel lprogresso;
	JTextField tfResultado;
	Deposito deposito;
	JLabel lplaca;
	JLabel lanoFabricacao;
	JLabel lCPFCondutor;
	JTextField tfplaca;
	JTextField tfanoFabricacao;
	JTextField tfCPFCondutor;
	JButton btOk;
	JButton btLimpa;
	JanelaPrincipal janelaPrincipal;

	public PainelCadastroVeiculo(JanelaPrincipal janelaPrincipal,
			Deposito deposito) {
		definaPosicoes();
		this.janelaPrincipal = janelaPrincipal;
		posicionePosicoes();
		this.deposito = deposito;
	}

	private void definaPosicoes() {
		tfResultado = new JTextField(30);
		tfResultado.setBackground(Color.black);
		tfResultado.setForeground(Color.red);
		tfResultado.setEditable(false);
		lprogresso = new JLabel("Resultado:");
		lplaca = new JLabel("Placa:");
		lanoFabricacao = new JLabel("Ano:");
		lCPFCondutor = new JLabel("CPF Proprietário:");
		tfCPFCondutor = new JTextField(11);
		tfCPFCondutor.setMaximumSize(tfCPFCondutor.getPreferredSize());
		tfCPFCondutor.addFocusListener(this);
		tfplaca = new JTextField(7);
		tfplaca.setMaximumSize(tfplaca.getPreferredSize());
		tfplaca.addFocusListener(this);
		tfanoFabricacao = new JTextField(4);
		tfanoFabricacao.setMaximumSize(tfanoFabricacao.getPreferredSize());
		tfanoFabricacao.addFocusListener(this);
		btOk = new JButton("Okay");
		btOk.setActionCommand(OpcaoBotoes.OKAY.name());
		btOk.addActionListener(this);
		btLimpa = new JButton("Limpar");
		btLimpa.setActionCommand(OpcaoBotoes.LIMPAR.name());
		btLimpa.addActionListener(this);

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
			ParallelGroup pgCamposBotoes = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgCampos = gl.createParallelGroup(Alignment.LEADING);
			SequentialGroup sgBotoes = gl.createSequentialGroup();

			sg.addGroup(pgLabels).addGroup(pgCamposBotoes);
			gl.setHorizontalGroup(sg);

			pgLabels.addComponent(lplaca).addComponent(lanoFabricacao)
					.addComponent(lCPFCondutor).addComponent(lprogresso);
			pgCamposBotoes.addGroup(pgCampos).addGroup(sgBotoes);
			pgCampos.addComponent(tfplaca).addComponent(tfanoFabricacao)
					.addComponent(tfCPFCondutor).addComponent(tfResultado);
			sgBotoes.addComponent(btOk).addComponent(btLimpa);

		}

		// Vertical
		{
			SequentialGroup sg = gl.createSequentialGroup();

			ParallelGroup pgPlaca = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgFabricacao = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgCPFCondutor = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBotoes = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBarraProgresso = gl
					.createParallelGroup(Alignment.TRAILING);

			sg.addGroup(pgPlaca).addGroup(pgFabricacao).addGroup(pgCPFCondutor)
					.addGroup(pgBarraProgresso).addGroup(pgBotoes);
			gl.setVerticalGroup(sg);

			pgBarraProgresso.addComponent(lprogresso).addComponent(tfResultado);

			pgPlaca.addComponent(lplaca).addComponent(tfplaca);
			pgFabricacao.addComponent(lanoFabricacao).addComponent(
					tfanoFabricacao);
			pgCPFCondutor.addComponent(lCPFCondutor)
					.addComponent(tfCPFCondutor);
			pgBotoes.addComponent(btOk).addComponent(btLimpa);
		}

	}

	// Este m�todo vai ser executado quando um dos bot�es forem clicados.
	@Override
	public void actionPerformed(ActionEvent e) {
		OpcaoBotoes opcao = OpcaoBotoes.valueOf(e.getActionCommand());
		boolean erro = true;

		switch (opcao) {
		case LIMPAR:
			limpaCampos();
		case OKAY:
			if (verificaAno() && VerificaCPF() && verificaPlaca()) {
				String placa = tfplaca.getText();
				placa.toUpperCase();
				long CPF = (Long.parseLong(tfCPFCondutor.getText()));
				int ano = (Integer.parseInt(tfanoFabricacao.getText()));
				Veiculo veiculo = new Veiculo(placa, ano, CPF);
				try {
					deposito.armazenaVeiculo(veiculo);
					erro = false;
					tfResultado.setText("Veículo cadastrado com sucesso");
					janelaPrincipal.atualizaPainel();
				} catch (ExcecaoDepositoPlacasIguais er) {
					tfResultado.setText("Atenção placa já cadastrada");
				} catch (ExcecaoDepositoCondutorInexistente e1) {
					tfResultado.setText("Atenção condutor não está cadastrado");
				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");

				}
			} else {
				tfResultado.setText("Preencha os campos corretamente");
			}
		}

		if (erro) {
			tfResultado.setForeground(Color.red);
		} else {
			tfResultado.setForeground(Color.green);
		}

	}

	private void limpaCampos() {
		tfCPFCondutor.setText("");
		tfplaca.setText("");
		tfanoFabricacao.setText("");
		tfResultado.setText("");
	}

	public boolean VerificaCPF() {
		if (tfCPFCondutor.getText().length() > 11
				|| tfCPFCondutor.getText().length() == 0) {
			tfResultado.setText("CPF deve ter entre 1 e 11 números");
			return false;
		}
		if (!tfCPFCondutor.getText().matches("[0-9]+")) {
			tfResultado.setText("CPF deve conter apenas n�meros");
			return false;
		}
		tfResultado.setText("");
		return true;
	}

	public boolean verificaPlaca() {
		if (tfplaca.getText().length() == 7
				&& tfplaca.getText().substring(0, 3).matches("[A-Za-z]+")
				&& tfplaca.getText().substring(3).matches("[0-9]+")) {
			tfResultado.setText("");
			return true;
		} else {
			tfResultado.setText("Placa deve conter 3 letras e 4 números");
			return false;
		}

	}

	public boolean verificaAno() {
		if (tfanoFabricacao.getText().length() == 4
				&& tfanoFabricacao.getText().substring(0).matches("[0-9]+")) {
			tfResultado.setText("");
			return true;
		} else {
			tfResultado.setText("Ano inválido");
			return false;
		}

	}

	public void focusLost(FocusEvent e) {
		tfResultado.setForeground(Color.red);

		if (e.getSource() == tfanoFabricacao) {
			verificaAno();
		}
		if (e.getSource() == tfCPFCondutor) {
			VerificaCPF();
		}
		if (e.getSource() == tfplaca) {
			verificaPlaca();
			String placa = tfplaca.getText();
			placa = placa.toUpperCase();
			tfplaca.setText(placa);
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub

	}

}
