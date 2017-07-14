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
import atores.AtorBotaoMenuJogo.Tipo;
import atores.AtorCarta.TipoMarcador;
import atores.AtorBotaoMenuPreJogo;
import atores.AtorCarta;
import atores.AtorEtiqueta;
import estruturas.Vetor2D_int;
import facedes.Facade_FluxoDeJogo;
import facedes.Facade_Menus;
import interfaceGrafica.QuadroInicial.actList_carregarJogo;
import interfaceGrafica.QuadroInicial.actList_novoJogo;
import jogo_Nucleo.*;
import jogo_TiposEnumerados.CartaType;
import jogo_TiposEnumerados.PersonagemType;



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
		EstadoCarta estadoCarta;
		
		public mouseListener_seletor( EstadoCarta estadoCarta) {
			this.estadoCarta = estadoCarta;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			estadoCarta.proximoEstado();
			
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
	
	public class EstadoCarta
	{
		protected AtorCarta atorCarta;
		protected TipoMarcador tipoJogador;

		public EstadoCarta(AtorCarta atorCarta)
		{
			this.atorCarta = atorCarta;
			tipoJogador = TipoMarcador.VAZIO;
			atualizarAtorCarta();
		}
		
		public void proximoEstado()
		{
			switch (tipoJogador) {
				case VAZIO:
					tipoJogador = TipoMarcador.HUMANO;
					break;
					
				case HUMANO:
					// tipoJogador = TipoMarcador.IA;
					tipoJogador = TipoMarcador.IA;
					break;
					
				case IA:
					tipoJogador = TipoMarcador.VAZIO;
					break;
			}
			
			atualizarAtorCarta();
		}
		
		private void atualizarAtorCarta()
		{
			switch (tipoJogador) {
				case VAZIO:
					atorCarta.definirMarcador(tipoJogador);
					atorCarta.definirSelecionado(false);
					break;
				case HUMANO:
				case IA:
					atorCarta.definirMarcador(tipoJogador);
					atorCarta.definirSelecionado(true);
					break;
			}
		}
	}
	
	// -------------------------------------------------------------------------
	
	private ArrayList<EstadoCarta> estadoCartas = new ArrayList<EstadoCarta>();
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
		        EstadoCarta estadoCarta;
		        int x = 110;
		        int y = 350;
		        int incX = 160;
		        
		        ArrayList<CartaType> cartasPersonagens = new ArrayList<CartaType>();
		        cartasPersonagens.add(CartaType.L);
		        cartasPersonagens.add(CartaType.SHERLOCK);
		        cartasPersonagens.add(CartaType.CARMEN);
		        cartasPersonagens.add(CartaType.PANTERA);
		        cartasPersonagens.add(CartaType.EDMORT);
		        cartasPersonagens.add(CartaType.BATMAN);
		        
		        for( CartaType cartaTipo : cartasPersonagens ) {
			        carta = new AtorCarta();
			        carta.definirCarta( cartaTipo );
			        estadoCarta = new EstadoCarta(carta);
			        estadoCartas.add( estadoCarta );
			        carta.addMouseListener( new mouseListener_seletor(estadoCarta) );
			        cena.addActor( carta , 5 );
			        carta.setLocation( x,  y );			        
			        x += incX;
		        }
		
	}	
	
	private void certificarJogadoresMinimos() {
		int jogadores = 0;
		for( EstadoCarta estadoCarta : estadoCartas ) {
			if( estadoCarta.atorCarta.getSelecionado() == true ) {
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
		ArrayList<EstadoDoJogo.TipoNovoJogador> novosJogadores = new ArrayList<EstadoDoJogo.TipoNovoJogador>();
		
		for( EstadoCarta estadoCarta : estadoCartas ) {
			EstadoDoJogo.TipoNovoJogador novoJogador = new EstadoDoJogo.TipoNovoJogador();
			
			switch( estadoCarta.atorCarta.obterTipo() ) {
				case L:
					novoJogador.personagemEnum = PersonagemType.L;
					break;
					
				case SHERLOCK:
					novoJogador.personagemEnum = PersonagemType.SHERLOCK;
					break;
					
				case CARMEN:
					novoJogador.personagemEnum = PersonagemType.CARMEN;
					break;
					
				case PANTERA:
					novoJogador.personagemEnum = PersonagemType.PANTERA;
					break;
					
				case EDMORT:
					novoJogador.personagemEnum = PersonagemType.EDMORT;
					break;
					
				case BATMAN:
					novoJogador.personagemEnum = PersonagemType.BATMAN;
					break;
					
				default:
					break;
			}
			
			if(estadoCarta.tipoJogador == TipoMarcador.VAZIO)
				novoJogador.emJogo = false;
			else
				novoJogador.emJogo = true;
			
			if(estadoCarta.tipoJogador == TipoMarcador.IA)
				novoJogador.inteligenciaArtificial = true;
			
			novosJogadores.add(novoJogador);
		}
		
		// Cria um novo estado de jogo
		EstadoDoJogo estadoDoJogo = new EstadoDoJogo();
		estadoDoJogo.gerarEstadoInicial( novosJogadores , null );
		ControladoraDoJogo.getInstance().definirEstadoDoJogo(estadoDoJogo);
		
		// TODO: Inteligencia Artificial ou Jogador?
		
		// Carregar quadro de jogo
		JanelaPrincipal.getInstance().carregarQuadro( new QuadroJogo() );
	}
	
}
