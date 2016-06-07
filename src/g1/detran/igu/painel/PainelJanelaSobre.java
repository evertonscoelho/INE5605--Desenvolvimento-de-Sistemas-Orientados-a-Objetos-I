package g1.detran.igu.painel;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

public class PainelJanelaSobre extends JPanel {

	JLabel lnomeGrupo;
	JLabel lfotoEverton;
	JLabel lnomeEverton;
	JLabel lfotoDeon;
	JLabel lnomeDeon;
	JLabel lfotoYuri;
	JLabel lnomeYuri;
	JLabel lfotoBob;
	JLabel lnomeBob;
	JLabel lTF1;
	JLabel lTF2;
	JLabel lTF3;
	JLabel lItens;
	JLabel lItem1;
	JLabel lItem2;
	JLabel lItem3;
	JLabel lItem4;
	JLabel lItem5;
	JLabel lItem6;
	JLabel lItem7;
	JLabel lItem8;
	JLabel lItem9;
	JLabel lItem10;
	JLabel lItem11;
	JLabel lItem12;
	JLabel lItem13;

	public PainelJanelaSobre() {
		definaPosicoes();
		posicionePosicoes();
	}

	private void definaPosicoes() {
		lnomeGrupo = new JLabel("Grupo 1(G1)");
		lTF1 = new JLabel("Itens TF1");
		lTF2 = new JLabel("Itens TF2");
		lTF3 = new JLabel("Itens TF3");
		lItem1 = new JLabel("1. Cadastro manual de condutor.");
		lItem2 = new JLabel("2. Cadastro manual de veículo.");
		lItem3 = new JLabel("3. Cadastro manual de infração.");
		lItem4 = new JLabel("4. Cadastro automático de condutor.");
		lItem5 = new JLabel("5. Cadastro automático de veículos.");
		lItem6 = new JLabel("6. Cadastro automático de infrações.");
		lItem7 = new JLabel("11. Mostrar todos os condutores cadastrados.");
		lItem8 = new JLabel("12. Mostrar todos os veículos cadastrados.");
		lItem9 = new JLabel("13. Mostrar todas as infrações cadastradas.");
		lItem10 = new JLabel("7. Mostrar as infrações de um condutor, sendo o CPF do condutor fornecido pelo usuário.");
		lItem11 = new JLabel("8. Mostrar as infrações de um veículo, sendo a placa do veículo fornecida pelo usuário.");
		lItem12 = new JLabel("9. Mostrar os N maiores infratores, sendo N fornecido pelo usuário.");
		lItem13 = new JLabel("10. Mostrar as infrações cometidas em um determinado período.");

		ClassLoader cl = this.getClass().getClassLoader();
		Icon img = new ImageIcon(cl.getResource("Everton.jpg"));
		lfotoEverton = new JLabel(img);
		lnomeEverton = new JLabel("Everton S. Coelho");
		img = new ImageIcon(cl.getResource("Deon.jpg"));
		lfotoDeon = new JLabel(img);
		lnomeDeon = new JLabel("Thiago Deon");
		img = new ImageIcon(cl.getResource("Yuri.jpg"));
		lfotoYuri = new JLabel(img);
		lnomeYuri = new JLabel("Yuri Kayser");
		img = new ImageIcon(cl.getResource("Bob.jpg"));
		lfotoBob = new JLabel(img);
		lnomeBob = new JLabel("José Vitor Moura");
		lItens = new JLabel("Itens Realizados:");
		Font fonte = new Font("Serif", Font.BOLD, 20);
		lItens.setFont(fonte);
		lnomeGrupo.setFont(fonte);
	}

	private void posicionePosicoes() {
		GroupLayout gl = new GroupLayout(this);

		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);

		setLayout(gl);

		// Horizontal
		{
			SequentialGroup sgFotos = gl.createSequentialGroup();
			SequentialGroup sgFotos2 = gl.createSequentialGroup();
			ParallelGroup pg = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgBob = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgYuri = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgEverton = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgDeon = gl.createParallelGroup(Alignment.CENTER);
			ParallelGroup pgItens = gl.createParallelGroup(Alignment.LEADING);

			pg.addComponent(lnomeGrupo).addGroup(sgFotos).addGroup(sgFotos2)
					.addComponent(lItens).addGroup(pgItens);

			gl.setHorizontalGroup(pg);

			sgFotos.addGroup(pgEverton).addGroup(pgDeon);
			pgEverton.addComponent(lfotoEverton).addComponent(lnomeEverton);
			pgDeon.addComponent(lfotoDeon).addComponent(lnomeDeon);
			sgFotos2.addGroup(pgYuri).addGroup(pgBob);
			pgYuri.addComponent(lfotoYuri).addComponent(lnomeYuri);
			pgBob.addComponent(lfotoBob).addComponent(lnomeBob);
			pgItens.addComponent(lTF1).addComponent(lItem1)
					.addComponent(lItem2).addComponent(lItem3)
					.addComponent(lItem7).addComponent(lItem8)
					.addComponent(lItem9).addComponent(lTF2)
					.addComponent(lItem4).addComponent(lItem5)
					.addComponent(lItem6).addComponent(lTF3)
					.addComponent(lItem10).addComponent(lItem11)
					.addComponent(lItem12).addComponent(lItem13);

		}

		// Vertical
		{
			ParallelGroup pgFotos = gl.createParallelGroup(Alignment.LEADING);
			ParallelGroup pgNomes = gl.createParallelGroup(Alignment.LEADING);
			ParallelGroup pgFotos2 = gl.createParallelGroup(Alignment.LEADING);
			ParallelGroup pgNomes2 = gl.createParallelGroup(Alignment.LEADING);
			SequentialGroup sg = gl.createSequentialGroup();
			SequentialGroup sgItens = gl.createSequentialGroup();

			sg.addComponent(lnomeGrupo).addGroup(pgFotos).addGroup(pgNomes)
					.addGroup(pgFotos2).addGroup(pgNomes2).addComponent(lItens)
					.addGroup(sgItens);

			pgFotos.addComponent(lfotoEverton).addComponent(lfotoDeon);
			pgNomes.addComponent(lnomeEverton).addComponent(lnomeDeon);
			pgFotos2.addComponent(lfotoYuri).addComponent(lfotoBob);
			pgNomes2.addComponent(lnomeYuri).addComponent(lnomeBob);
			sgItens.addComponent(lTF1).addComponent(lItem1)
					.addComponent(lItem2).addComponent(lItem3)
					.addComponent(lItem7).addComponent(lItem8)
					.addComponent(lItem9).addComponent(lTF2)
					.addComponent(lItem4).addComponent(lItem5)
					.addComponent(lItem6).addComponent(lTF3)
					.addComponent(lItem10).addComponent(lItem11)
					.addComponent(lItem12).addComponent(lItem13);
			gl.setVerticalGroup(sg);
		}
	}
}
