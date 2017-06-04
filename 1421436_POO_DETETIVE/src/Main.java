import javax.swing.JFileChooser;

public class Main {
	
	public static JanelaInicial janelaInicial;
	public static JanelaIntermediaria janelaIntermediaria;
	public static JanelaTabuleiro janelaTabuleiro;
	public static JanelaDados janelaDados;
	
	public static void main(String[] args)
	{
		janelaTabuleiro = new JanelaTabuleiro("Janela inicial");
		janelaTabuleiro.setVisible(true);
	}
}
