package interfaceGrafica;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import animacao.*;
import atores.*;
import estruturas.*;
import mediadores.*;




public class QuadroInicial extends JPanel {
	// Listener que carrega proxima janela de novo jogo
	class actList_novoJogo extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			JanelaPrincipal.getInstance().carregarQuadro( new QuadroSelecaoDeJogadores() );
		}
	}	

	// Listener que carrega o fileChooser para carregar um jogo existente
	class actList_carregarJogo extends MouseAdapter {
		JFrame frame;
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			TradutorMenus.getInstance().carregarJogo();
			
			JanelaPrincipal.getInstance().carregarQuadro( new QuadroJogo() );
		}
	}
	
	
	//Construtor do QuadroInicial
	public QuadroInicial() {
		setBackground( new Color(255,255,255) );
		setLayout( null );
		
		// Camera e Cena
		Vetor2D_int dim = JanelaPrincipal.getInstance().obterDimensoesInternas();
		Scene cena = new Scene( 0 , 0 , dim.x , dim.y );

        Camera camera = new Camera( cena , dim.x , dim.y );
        camera.hideScrolls();
        camera.setBounds(0, 0, dim.x, dim.y);
        add( camera );
        
		// Elementos
        	// BG
	        AtorBackground atorBackground = new AtorBackground( "bg_mainscreen.txt" );
	        cena.addActor( atorBackground , 0 );
	        atorBackground.setLocation(0, 0);
	        
	        // Titulo
	        AtorEtiqueta titulo = new AtorEtiqueta( AtorEtiqueta.Tipo.JOGO_DOS_DETETIVES );
	        cena.addActor( titulo , 1 );
	        titulo.setLocation(240, 0);
	        
	        // Novo Jogo
	        AtorBotaoMenuPreJogo botao_novoJogo = new AtorBotaoMenuPreJogo( AtorBotaoMenuPreJogo.Tipo.NOVO_JOGO );
	        botao_novoJogo.addMouseListener( new actList_novoJogo() );
	        cena.addActor( botao_novoJogo , 2 );
	        botao_novoJogo.setLocation(300, 240);
	        
	        // Continuar Jogo
	        AtorBotaoMenuPreJogo botao_carregarJogo = new AtorBotaoMenuPreJogo( AtorBotaoMenuPreJogo.Tipo.CONTINUAR_JOGO );
	        botao_carregarJogo.addMouseListener( new actList_carregarJogo() );
	        cena.addActor( botao_carregarJogo , 2 );
	        botao_carregarJogo.setLocation(750, 550);
	}	
}
