package atores;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import animacao.Actor;
import animacao.Scene;
import atores.CameraMenu.Modos;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo;
import jogo.ControladoraDoJogo.EstadoDaJogada;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;



public class CenaMenuPrincipal extends Scene {
	class mouseListener_dado extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			MediadorFluxoDeJogo.getInstance().rolarDados();
			
			botao_mover.setVisible(true);
	
			botao_dado.setVisible(false);			
			botao_passar.setVisible(false);
			botao_mao.setVisible(false);
			botao_notas.setVisible(false);
			botao_acusar.setVisible(false);
			
			revalidarPosicaoDosBotoes();
		}
	}	
	
	class mouseListener_mover extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			MediadorFluxoDeJogo.getInstance().confirmarMovimento();
			
			botao_mover.setVisible(false);
			
			botao_passar.setVisible(true);
			botao_mao.setVisible(true);
			botao_notas.setVisible(true);
			botao_acusar.setVisible(true);
			
			revalidarPosicaoDosBotoes();
		}
	}

	class mouseListener_acusar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			System.out.println("Listener Acusar: Nao implementado");
		}
	}

	class mouseListener_verCartas extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MAO);
			TradutorMenus.getInstance().desenharCartasNaMao(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaMao);
		}
	}

	class mouseListener_verBlocoNotas extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.NOTAS);
			TradutorMenus.getInstance().desenharBlocoDeNotas(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaBlocoNotas);
		}
	}

	class mouseListener_passar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
		}
	}
	
	protected AtorBotaoMenuJogo personagem_l;
	protected AtorBotaoMenuJogo personagem_sherlock;
	protected AtorBotaoMenuJogo personagem_pantera;
	protected AtorBotaoMenuJogo personagem_carmen;
	protected AtorBotaoMenuJogo personagem_edmort;
	protected AtorBotaoMenuJogo personagem_batman;
	
	protected AtorBotaoMenuJogo botao_dado;
	protected AtorBotaoMenuJogo botao_mover;
	protected AtorBotaoMenuJogo botao_acusar;
	protected AtorBotaoMenuJogo botao_mao;
	protected AtorBotaoMenuJogo botao_notas;
	protected AtorBotaoMenuJogo botao_passar;	
	
	ArrayList<AtorBotaoMenuJogo> botoes = new ArrayList<AtorBotaoMenuJogo>();
	
	private Vetor2D_int proximaPosicao = new Vetor2D_int(0,0);
	
	public CenaMenuPrincipal()
	{
		super(0, 0, 900, 100);
		this.setVisible(false);
		
		inicializarBotoes();
		esconderBotoes();
		
		this.setVisible(true);
	}
	
	@Override
	public synchronized void addActor( Actor actor , int layer ) {
		super.addActor(actor, layer);
		if( actor instanceof AtorBotaoMenuJogo ) {
			botoes.add( (AtorBotaoMenuJogo)actor );
		}
	}

	private void inicializarBotoes() {
		// Personagens
		personagem_l = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_L );
		personagem_l.setVisible(false);
		this.addActor( personagem_l , 10 );
		
		personagem_sherlock = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_SHERLOCK );
		this.addActor( personagem_sherlock , 10 );
		
		personagem_pantera = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_PANTERA );
		this.addActor( personagem_pantera , 10 );	
		
		personagem_carmen = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_CARMEN );
		this.addActor( personagem_carmen , 10 );	
		
		personagem_edmort = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_EDMORT );
		this.addActor( personagem_edmort , 10 );	
		
		personagem_batman = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_BATMAN );
		this.addActor( personagem_batman , 10 );	
		
		// Controle de jogo		
		botao_acusar = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_ACUSAR );
		botao_acusar.addMouseListener( new mouseListener_acusar() );
		this.addActor( botao_acusar , 10 );		
		
		botao_mao = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_MAO );
		botao_mao.addMouseListener( new mouseListener_verCartas() );
		this.addActor( botao_mao , 10 );		
		
		botao_notas = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_NOTAS );
		botao_notas.addMouseListener( new mouseListener_verBlocoNotas() );
		this.addActor( botao_notas , 10 );	
		
		botao_dado = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_DADO );
		botao_dado.addMouseListener( new mouseListener_dado() );
		this.addActor( botao_dado , 10 );
		
		botao_mover = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_MOVER );
		botao_mover.addMouseListener( new mouseListener_mover() );
		this.addActor( botao_mover , 10 );
		
		botao_passar = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_PASSAR );
		botao_passar.addMouseListener( new mouseListener_passar() );
		this.addActor( botao_passar , 10 );				
	}

	public void esconderBotoes() {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			botao.setVisible(false);
			botao.setLocation(0, 0);
		}
		
		resetarPosicaoInicial();
	}
	
	public void ativarBotao( AtorBotaoMenuJogo.Tipo tipo ) {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.obterTipo() == tipo ) {
				botao.setVisible(true);
				break;
			}
		}
		
		revalidarPosicaoDosBotoes();
	}	
	
	public void desativarBotao( AtorBotaoMenuJogo.Tipo tipo ) {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.obterTipo() == tipo ) {
				botao.setVisible(false);
				break;
			}
		}
		
		revalidarPosicaoDosBotoes();
	}
	
	private void revalidarPosicaoDosBotoes() {
		resetarPosicaoInicial();
		
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.isVisible() == true ) {
				botao.setLocation( proximaPosicao.x , proximaPosicao.y );
				
				proximaPosicao.x += 100;
			}
		}
	}
	
	private void resetarPosicaoInicial() {
		proximaPosicao.x = 50;
		proximaPosicao.y = 50;
	}
}
