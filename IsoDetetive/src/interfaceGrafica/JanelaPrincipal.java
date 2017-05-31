package interfaceGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import estruturas.Vetor2D_int;





public class JanelaPrincipal extends JFrame {
	public static JanelaPrincipal singleton = null;	
	public Container mainContentPane;
	public Vetor2D_int tamanho;
	
	
	
	private JanelaPrincipal() {
		tamanho = new Vetor2D_int( 1024 , 768 );
		
    	setSize( tamanho.x , tamanho.y );

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
	
	public static void definirLayoutManager( LayoutManager mgr ) {
		JanelaPrincipal.singleton.mainContentPane.setLayout( mgr );
	}
	
	public static void resetarLayoutManager() {
		JanelaPrincipal.singleton.mainContentPane.setLayout( new FlowLayout() );
	}
}
