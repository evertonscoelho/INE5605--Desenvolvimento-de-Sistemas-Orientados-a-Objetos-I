package g1.detran.igu.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import g1.detran.igu.JanelaPrincipal;
import g1.detran.igu.OpcaoMenu;

public class Menu extends JMenuBar{
	JMenu menuArmazena, menuCondutor, menuVeiculo, menuInfracao, menuPesquisa,
			menuMostra, menuAjuda;
	JMenuItem itemMemoria, itemArquivo, itemSair, itemCondutorManual,
			itemCondutorAutomatico, itemVeiculoManual, itemVeiculoAutomatico,
			itemInfracaoManual, itemInfracaoAutomatico, itemInfracaoCondutor,
			itemMostrar, itemInfracaoVeiculo, itemMaioresInfratores,
			itemInfracaoPeriodo, itemSobre;

	public Menu(JanelaPrincipal jp) {

		// Nome dos menus
		menuArmazena = new JMenu("Armazenamento");
		menuCondutor = new JMenu("Condutor");
		menuVeiculo = new JMenu("Veiculo");
		menuInfracao = new JMenu("Infração");
		menuPesquisa = new JMenu("Pesquisar");
		menuMostra = new JMenu("Mostrar");
		menuAjuda = new JMenu("Ajuda");

		// Nome dos itens
		itemMemoria = new JMenuItem("Em memória");
		itemArquivo = new JMenuItem("Em arquivo");
		itemSair = new JMenuItem("Sair");
		itemCondutorManual = new JMenuItem("Cadastro Manual");
		itemCondutorAutomatico = new JMenuItem("Cadastro Automático");
		itemVeiculoManual = new JMenuItem("Cadastro Manual");
		itemVeiculoAutomatico = new JMenuItem("Cadastro Automático");
		itemInfracaoManual = new JMenuItem("Cadastro Manual");
		itemInfracaoAutomatico = new JMenuItem("Cadastro Automático");
		itemInfracaoCondutor = new JMenuItem("Infrações do Condutor...");
		itemInfracaoVeiculo = new JMenuItem("Infrações do Veículo...");
		itemMaioresInfratores = new JMenuItem("Maiores infratores...");
		itemInfracaoPeriodo = new JMenuItem("Infrações por período...");
		itemMostrar = new JMenuItem("Mostrar dados");
		itemSobre = new JMenuItem("Sobre");
		itemMostrar = new JMenuItem("Mostrar dados cadastrados");

		// Cria o Menu "Armazenamento" e adiciona itens
		menuArmazena.add(itemMemoria);
		menuArmazena.add(itemArquivo);
		menuArmazena.add(itemSair);
		add(menuArmazena);
		itemMemoria.setActionCommand(OpcaoMenu.ITEMMEMORIA.name());
		itemMemoria.addActionListener(jp);

		itemArquivo.setActionCommand(OpcaoMenu.ITEMARQUIVO.name());
		itemArquivo.addActionListener(jp);

		itemSair.setActionCommand(OpcaoMenu.SAIR.name());
		itemSair.addActionListener(jp);

		// Cria o Menu "Condutor" e adiciona itens
		menuCondutor.add(itemCondutorManual);
		menuCondutor.add(itemCondutorAutomatico);
		add(menuCondutor);
		menuCondutor.setEnabled(false);
		itemCondutorManual.setActionCommand(OpcaoMenu.CONDUTORMANUAL.name());
		itemCondutorManual.addActionListener(jp);
		itemCondutorAutomatico
				.setActionCommand(OpcaoMenu.ITEMCONDUTORAUTOMATICO.name());
		itemCondutorAutomatico.addActionListener(jp);

		// Cria o Menu "Veiculo" e adiciona itens
		menuVeiculo.add(itemVeiculoManual);
		menuVeiculo.add(itemVeiculoAutomatico);
		add(menuVeiculo);
		menuVeiculo.setEnabled(false);
		itemVeiculoManual.setActionCommand(OpcaoMenu.VEICULOMANUAL.name());
		itemVeiculoManual.addActionListener(jp);
		itemVeiculoAutomatico.setActionCommand(OpcaoMenu.ITEMVEICULOAUTOMATICO
				.name());
		itemVeiculoAutomatico.addActionListener(jp);

		// Cria o Menu "Infracao" e adiciona itens
		menuInfracao.add(itemInfracaoManual);
		menuInfracao.add(itemInfracaoAutomatico);
		add(menuInfracao);
		menuInfracao.setEnabled(false);
		itemInfracaoManual.setActionCommand(OpcaoMenu.INFRACAOMANUAL.name());
		itemInfracaoManual.addActionListener(jp);
		itemInfracaoAutomatico.setActionCommand(OpcaoMenu.INFRACAOAUTOMATICO
				.name());
		itemInfracaoAutomatico.addActionListener(jp);

		// Cria o Menu "Pesquisa" e adiciona itens
		menuPesquisa.add(itemInfracaoCondutor);
		itemInfracaoCondutor.setActionCommand(OpcaoMenu.INFRACOESCPF.name());
		itemInfracaoCondutor.addActionListener(jp);

		menuPesquisa.add(itemInfracaoVeiculo);
		itemInfracaoVeiculo.setActionCommand(OpcaoMenu.INFRACAOVEICULO.name());
		itemInfracaoVeiculo.addActionListener(jp);

		menuPesquisa.add(itemMaioresInfratores);
		itemMaioresInfratores.setActionCommand(OpcaoMenu.MAIORESINFRATORES
				.name());
		itemMaioresInfratores.addActionListener(jp);

		menuPesquisa.add(itemInfracaoPeriodo);
		itemInfracaoPeriodo.setActionCommand(OpcaoMenu.INFRACAOPORPERIODO
				.name());
		itemInfracaoPeriodo.addActionListener(jp);

		add(menuPesquisa);
		menuPesquisa.setEnabled(false);

		// Cria o Menu "Mostrar" e adiciona itens
		menuMostra.add(itemMostrar);
		add(menuMostra);
		menuMostra.setEnabled(false);
		itemMostrar.setActionCommand(OpcaoMenu.MOSTRAR.name());
		itemMostrar.addActionListener(jp);

		// Cria o Menu "Ajuda" e adiciona itens
		menuAjuda.add(itemSobre);
		itemSobre.setActionCommand(OpcaoMenu.SOBRE.name());
		itemSobre.addActionListener(jp);
		add(menuAjuda);

	}

	public void habilitaMenu() {
			menuCondutor.setEnabled(true);
			menuVeiculo.setEnabled(true);
			menuInfracao.setEnabled(true);
			menuMostra.setEnabled(true);
			menuPesquisa.setEnabled(true);
		}

	
}
