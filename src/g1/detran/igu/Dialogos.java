package g1.detran.igu;

import javax.swing.JDialog;
import javax.swing.JPanel;

public abstract class  Dialogos extends JDialog {

    public Dialogos(String titulo, JPanel painel){
		this.setTitle(titulo);
		setContentPane(painel);
		this.setModal(true);
		setResizable(false);
		pack();												//Calcula o tamanho da janela em fun��o do tamanho do painel.
		setLocationRelativeTo(null);						//Centraliza a janela na tela.
		setVisible(true);									//A janela fica vis�vel para o usu�rio.
    }
    
}
