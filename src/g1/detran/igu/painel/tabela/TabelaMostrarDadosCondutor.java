package g1.detran.igu.painel.tabela;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import g1.detran.logico.Condutor;
import g1.detran.logico.Deposito;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TabelaMostrarDadosCondutor extends JPanel implements
		ActionListener {

	JScrollPane rolagem;
	JLabel lmostrando;
	JLabel linicial;
	JLabel lA;
	JLabel lfinal;
	JLabel ltotal;
	JLabel lproximo;
	JLabel lde;
	JTextField tfbusca;
	JButton btmostrar;
	JTable tabela;
	Deposito deposito;
	DefaultTableModel modelo;
	int inicial = 0;
	int fim;

	public TabelaMostrarDadosCondutor(Deposito deposito) {
		this.deposito = deposito;
		define();
		posicione();
	}

	private void define() {
		lmostrando = new JLabel("Mostrando");
		linicial = new JLabel("");
		lA = new JLabel(" A ");
		lfinal = new JLabel("");
		lde = new JLabel("de ");
		try {
			String total = "" + deposito.getQntdCondutoresCadastrados();
			ltotal = new JLabel(total);
		} catch (ExcecaoErroDeAcesso e2) {
			JOptionPane.showMessageDialog(null, "Erro de acesso!");

		}

		lproximo = new JLabel("Mostrar Próximos");
		tfbusca = new JTextField(7);
		tfbusca.setMaximumSize(tfbusca.getPreferredSize());
		btmostrar = new JButton("Mostrar");
		btmostrar.addActionListener(this);

		String[] colunas = new String[3];
		String[][] valores = new String[0][3];
		colunas[0] = "CPF";
		colunas[1] = "Nome";
		colunas[2] = "Data de Nascimento";

		modelo = new DefaultTableModel(valores, colunas);
		tabela = new JTable(modelo);
		rolagem = new JScrollPane(tabela);

	}

	public void posicione() {
		GroupLayout gl = new GroupLayout(this);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		setLayout(gl);

		// Horizontal
		{
			SequentialGroup sgDados = gl.createSequentialGroup();
			SequentialGroup sgPesquisa = gl.createSequentialGroup();
			ParallelGroup pg = gl.createParallelGroup(Alignment.CENTER);

			pg.addGroup(sgDados).addGroup(sgPesquisa).addComponent(rolagem);
			sgDados.addComponent(lmostrando).addComponent(linicial)
					.addComponent(lA).addComponent(lfinal).addComponent(lde)
					.addComponent(ltotal);
			sgPesquisa.addComponent(lproximo).addComponent(tfbusca)
					.addComponent(btmostrar);
			gl.setHorizontalGroup(pg);
		}
		// Vertical
		{

			ParallelGroup pgDados = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgPesquisa = gl.createParallelGroup(Alignment.CENTER);
			SequentialGroup sg = gl.createSequentialGroup();

			sg.addGroup(pgDados).addGroup(pgPesquisa).addComponent(rolagem);
			pgDados.addComponent(lmostrando).addComponent(linicial)
					.addComponent(lA).addComponent(lfinal).addComponent(lde)
					.addComponent(ltotal);
			pgPesquisa.addComponent(lproximo).addComponent(tfbusca)
					.addComponent(btmostrar);
			gl.setVerticalGroup(sg);
		}

	}

	public void actionPerformed(ActionEvent e) {
		insertLinhaTabela();
	}

	private void insertLinhaTabela() {
		try {
			if (tfbusca.getText().trim().length() > 0) {
				modelo.setRowCount(0);
				int numeroLinhas = Integer.parseInt(tfbusca.getText());

				// Se tiver algum texto no campo final
				if (lfinal.getText().trim().length() > 0) {
					// Inicial recebe o valor do final mais 1.
					inicial = Integer.parseInt(lfinal.getText()) + 1;
					// Campo de texto incial visivel na tela recebe o valor da
					// variavel
					linicial.setText("" + inicial);
					// Variavel fim recebe o número de linhas(quantidade de
					// pesquisas) mais o inicial
					fim = numeroLinhas + inicial - 1;
					// Campo de texto fim que aparece em tela recebe a variavel
					// fim.
					lfinal.setText("" + (fim));
				} else {
					// Se o campo final ainda não tiver nenhum dado
					// O campo inicial recebe 1 e o número final recebe a
					// quantidade de linhas necessárias
					linicial.setText("1");
					fim = numeroLinhas;
					lfinal.setText("" + fim);
				}

				if (fim >= Integer.parseInt(ltotal.getText())) {
					// Se o a variavél fim chegar ser maior ou igual a
					// quantidade total de dados cadastrados
					lfinal.setText(ltotal.getText());
					fim = Integer.parseInt(ltotal.getText());
					btmostrar.setEnabled(false);
				}
				String[] coluna = new String[3];
				String CPF;
				try {
					Condutor[] condutores = deposito.getCondutoresEmArray(
							numeroLinhas, inicial);
					for (int x = 0; x <= condutores.length - 1; x++) {
						if (condutores[x] != null) {
							CPF = "" + condutores[x].getCPF();
							Date dataNascimento = condutores[x].getDataNascimento();
							SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
							String data = formatador.format(dataNascimento);
							coluna[0] = CPF;
							coluna[1] = condutores[x].getNome();
							coluna[2] = data;
							modelo.addRow(coluna);
						}

					}
				} catch (ExcecaoErroDeAcesso e2) {
					JOptionPane.showMessageDialog(null, "Erro de acesso!");
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Valor digitado não é um número!");
		}
	}
}