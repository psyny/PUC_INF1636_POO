package interfaceGrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import animacao.Camera;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo;


public class JanelaPrincipal extends JFrame {
	public enum QUADRO {
		INICIAL,
		SELECAO_JOGADORES,
		JOGO,
		VITORIA,
		DESCONHECIDO
	}
	
	private static JanelaPrincipal instance = null;	
	public Container mainContentPane;
	public Vetor2D_int tamanho;
	
	private QUADRO quadroAtual = QUADRO.DESCONHECIDO;
	
	//Contrutor de JanelaPrincipal
	private JanelaPrincipal() {
		tamanho = new Vetor2D_int( 1024 , 768 );
		
    	setSize( tamanho.x , tamanho.y );
    	setResizable(false);
    	
    	setTitle("Jogo dos Detetives");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocationRelativeTo(null);
	}
	
	
	public static JanelaPrincipal getInstance() {
		if( instance == null ){
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					// Cria a janela e prepara o Singleton	
					JanelaPrincipal janelaPrincipal =  new JanelaPrincipal();
					JanelaPrincipal.instance = janelaPrincipal;
					
					janelaPrincipal.mainContentPane =  janelaPrincipal.getContentPane();			

					// Exibe a janela inicial
					janelaPrincipal.carregarQuadro( new QuadroInicial() );
					
					// Finalmente, exibe a janela
					janelaPrincipal.setVisible( true );
				}
				
			} ); // EventQueue end
		}
		
		return instance;
	}	

	//inicializador statico da JanelaPrincipal, que inicializa a variavel static singleton
	public static void inicializar() {
		

	}
	
	//Remove todos os componentes do mainContentPane
	private synchronized void removerTodoConteudo() {
		// Remove outros elementos
		this.mainContentPane.removeAll();	
	}
	
	//Repinta e Revalida o mainContentPane
	private void revalidar() {
		this.mainContentPane.repaint();
		this.mainContentPane.validate();
	}
	
	//Limpa o mainContentPane e dpois adiciona um componente ao ele, e repinta
	public synchronized void carregarQuadro( Component cmp ) {
		if( cmp instanceof QuadroInicial ) {
			this.quadroAtual = QUADRO.INICIAL;
		} else if( cmp instanceof QuadroSelecaoDeJogadores ) {
			this.quadroAtual = QUADRO.SELECAO_JOGADORES;
		} else if( cmp instanceof QuadroJogo ) {
			this.quadroAtual = QUADRO.JOGO;
		} else if( cmp instanceof QuadroVitoria ) {
			this.quadroAtual = QUADRO.VITORIA;	
		} else {
			this.quadroAtual = QUADRO.DESCONHECIDO;		
		}
				
		this.removerTodoConteudo();
		this.mainContentPane.add( cmp );
		this.revalidar();
	}
	
	// obter quadro
	public synchronized JanelaPrincipal.QUADRO obterQuadroAtual() {
		return this.quadroAtual;
	}
	
	//Define o LayoutManager do mainContentPane
	public void definirLayoutManager( LayoutManager mgr ) {
		this.mainContentPane.setLayout( mgr );
	}
	
	//Define o LayoutManager para FlowLayout
	public void resetarLayoutManager() {
		JanelaPrincipal.getInstance().mainContentPane.setLayout( new FlowLayout() );
	}
	
	//Obtem espaço interno ( dimensoes uteis ) da janela
	public Vetor2D_int obterDimensoesInternas() {
		Vetor2D_int dimensoes = new Vetor2D_int(0,0);
		Insets inset = this.getInsets();
		
		dimensoes.x = (int)this.getSize().getWidth() - inset.left - inset.right;
		dimensoes.y = (int)this.getSize().getHeight() - inset.top - inset.bottom;
		
		return dimensoes;
	}
}
