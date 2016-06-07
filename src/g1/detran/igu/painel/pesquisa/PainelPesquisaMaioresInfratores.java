package g1.detran.igu.painel.pesquisa;

import java.awt.Color;

import g1.detran.logico.Condutor;
import g1.detran.logico.Infratores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import g1.detran.igu.OpcaoBotoes;
import g1.detran.logico.Deposito;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

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
import javax.xml.namespace.QName;

public class PainelPesquisaMaioresInfratores extends JPanel implements
		ActionListener {

	Deposito deposito;
	JTable tabela;
	JLabel lNumero;
	JLabel lResultado;
	JButton btPesquisar;
	JButton btInterromper;
	JTextField tfNumero;
	JTextField tfResultado;
	DefaultTableModel modelo;
	JScrollPane rolagem;
	Infratores[] nInfratores;

	public PainelPesquisaMaioresInfratores(Deposito deposito) {

		this.deposito = deposito;
		definaPosicoes();
		posicionePosicoes();
		try {
			nInfratores = new Infratores[deposito
					.getQntdCondutoresCadastrados()];
		} catch (ExcecaoErroDeAcesso e) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");
		}

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

			sgBotoes.addComponent(lNumero).addComponent(tfNumero)
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

			pgBotoes.addComponent(lNumero).addComponent(tfNumero)
					.addComponent(btPesquisar).addComponent(btInterromper);

			sg.addGroup(pgBotoes).addComponent(rolagem).addGroup(pgResultado);
			gl.setVerticalGroup(sg);
		}

	}

	private void definaPosicoes() {
		lNumero = new JLabel("N");

		tfNumero = new JTextField(7);
		tfNumero.setMaximumSize(tfNumero.getPreferredSize());

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

		String[] colunas = new String[4];
		String[][] valores = new String[0][4];
		colunas[0] = "Ordem";
		colunas[1] = "Num. de Infrações";
		colunas[2] = "CPF";
		colunas[3] = "Nome";

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
				int numero = (Integer.parseInt(tfNumero.getText()));
				pesquise();
				ordenaArrayBolha();
				if (nInfratores[0] == null) {
					break;
				} else {
					monteTabela(numero);
				}
			} catch (NumberFormatException e2) {
				tfResultado.setForeground(Color.red);
				tfResultado.setText("Digite apenas números");
			}
			break;
		}
	}

	private void monteTabela(int numero) {
		modelo.setRowCount(0);
		String[] colunas = new String[4];
		int ordem = 1;
		try {
			int quantidadeCadastrada = deposito.getQntdCondutoresCadastrados();

			while (numero >= ordem && ordem <= quantidadeCadastrada) {
				if (nInfratores[ordem - 1] != null) {
					colunas[0] = "" + ordem++;
					colunas[1] = ""
							+ nInfratores[ordem - 2].getNumeroDeInfracoes();
					colunas[2] = "" + nInfratores[ordem - 2].getCpf();
					colunas[3] = deposito.getNomePeloCpf(nInfratores[ordem - 2]
							.getCpf());
					modelo.addRow(colunas);
				}
			}
		} catch (ExcecaoErroDeAcesso e) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");
		}
	}

	private void pesquise() {
		Condutor[] condutores;
		Infratores infratores;
		int quantidadeCondutores = 1;
		int indice = 0;
		try {
			int limite = deposito.getQntdVeiculosCadastrados();

			while (quantidadeCondutores <= limite) {
				condutores = deposito.getCondutoresEmArray(1,
						quantidadeCondutores++);
				if (condutores[0] != null) {
					infratores = new Infratores(
							deposito.getInfracoesDoCPF(condutores[0].getCPF()),
							condutores[0].getCPF());
					nInfratores[indice++] = infratores;

				}
				if (quantidadeCondutores > deposito
						.getQntdInfracoesCadastrados()) {
					tfResultado.setForeground(Color.green);
					tfResultado.setText("Pesquisa concluída!");
				}
			}
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}
	}

	public void ordenaArrayBolha() {

		int totalElementos = nInfratores.length;
		for (int fixo = 0; fixo <= totalElementos - 1; fixo++) {
			for (int variavel = fixo + 1; variavel <= totalElementos - 1; variavel++) {
				if (nInfratores[fixo] != null && nInfratores[variavel] != null) {
					if (nInfratores[fixo].getNumeroDeInfracoes() <= nInfratores[variavel]
							.getNumeroDeInfracoes()) {
						Infratores armazena = nInfratores[fixo];
						nInfratores[fixo] = nInfratores[variavel];
						nInfratores[variavel] = armazena;

					}
				}
			}
		}
	}
}
