package g1.detran.igu;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import g1.detran.igu.painel.PainelJanelaPrincipal;
import g1.detran.logico.Deposito;
import g1.detran.logico.DepositoEmArquivo;
import g1.detran.logico.DepositoEmMemoria;
import g1.detran.logico.excecoes.ExcecaoErroDeAcesso;
import g1.detran.igu.OpcaoMenu;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroCondutor;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroCondutorAutomatico;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroInfracao;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroInfracaoAutomatico;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroVeiculo;
import g1.detran.igu.dialogo.cadastro.DialogoCadastroVeiculoAutomatico;
import g1.detran.igu.dialogo.pesquisa.DialogoMostraDados;
import g1.detran.igu.dialogo.pesquisa.DialogoPesquisaInfracaoPorCondutor;
import g1.detran.igu.dialogo.pesquisa.DialogoPesquisaInfracaoPorPeriodo;
import g1.detran.igu.dialogo.pesquisa.DialogoPesquisaInfracaoVeiculo;
import g1.detran.igu.dialogo.pesquisa.DialogoPesquisaMaioresInfratores;
import g1.detran.igu.menu.Menu;

public class JanelaPrincipal extends JFrame implements ActionListener {

	Deposito deposito;
	PainelJanelaPrincipal painelJanelaPrincipal;
	Menu menuPrincipal;

	public JanelaPrincipal() {
		super("G1: Trabalho Detran");
		setResizable(true); // A janela pode mudar de tamanho.
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Quando o programa for
													// fechado, a execu��o �
													// encerrada.
		setPreferredSize(new Dimension(800, 600));
		menuPrincipal = new Menu(this);
		setJMenuBar(menuPrincipal);
		pack(); // Determina tamanho final da janela.
		setLocationRelativeTo(null); // Centraliza a janela.
	}

	public void interaja() {
		setVisible(true); // Janela fica vis�vel ao usu�rio.
	}

	// Este m�todo vai ser executado quando um dos itens de menu forem clicados.
	@Override
	public void actionPerformed(ActionEvent e) {
		OpcaoMenu opcao = OpcaoMenu.valueOf(e.getActionCommand());

		// Quando um tipo de armazenamento for selecionado os outros menus s�o
		// liberados.
		switch (opcao) {
		case ITEMMEMORIA:
			painelJanelaPrincipal = new PainelJanelaPrincipal();
			deposito = new DepositoEmMemoria();
			setContentPane(painelJanelaPrincipal);
			painelJanelaPrincipal.visivel();
			menuPrincipal.habilitaMenu();
			break;
		case SAIR:
			System.exit(0); // Termina a execu��o do programa.
		case CONDUTORMANUAL:
			new DialogoCadastroCondutor(deposito, this);
			break;
		case VEICULOMANUAL:
			try {
				if (deposito.getQntdCondutoresCadastrados() > 0) {
					new DialogoCadastroVeiculo(deposito, this);// Abre
																// uma
																// nova
																// janela
																// para
					// cadastro de Veiculo.
				} else {
					JOptionPane.showMessageDialog(null,
							"Não há condutores cadastrados!");
				}
			} catch (ExcecaoErroDeAcesso e2) {
				JOptionPane.showMessageDialog(null, "Erro de acesso!");

			}
			break;
		case INFRACAOMANUAL:
			try {
				if (deposito.getQntdVeiculosCadastrados() > 0) {
					new DialogoCadastroInfracao(deposito, this);
				} else {
					JOptionPane.showMessageDialog(null,
							"Não há veículos cadastrados!");
				}
			} catch (ExcecaoErroDeAcesso e2) {
				JOptionPane.showMessageDialog(null, "Erro de acesso!");

			}
			break;

		case ITEMCONDUTORAUTOMATICO:
			new DialogoCadastroCondutorAutomatico(deposito, this);
			break;

		case ITEMVEICULOAUTOMATICO:
			try {
				if (deposito.getQntdCondutoresCadastrados() > 0) {
					new DialogoCadastroVeiculoAutomatico(deposito, this);
				} else {
					JOptionPane.showMessageDialog(null,
							"Não há condutores cadastrados!");
				}
			} catch (ExcecaoErroDeAcesso e2) {
				JOptionPane.showMessageDialog(null, "Erro de acesso!");

			}
			break;

		case SOBRE:
			new DialogoSobre();
			break;

		case MOSTRAR:
			new DialogoMostraDados(deposito);
			break;

		case INFRACAOAUTOMATICO:
			try {
				if (deposito.getQntdVeiculosCadastrados() > 0) {

					new DialogoCadastroInfracaoAutomatico(deposito, this);
				} else {
					JOptionPane.showMessageDialog(null,
							"Não há veículos cadastrados!");
				}
			} catch (ExcecaoErroDeAcesso e2) {
				JOptionPane.showMessageDialog(null, "Erro de acesso!");

			}
			break;

		case INFRACOESCPF:
			new DialogoPesquisaInfracaoPorCondutor(deposito);
			break;

		case INFRACAOVEICULO:
			new DialogoPesquisaInfracaoVeiculo(deposito);
			break;

		case MAIORESINFRATORES:
			new DialogoPesquisaMaioresInfratores(deposito);
			break;

		case INFRACAOPORPERIODO:
			new DialogoPesquisaInfracaoPorPeriodo(deposito);
			break;

		case ITEMARQUIVO:
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
			if (jfc.showDialog(this, "Escolher Diretório") == JFileChooser.APPROVE_OPTION) {
				File arquivo = jfc.getSelectedFile();
				if (arquivo.exists()) {
					painelJanelaPrincipal = new PainelJanelaPrincipal();
					try {
						deposito = new DepositoEmArquivo(jfc.getSelectedFile()
								.getAbsolutePath());
						painelJanelaPrincipal.atualizaPanel(deposito);
					} catch (ExcecaoErroDeAcesso e1) {
						JOptionPane.showMessageDialog(null,
								"Erro ao salvar os arquivos!");
					}
					setContentPane(painelJanelaPrincipal);
					painelJanelaPrincipal.visivel();
					menuPrincipal.habilitaMenu();
					break;
				} else {
					JOptionPane.showMessageDialog(null, "Arquivo não existe!");

				}
			} else {

			}

		}
	}

	public void atualizaPainel() {
		painelJanelaPrincipal.atualizaPanel(deposito);
	}

}
