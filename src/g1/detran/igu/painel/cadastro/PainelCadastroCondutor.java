package g1.detran.igu.painel.cadastro;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import g1.detran.logico.Deposito;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.OpcaoBotoes;
import g1.detran.logico.VerificaData;
import java.awt.event.ActionListener;
import java.text.ParseException;
import g1.detran.logico.Condutor;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorExistente;
import g1.detran.logico.excecoes.ExcecaoDepositoCondutorIdadeValida;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PainelCadastroCondutor extends JPanel implements ActionListener,
		FocusListener {
	Deposito deposito;
	JLabel lCPF;
	JLabel lnome;
	JLabel lresultado;
	JLabel ldataNascimento;
	JTextField tfCPF;
	JTextField tfnome;
	MaskFormatter formataData;
	JFormattedTextField tfdataNascimento;
	JButton btOk;
	JButton btLimpa;
	JTextField tfResultado;
	JanelaPrincipal janelaPrincipal;

	public PainelCadastroCondutor(JanelaPrincipal janelaPrincipal,
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
		lCPF = new JLabel("CPF:");
		lresultado = new JLabel("Resultado:");
		lnome = new JLabel("Nome:");
		ldataNascimento = new JLabel("Data de Nascimento:");
		tfCPF = new JTextField(11);
		tfCPF.setMaximumSize(tfCPF.getPreferredSize());
		tfCPF.setActionCommand(OpcaoBotoes.CPF.name());
		tfCPF.addFocusListener(this);
		tfnome = new JTextField(30);
		tfnome.setMaximumSize(tfnome.getPreferredSize());
		tfnome.addFocusListener(this);
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
		tfdataNascimento = new JFormattedTextField(formataData);
		tfdataNascimento.addFocusListener(this);

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

			pgLabels.addComponent(lCPF).addComponent(lnome)
					.addComponent(ldataNascimento).addComponent(lresultado);
			pgCamposBotoes.addGroup(pgCampos).addGroup(sgBotoes);
			pgCampos.addComponent(tfCPF).addComponent(tfnome)
					.addComponent(tfdataNascimento).addComponent(tfResultado);
			sgBotoes.addComponent(btOk).addComponent(btLimpa);

		}

		// Vertical
		{
			SequentialGroup sg = gl.createSequentialGroup();

			ParallelGroup pgNome = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgCPF = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgNascimento = gl
					.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBarra = gl.createParallelGroup(Alignment.TRAILING);
			ParallelGroup pgBotoes = gl.createParallelGroup(Alignment.TRAILING);

			sg.addGroup(pgCPF).addGroup(pgNome).addGroup(pgNascimento)
					.addGroup(pgBarra).addGroup(pgBotoes);
			gl.setVerticalGroup(sg);

			pgCPF.addComponent(lCPF).addComponent(tfCPF);
			pgNome.addComponent(lnome).addComponent(tfnome);
			pgNascimento.addComponent(ldataNascimento).addComponent(
					tfdataNascimento);
			pgBarra.addComponent(lresultado).addComponent(tfResultado);
			pgBotoes.addComponent(btOk).addComponent(btLimpa);
		}

	}

	// Este método vai ser executado quando um dos bot�es forem clicados.
	@Override
	public void actionPerformed(ActionEvent e) {
		OpcaoBotoes opcao = OpcaoBotoes.valueOf(e.getActionCommand());
		boolean erro = true;

		switch (opcao) {
		case OKAY:
			if (validacaoCPF() && validacaoNome() && validacaoData()) {
				long CPF = (Long.parseLong(tfCPF.getText()));
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				formatter.setLenient(false);
				Date dataNascimento = null;
				try {
					dataNascimento = formatter
							.parse(tfdataNascimento.getText());
				} catch (ParseException e1) {
					tfResultado
							.setText("Preencha a data de nascimento corretamente");
				}
				Condutor condutor = new Condutor(CPF, tfnome.getText(),
						dataNascimento);
				try {
					deposito.armazenaCondutor(condutor);
					tfResultado.setText("Condutor cadastrado com sucesso");
					erro = false;
					janelaPrincipal.atualizaPainel();
				} catch (ExcecaoDepositoCondutorExistente er) {
					JOptionPane
							.showMessageDialog(null, "Condutor já existente");

				} catch (ExcecaoDepositoCondutorIdadeValida e1) {
					tfResultado.setText("Preencha os campos corretamente");
				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");

				}
			} else {
				tfResultado.setText("Preencha os campos corretamente");
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
		tfCPF.setText("");
		tfnome.setText("");
		tfdataNascimento.setText("");
		tfResultado.setText("");
	}

	private boolean validacaoNome() {
		if (tfnome.getText().length() > 50 || tfnome.getText().length() == 0) {
			tfResultado.setText("Nome deve ter entre 1 e 50 caracteres");
			return false;
		}
		tfResultado.setText("");
		return true;
	}

	public boolean validacaoCPF() {
		if (tfCPF.getText().length() > 11 || tfCPF.getText().length() == 0) {
			tfResultado.setText("CPF deve ter entre 1 e 11 números");
			return false;
		}

		if (!tfCPF.getText().matches("[0-9]+")) {
			tfResultado.setText("CPF deve conter apenas números");
			return false;
		}
		tfResultado.setText("");
		return true;
	}

	public void focusLost(FocusEvent evt) {
		tfResultado.setForeground(Color.red);
		if (evt.getSource() == tfCPF) {
			validacaoCPF();
		}
		if (evt.getSource() == tfnome) {
			validacaoNome();
		}

		if (evt.getSource() == tfdataNascimento) {
			validacaoData();
		}

	}

	private boolean validacaoData() {
		VerificaData verificaData = new VerificaData(tfdataNascimento.getText());
		if (verificaData.verifica()) {
			if (verificaData.idadeValida()) {
				tfResultado.setText("");
				return true;
			} else {
				tfResultado
						.setText("A pessoa precisa deve ter entre 18 e 100 anos");
				return false;
			}
		} else {
			tfResultado.setText("Digite uma data válida no formato dd/mm/aaaa");
			return false;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
