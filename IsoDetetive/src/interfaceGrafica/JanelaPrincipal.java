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
import jogo.ControladoraDoJogo;


public class JanelaPrincipal extends JFrame {	
	public static JanelaPrincipal singleton = null;	
	public Container mainContentPane;
	public Vetor2D_int tamanho;
	
	
	//Contrutor de JanelaPrincipal
	private JanelaPrincipal() {
		tamanho = new Vetor2D_int( 1024 , 768 );
		
    	setSize( tamanho.x , tamanho.y );

    	setTitle("Jogo dos Detetives");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
	}

	//inicializador statico da JanelaPrincipal, que inicializa a variavel static singleton
	public static void inicializar() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Cria a janela e prepara o Singleton			
				JanelaPrincipal.singleton = new JanelaPrincipal();
				JanelaPrincipal.singleton.mainContentPane =  JanelaPrincipal.singleton.getContentPane();
				
				

				// Exibe a janela inicial
				JanelaPrincipal.singleton.carregarQuadro( new QuadroInicial() );
				
				// Finalmente, exibe a janela
				JanelaPrincipal.singleton.setVisible( true );
			}
			
		} ); // EventQueue end
	}
	
	//Remove todos os componentes do mainContentPane
	private void removerTodoConteudo() {
		this.mainContentPane.removeAll();	
	}
	
	//Repinta e Revalida o mainContentPane
	private void revalidar() {
		this.mainContentPane.repaint();
		this.mainContentPane.validate();
	}
	
	//Limpa o mainContentPane e dpois adiciona um componente ao ele, e repinta
	public void carregarQuadro( Component cmp ) {
		this.removerTodoConteudo();
		this.mainContentPane.add( cmp );
		this.revalidar();
	}
	
	//Define o LayoutManager do mainContentPane
	public void definirLayoutManager( LayoutManager mgr ) {
		this.mainContentPane.setLayout( mgr );
	}
	
	//Define o LayoutManager para FlowLayout
	public void resetarLayoutManager() {
		JanelaPrincipal.getInstance().mainContentPane.setLayout( new FlowLayout() );
	}
	
	public static JanelaPrincipal getInstance() {
		return JanelaPrincipal.singleton;
	}
	
}
