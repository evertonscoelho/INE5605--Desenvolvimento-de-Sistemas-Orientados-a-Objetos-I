package g1.detran.igu.painel.pesquisa;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import g1.detran.igu.OpcaoBotoes;
import g1.detran.logico.Deposito;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import g1.detran.logico.Infracao;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;


public class PainelPesquisaInfracaoVeiculo extends JPanel implements
		ActionListener, FocusListener {

	Deposito deposito;
	JTable tabela;
	JLabel lPlaca;
	JLabel lResultado;
	JButton btPesquisar;
	JButton btInterromper;
	JTextField tfPlaca;
	JTextField tfResultado;
	DefaultTableModel modelo;
	JScrollPane rolagem;
	List<Infracao> infracaoPlaca;

	public PainelPesquisaInfracaoVeiculo(Deposito deposito) {

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

			sgBotoes.addComponent(lPlaca).addComponent(tfPlaca)
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

			pgBotoes.addComponent(lPlaca).addComponent(tfPlaca)
					.addComponent(btPesquisar).addComponent(btInterromper);

			sg.addGroup(pgBotoes).addComponent(rolagem).addGroup(pgResultado);
			gl.setVerticalGroup(sg);
		}

	}

	private void definaPosicoes() {
		lPlaca = new JLabel("Placa");

		tfPlaca = new JTextField(7);
		tfPlaca.setMaximumSize(tfPlaca.getPreferredSize());
		tfPlaca.addFocusListener(this);
		

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

		String[] colunas = new String[2];
		String[][] valores = new String[0][2];
		colunas[0] = "Ordem";
		colunas[1] = "Data da Infração";

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
				String placa = (tfPlaca.getText());
				infracaoPlaca = pesquise(placa);
				if (infracaoPlaca == null) {
					break;
				} else {
					monteTabela();
				}
			} catch (NumberFormatException e2) {
				tfResultado.setForeground(Color.red);
				tfResultado.setText("Placa inválida, digite 3 letras e 4 números");
			}
			break;
	}

}

	private List<Infracao> pesquise(String placa) {
		Infracao[] arrayInfracoes;
		List<Infracao> infracoesDaPlaca = new LinkedList<>();
		boolean terminou = false;
		int quantidadeInfracoes = 1;
		try {
			int limite = deposito.getQntdInfracoesCadastrados();

			while (!terminou && quantidadeInfracoes <= limite) {
				arrayInfracoes = deposito.getInfracoesEmArray(30,
						quantidadeInfracoes);
				quantidadeInfracoes += 30;
				for (int indice = 0; indice < arrayInfracoes.length; indice++) {
					if (arrayInfracoes[indice] != null){
						if (arrayInfracoes[indice].getPlacaVeiculo().equals(placa)) {
						infracoesDaPlaca.add(arrayInfracoes[indice]);
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
		return infracoesDaPlaca;
	}	

	private void monteTabela() {
		modelo.setRowCount(0);
		String[] colunas = new String[2];
		int ordem = 1;
		for (Infracao infracao : infracaoPlaca) {
			colunas[0] = "" + ordem++;
			colunas[1] = infracao.getDataInfracao();
			modelo.addRow(colunas);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		String placaMaiuscula = tfPlaca.getText();
		tfPlaca.setText(placaMaiuscula.toUpperCase());
		
	}
}
