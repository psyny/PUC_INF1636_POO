package interfaceGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;





public class JanelaPrincipal extends JFrame {
	public static JanelaPrincipal singleton = null;	
	public Container mainContentPane;
	
	
	private JanelaPrincipal() {
    	setSize(1024, 768);

    	setTitle("Jogo dos Detetives");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
	}
	
	
	public static void inicializar() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Cria a janela e prepara o Singleton			
				JanelaPrincipal.singleton = new JanelaPrincipal();
				JanelaPrincipal.singleton.mainContentPane =  JanelaPrincipal.singleton.getContentPane();

				// Exibe a janela inicial
				JanelaPrincipal.carregarQuadro( new QuadroInicial() );
				
				// Finalmente, exibe a janela
				JanelaPrincipal.singleton.setVisible( true );
			}
			
		} ); // EventQueue end
	}
	
	
	private static void removerTodoConteudo() {
		JanelaPrincipal.singleton.mainContentPane.removeAll();	
	}
	
	private static void revalidar() {
		JanelaPrincipal.singleton.mainContentPane.repaint();
		JanelaPrincipal.singleton.mainContentPane.validate();
	}
	
	public static void carregarQuadro( Component cmp ) {
		JanelaPrincipal.removerTodoConteudo();
		JanelaPrincipal.singleton.mainContentPane.add( cmp );
		JanelaPrincipal.revalidar();
	}
}
