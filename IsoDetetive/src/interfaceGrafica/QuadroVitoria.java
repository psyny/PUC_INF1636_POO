package interfaceGrafica;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import animacao.Camera;
import animacao.Scene;
import atores.AtorBackground;
import atores.AtorBotaoMenuPreJogo;
import atores.AtorCarta;
import atores.AtorEtiqueta;
import estruturas.Vetor2D_int;
import interfaceGrafica.QuadroInicial.actList_carregarJogo;
import interfaceGrafica.QuadroInicial.actList_novoJogo;
import interfaceGrafica.QuadroSelecaoDeJogadores.EstadoCarta;
import jogo.Carta;
import jogo.PersonagemEnum;

public class QuadroVitoria extends JPanel{

	public QuadroVitoria(PersonagemEnum vencedor, ArrayList<Carta> crime)
	{
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
	        AtorBackground atorBackground = new AtorBackground( obterBackground(vencedor) );
	        cena.addActor( atorBackground , 0 );
	        atorBackground.setLocation(0, 0);
	        
	        AtorCarta carta;
	        int x = 110;
	        int y = 350;
	        int incX = 160;
	        
	        for (Carta elemento : crime) {
		        carta = new AtorCarta();
		        carta.definirCarta( elemento.tipo );
		        cena.addActor( carta , 5 );
		        carta.setLocation( x,  y );			        
		        x += incX;
			}
	        
	        // Titulo
	        /*AtorEtiqueta titulo = new AtorEtiqueta( AtorEtiqueta.Tipo.JOGO_DOS_DETETIVES );
	        cena.addActor( titulo , 1 );
	        titulo.setLocation(240, 0);*/
	        
	        // Novo Jogo
	        /*AtorBotaoMenuPreJogo botao_novoJogo = new AtorBotaoMenuPreJogo( AtorBotaoMenuPreJogo.Tipo.NOVO_JOGO );
	        botao_novoJogo.addMouseListener( new actList_novoJogo() );
	        cena.addActor( botao_novoJogo , 2 );
	        botao_novoJogo.setLocation(300, 240);*/
	}
	
	private String obterBackground(PersonagemEnum personagem)
	{
		switch (personagem) {
			case L:
				return "bg_mainscreen.txt";
			case CARMEN:
				return "bg_mainscreen.txt";
			case SHERLOCK:
				return "bg_mainscreen.txt";
			case EDMORT:
				return "bg_mainscreen.txt";
			case PANTERA:
				return "bg_mainscreen.txt";
			case BATMAN:
				return "bg_mainscreen.txt";
		}
		
		return null;
	}
}
