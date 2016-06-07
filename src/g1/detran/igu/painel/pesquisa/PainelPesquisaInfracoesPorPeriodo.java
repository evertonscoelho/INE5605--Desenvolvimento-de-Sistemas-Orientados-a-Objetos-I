package g1.detran.igu.painel.pesquisa;

import g1.detran.igu.OpcaoBotoes;
import g1.detran.logico.Deposito;
import g1.detran.logico.VerificaData;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import g1.detran.logico.Infracao;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class PainelPesquisaInfracoesPorPeriodo extends JPanel implements
		ActionListener, FocusListener {

	Deposito deposito;
	JTable tabela;
	JLabel lDe;
	JLabel lAte;
	JLabel lResultado;
	JButton btPesquisar;
	JButton btInterromper;
	JFormattedTextField tfDataDe;
	JFormattedTextField tfDataAte;
	JTextField tfResultado;
	DefaultTableModel modelo;
	JScrollPane rolagem;
	MaskFormatter formataData;
	List<Infracao> infracaoPorPeriodo;
	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public PainelPesquisaInfracoesPorPeriodo(Deposito deposito) {

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
			ParallelGroup pg = gl.createParallelGroup(Alignment.CENTER);
			SequentialGroup sgBotoes = gl.createSequentialGroup();
			SequentialGroup sgResultado = gl.createSequentialGroup();

			sgBotoes.addComponent(lDe).addComponent(tfDataDe)
					.addComponent(lAte).addComponent(tfDataAte)
					.addComponent(btPesquisar).addComponent(btInterromper);

			sgResultado.addComponent(lResultado).addComponent(tfResultado);

			pg.addGroup(sgBotoes).addComponent(rolagem).addGroup(sgResultado);
			gl.setHorizontalGroup(pg);
		}
		// Vertical
		{

			SequentialGroup sg = gl.createSequentialGroup();
			ParallelGroup pgBotoes = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgResultado = gl
					.createParallelGroup(Alignment.LEADING);

			pgResultado.addComponent(lResultado).addComponent(tfResultado);

			pgBotoes.addComponent(lDe).addComponent(tfDataDe)
					.addComponent(lAte).addComponent(tfDataAte)
					.addComponent(btPesquisar).addComponent(btInterromper);

			sg.addGroup(pgBotoes).addComponent(rolagem).addGroup(pgResultado);
			gl.setVerticalGroup(sg);
		}

	}

	private void definaPosicoes() {
		lDe = new JLabel("De: ");
		lAte = new JLabel("Até: ");

		try {
			formataData = new MaskFormatter("##/##/####");
		} catch (ParseException e) {

		}
		formataData.setPlaceholderCharacter('_');
		tfDataAte = new JFormattedTextField(formataData);
		tfDataDe = new JFormattedTextField(formataData);
		tfDataAte.addFocusListener(this);
		tfDataDe.addFocusListener(this);

		btInterromper = new JButton("Interromper");
		btInterromper.setActionCommand(OpcaoBotoes.INTERROMPER.name());
		btInterromper.addActionListener(this);
		btInterromper.setEnabled(false);

		btPesquisar = new JButton("Pesquisar");
		btPesquisar.setActionCommand(OpcaoBotoes.PESQUISAR.name());
		btPesquisar.addActionListener(this);

		tfResultado = new JTextField(30);
		tfResultado.setBackground(Color.black);
		tfResultado.setForeground(Color.red);
		tfResultado.setEditable(false);

		lResultado = new JLabel("Resultado:");

		String[] colunas = new String[3];
		String[][] valores = new String[0][3];
		colunas[0] = "Ordem";
		colunas[1] = "Data da Infração";
		colunas[2] = "Placa";

		modelo = new DefaultTableModel(valores, colunas);
		tabela = new JTable(modelo);
		rolagem = new JScrollPane(tabela);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		OpcaoBotoes opcao = OpcaoBotoes.valueOf(e.getActionCommand());
		switch (opcao) {
		case INTERROMPER:
			break;
		case PESQUISAR:
			try {
				String data1 = tfDataDe.getText();
				String data2 = tfDataAte.getText();
				infracaoPorPeriodo = pesquise(data1, data2);
				if (infracaoPorPeriodo == null) {
					break;
				} else {
					monteTabela();
				}
			} catch (NumberFormatException e2) {
				tfResultado.setForeground(Color.red);
				tfResultado.setText("Digite datas válidas");
			}
			break;

		}
	}

	private void monteTabela() {
		modelo.setRowCount(0);
		String[] colunas = new String[3];
		int ordem = 1;
		for (Infracao infracao : infracaoPorPeriodo) {
			colunas[0] = "" + ordem++;
			colunas[1] = infracao.getDataInfracao();
			colunas[2] = infracao.getPlacaVeiculo();
			modelo.addRow(colunas);
		}

	}

	private List<Infracao> pesquise(String data1, String data2) {
		Infracao[] infracoes;
		List<Infracao> infracoesDoPeriodo = new LinkedList<>();
		boolean terminou = false;
		int quantidadeInfracoes = 1;
		String data;
	

		try {
			int limite = deposito.getQntdInfracoesCadastrados();
			formatter.setLenient(false);
			java.sql.Date dataDe = new java.sql.Date(formatter.parse(data1).getTime());
			java.util.Date utilDataDe = new java.util.Date(dataDe.getTime());
			java.sql.Date dataAte = new java.sql.Date(formatter.parse(data2).getTime());
			java.util.Date utilDataAte = new java.util.Date(dataAte.getTime());
			while (!terminou && quantidadeInfracoes <= limite) {
				infracoes = deposito.getInfracoesEmArray(30,
						quantidadeInfracoes);
				quantidadeInfracoes += 30;
				for (int indice = 0; indice < infracoes.length; indice++) {
					if (infracoes[indice] != null) {
						data = infracoes[indice].getDataInfracao();
						formatter.setLenient(false);
						java.sql.Date dataInfracao = new java.sql.Date(formatter.parse(data).getTime());
						java.util.Date utilDataInfracao = new java.util.Date(dataInfracao.getTime());
						if (utilDataInfracao.after(utilDataDe) && utilDataInfracao.before(utilDataAte) || utilDataInfracao.equals(utilDataDe) || utilDataInfracao.equals(utilDataAte)) {
							infracoesDoPeriodo.add(infracoes[indice]);
						}
					}
				}
				if (quantidadeInfracoes > deposito
						.getQntdInfracoesCadastrados()) {
					terminou = true;
					tfResultado.setForeground(Color.green);
					tfResultado.setText("Pesquisa concluída!");
					
				}
			}
		} catch (ParseException e) {
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");
		}
		return infracoesDoPeriodo;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
