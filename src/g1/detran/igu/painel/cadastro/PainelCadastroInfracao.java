package g1.detran.igu.painel.cadastro;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import g1.detran.logico.Deposito;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.OpcaoBotoes;
import g1.detran.logico.VerificaData;
import java.awt.Color;
import java.awt.event.ActionListener;
import g1.detran.logico.Infracao;
import g1.detran.logico.excecoes.ExcecaoDepositoInfracaoDataInvalida;
import g1.detran.logico.excecoes.ExcecaoDepositoVeiculoInexistente;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import java.text.ParseException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.MaskFormatter;

public class PainelCadastroInfracao extends JPanel implements ActionListener,
		FocusListener {
	JLabel lprogresso;
	JTextField tfResultado;
	Deposito deposito;
	JLabel lid;
	JLabel lplacaVeiculo;
	JLabel lanoVeiculo;
	JLabel ldata;
	JTextField tfid;
	JTextField tfplacaVeiculo;
	JTextField tfanoVeiculo;
	MaskFormatter formataData;
	JFormattedTextField tfdataInfracao;
	JButton btOk;
	JButton btLimpa;
	JanelaPrincipal janelaPrincipal;
	int id;

	public PainelCadastroInfracao(JanelaPrincipal janelaPrincipal,
			Deposito deposito) {
		definaPosicoes();
		this.janelaPrincipal = janelaPrincipal;
		posicionePosicoes();
		this.deposito = deposito;
		try {
			id = this.deposito.getQntdInfracoesCadastrados() + 1;
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}

		String idString = "" + id;
		tfid.setText(idString);
	}

	private void definaPosicoes() {
		tfResultado = new JTextField(30);
		tfResultado.setBackground(Color.black);
		tfResultado.setForeground(Color.red);
		tfResultado.setEditable(false);
		lprogresso = new JLabel("Resultado:");
		lid = new JLabel("ID:");
		lplacaVeiculo = new JLabel("Placa do Veículo:");
		lanoVeiculo = new JLabel("Ano do Veículo:");
		ldata = new JLabel("Data:");
		tfid = new JTextField(5);
		tfid.setBackground(Color.gray);
		tfid.setEditable(false);
		tfid.setMaximumSize(tfid.getPreferredSize());
		tfplacaVeiculo = new JTextField(30);
		tfplacaVeiculo.setMaximumSize(tfplacaVeiculo.getPreferredSize());
		tfplacaVeiculo.addFocusListener(this);
		tfanoVeiculo = new JTextField(5);
		tfanoVeiculo.setMaximumSize(tfanoVeiculo.getPreferredSize());
		tfanoVeiculo.setBackground(Color.gray);
		tfanoVeiculo.setEditable(false);
		btOk = new JButton("Okay");
		btOk.setActionCommand(OpcaoBotoes.OKAY.name());
		btOk.addActionListener(this);
		btLimpa = new JButton("Limpar");
		btLimpa.setActionCommand(OpcaoBotoes.LIMPAR.name());
		btLimpa.addActionListener(this);
		try {
			formataData = new MaskFormatter("##/##/####");
		} catch (ParseException e) {

		}
		formataData.setPlaceholderCharacter('_');
		tfdataInfracao = new JFormattedTextField(formataData);
		tfdataInfracao.addFocusListener(this);

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

			pgLabels.addComponent(lid).addComponent(lplacaVeiculo)
					.addComponent(lanoVeiculo).addComponent(ldata)
					.addComponent(lprogresso);
			pgCamposBotoes.addGroup(pgCampos).addGroup(sgBotoes);
			pgCampos.addComponent(tfid).addComponent(tfplacaVeiculo)
					.addComponent(tfanoVeiculo).addComponent(tfdataInfracao)
					.addComponent(tfResultado);
			sgBotoes.addComponent(btOk).addComponent(btLimpa);

		}

		// Vertical
		{
			SequentialGroup sg = gl.createSequentialGroup();

			ParallelGroup pgID = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgPlaca = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgAnoVeiculo = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgData = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBotoes = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBarraProgresso = gl
					.createParallelGroup(Alignment.TRAILING);

			sg.addGroup(pgID).addGroup(pgPlaca).addGroup(pgAnoVeiculo)
					.addGroup(pgData).addGroup(pgBarraProgresso)
					.addGroup(pgBotoes);
			gl.setVerticalGroup(sg);

			pgBarraProgresso.addComponent(lprogresso).addComponent(tfResultado);

			pgID.addComponent(lid).addComponent(tfid);
			pgPlaca.addComponent(lplacaVeiculo).addComponent(tfplacaVeiculo);
			pgAnoVeiculo.addComponent(lanoVeiculo).addComponent(tfanoVeiculo);
			pgData.addComponent(ldata).addComponent(tfdataInfracao);
			pgBotoes.addComponent(btOk).addComponent(btLimpa);
		}

	}

	// Este método vai ser executado quando um dos bot�es forem clicados.
	@Override
	public void actionPerformed(ActionEvent e) {
		OpcaoBotoes opcao = OpcaoBotoes.valueOf(e.getActionCommand());
		boolean erro = true;

		switch (opcao) {
		case LIMPAR:
			limpaCampos();
			break;
		case OKAY:
			if (validacaoData() && verificaPlaca()) {
				int ano = (Integer.parseInt(tfanoVeiculo.getText()));
				String placa = tfplacaVeiculo.getText();
				placa.toUpperCase();
				tfplacaVeiculo.setText(placa);
				Infracao infracao = new Infracao(id, placa,
						tfdataInfracao.getText());
				try {
					deposito.armazenaInfracao(infracao);
					tfResultado.setText("Infração cadastrada com sucesso");
					erro = false;
					id++;
					tfid.setText("" + id);
					janelaPrincipal.atualizaPainel();

				} catch (ExcecaoDepositoInfracaoDataInvalida e1) {
					JOptionPane.showMessageDialog(null,
							"Data da infração anterior a data do veículo");
				} catch (ExcecaoDepositoVeiculoInexistente e1) {
					JOptionPane.showMessageDialog(null, "Veículo inexistente");
				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");

				}
			}

			break;
		}

		if (erro) {
			tfResultado.setForeground(Color.red);
		} else {
			tfResultado.setForeground(Color.green);
		}
	}

	private void limpaCampos() {
		tfanoVeiculo.setText("");
		tfplacaVeiculo.setText("");
		tfdataInfracao.setText("");
		tfResultado.setText("");
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		tfResultado.setForeground(Color.red);
		if (e.getSource() == tfdataInfracao) {
			validacaoData();
			if (!verificaAnoFabricacaoPassada()) {
				tfResultado
						.setText("Ano de Fabricação é superior à data informada.");
			}
		}
		if (e.getSource() == tfplacaVeiculo) {
			if (verificaPlaca()) {
				String placa = tfplacaVeiculo.getText();
				placa = placa.toUpperCase();
				tfplacaVeiculo.setText(placa);
				try {
					int ano = deposito
							.getAnoPelaPlaca(tfplacaVeiculo.getText());
					if (ano == 0) {
						tfResultado
								.setText("Não existe veículo com esta placa");
					} else {
						String anoS = "" + ano;
						tfanoVeiculo.setText(anoS);
					}
				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");

				}

			}
		}
	}

	private boolean validacaoData() {
		VerificaData verificaData = new VerificaData(tfdataInfracao.getText());
		if (verificaData.verifica()) {
			if (verificaData.comparaDataFutura()
					&& verificaAnoFabricacaoPassada()) {
				tfResultado.setText("");
				return true;

			} else {
				tfResultado.setText("Data inválida.");
				return false;
			}
		} else {
			tfResultado.setText("Digite uma data válida no formato dd/mm/aaaa");
			return false;
		}
	}

	public boolean verificaPlaca() {
		if (tfplacaVeiculo.getText().length() == 7
				&& tfplacaVeiculo.getText().substring(0, 3)
						.matches("[A-Za-z]+")
				&& tfplacaVeiculo.getText().substring(3).matches("[0-9]+")) {
			tfResultado.setText("");
			return true;
		} else {
			tfResultado.setText("Placa deve conter 3 letras e 4 números");
			return false;
		}

	}

	private boolean verificaAnoFabricacaoPassada() {
		if (tfdataInfracao.getText().indexOf("_") == -1) {
			int anoFabricacao = Integer.parseInt(tfanoVeiculo.getText());
			int anoInfracao = Integer.parseInt(tfdataInfracao.getText()
					.substring(6));
			if (anoFabricacao > anoInfracao) {
				return false;
			}
		}
		return true;
	}
}
