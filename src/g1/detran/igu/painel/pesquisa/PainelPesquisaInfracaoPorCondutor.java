package g1.detran.igu.painel.pesquisa;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import g1.detran.logico.Deposito;
import g1.detran.logico.Infracao;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;
import g1.detran.igu.OpcaoBotoes;
import javax.swing.GroupLayout;
import javax.swing.JButton;
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

public class PainelPesquisaInfracaoPorCondutor extends JPanel implements
		ActionListener {

	Deposito deposito;
	JTable tabela;
	JLabel lCPF;
	JLabel lResultado;
	JButton btPesquisar;
	JButton btInterromper;
	JTextField tfCPF;
	JTextField tfResultado;
	DefaultTableModel modelo;
	JScrollPane rolagem;
	List<Infracao> infracaoCPF;

	public PainelPesquisaInfracaoPorCondutor(Deposito deposito) {
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

			sgBotoes.addComponent(lCPF).addComponent(tfCPF)
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

			pgBotoes.addComponent(lCPF).addComponent(tfCPF)
					.addComponent(btPesquisar).addComponent(btInterromper);

			sg.addGroup(pgBotoes).addComponent(rolagem).addGroup(pgResultado);
			gl.setVerticalGroup(sg);
		}

	}

	private void definaPosicoes() {
		lCPF = new JLabel("CPF");

		tfCPF = new JTextField(11);
		tfCPF.setMaximumSize(tfCPF.getPreferredSize());

		btInterromper = new JButton("Interromper");
		btInterromper.setActionCommand(OpcaoBotoes.INTERROMPER.name());
		btInterromper.addActionListener(this);

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
		colunas[1] = "Placa";
		colunas[2] = "Data da Infração";

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
				long CPF = (Long.parseLong(tfCPF.getText()));
				infracaoCPF = pesquise(CPF);
				if (infracaoCPF == null) {
					break;
				} else {
					monteTabela();
				}
			} catch (NumberFormatException e2) {
				tfResultado.setForeground(Color.red);
				tfResultado.setText("CPF inválido, digite apenas números");
			}
			break;
		}
	}

	private void monteTabela() {
		modelo.setRowCount(0);
		String[] colunas = new String[3];
		int ordem = 1;
		for (Infracao infracao : infracaoCPF) {
			colunas[0] = "" + ordem++;
			colunas[1] = infracao.getPlacaVeiculo();
			colunas[2] = infracao.getDataInfracao();
			modelo.addRow(colunas);
		}
	}

	private List<Infracao> pesquise(long CPF) {
		Infracao[] infracoes;
		List<Infracao> infracoesDoCPF = new LinkedList<>();
		boolean terminou = false;
		int quantidadeInfracoes = 1;
		try {
			int limite = deposito.getQntdInfracoesCadastrados();

			while (!terminou && quantidadeInfracoes <= limite) {
				infracoes = deposito.getInfracoesEmArray(30,
						quantidadeInfracoes);
				quantidadeInfracoes += 30;
				for (int indice = 0; indice < infracoes.length; indice++) {
					if (infracoes[indice] != null) {
						if (deposito.PlacaPertenceAoCpf(CPF,
								infracoes[indice].getPlacaVeiculo())) {
							infracoesDoCPF.add(infracoes[indice]);
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
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}
		return infracoesDoCPF;
	}
}
