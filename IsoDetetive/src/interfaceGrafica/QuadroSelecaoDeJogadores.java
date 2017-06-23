package interfaceGrafica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import animacao.Camera;
import animacao.Scene;
import atores.AtorBackground;
import atores.AtorBotaoMenuPreJogo;
import atores.AtorCarta;
import atores.AtorEtiqueta;
import estruturas.Vetor2D_int;
import interfaceGrafica.QuadroInicial.actList_carregarJogo;
import interfaceGrafica.QuadroInicial.actList_novoJogo;
import jogo.*;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;



public class QuadroSelecaoDeJogadores extends JPanel {
	private JList lista_de_personagens;
	private JButton start;	
	
	// Listener que carrega proxima janela de jogo iniciado
	class actList_iniciarJogo extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			iniciarNovoJogo();
		}
	}	
	
	private class mouseListener_seletor extends MouseAdapter {
		AtorCarta atorCarta;
		
		public mouseListener_seletor( AtorCarta atorCarta ) {
			this.atorCarta = atorCarta;
			atorCarta.definirSelecionado(false);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			atorCarta.definirSelecionado( !atorCarta.getSelecionado() );
			
			certificarJogadoresMinimos();
		}
	}	
	
	
	// Listener de mudanças na lista de personagens
	private class listener_ListaDePesonagens implements ListSelectionListener {
		public JList list;
		public JButton button;
		
		public listener_ListaDePesonagens(JList list, JButton button) {
			this.list = list;
			this.button = button;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			List lista = list.getSelectedValuesList();
			if(lista.size() >= 3) {
				button.setEnabled(true);
			}	
			else {
				button.setEnabled(false);
			}
		}
	}
	
	// -------------------------------------------------------------------------
	
	private ArrayList<AtorCarta> cartas = new ArrayList<AtorCarta>();
	private AtorBotaoMenuPreJogo iniciarJogo = null;
	
	public QuadroSelecaoDeJogadores() {
		setBackground( new Color(230,230,230) );
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
	        AtorEtiqueta titulo = new AtorEtiqueta( AtorEtiqueta.Tipo.SELECAO_DE_PERSONAGENS );
	        cena.addActor( titulo , 1 );
	        titulo.setLocation(140, 0);
	        
	        // Novo Jogo
	        AtorBotaoMenuPreJogo botao_novoJogo = new AtorBotaoMenuPreJogo( AtorBotaoMenuPreJogo.Tipo.NOVO_JOGO );
	        this.iniciarJogo = botao_novoJogo;
	        botao_novoJogo.addMouseListener( new actList_iniciarJogo() );
	        cena.addActor( botao_novoJogo , 2 );
	        botao_novoJogo.setLocation(500, 600);
	        botao_novoJogo.setVisible( false );
	        
	        // Cartas
		        AtorCarta carta;
		        int x = 110;
		        int y = 350;
		        int incX = 160;
		        
		        ArrayList<CartaEnum> cartasPersonagens = new ArrayList<CartaEnum>();
		        cartasPersonagens.add(CartaEnum.L);
		        cartasPersonagens.add(CartaEnum.SHERLOCK);
		        cartasPersonagens.add(CartaEnum.CARMEN);
		        cartasPersonagens.add(CartaEnum.PANTERA);
		        cartasPersonagens.add(CartaEnum.EDMORT);
		        cartasPersonagens.add(CartaEnum.BATMAN);
		        
		        for( CartaEnum cartaTipo : cartasPersonagens ) {
			        carta = new AtorCarta();
			        carta.definirCarta( cartaTipo );
			        carta.addMouseListener( new mouseListener_seletor(carta) );
			        cena.addActor( carta , 5 );
			        cartas.add( carta );
			        carta.setLocation( x,  y );			        
			        x += incX;
		        }
		
	}	
	
	private void certificarJogadoresMinimos() {
		int jogadores = 0;
		for( AtorCarta atorCarta : cartas ) {
			if( atorCarta.getSelecionado() == true ) {
				jogadores++;
			}
		}
		
		if( jogadores >= 3 ) {
			this.iniciarJogo.setVisible( true );
		} else {
			this.iniciarJogo.setVisible( false );
		}
	}
	
	private void iniciarNovoJogo() {
		for( AtorCarta atorCarta : cartas ) {
			if( atorCarta.getSelecionado() == true ) {
				switch( atorCarta.obterTipo() ) {
					case L:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.L );
						break;
						
					case SHERLOCK:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.SHERLOCK );
						break;
						
					case CARMEN:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.CARMEN );
						break;
						
					case PANTERA:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.PANTERA );
						break;
						
					case EDMORT:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.EDMORT );
						break;
						
					case BATMAN:
						MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( PersonagemEnum.BATMAN );
						break;
						
					default:
						break;
				}
			}
		}
		
		JanelaPrincipal.getInstance().carregarQuadro( new QuadroJogo() );
	}
	
}
