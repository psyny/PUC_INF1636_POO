package mediadores;

import java.util.ArrayList;

import atores.*;
import estruturas.*;
import jogo.*;
import observers.Observer_JogadorReposicionado;


public class TradutorJogadores implements Observer_JogadorReposicionado {
	public class AtoresDoJogador {
		public Jogador 		jogador;
		public AtorJogador 	atorJogador;
	}
	
	protected ArrayList<AtoresDoJogador> atoresDosJogadores;
	
	public TradutorJogadores( ) {
		// Checa se os objetos necessarios foram registrados no fluxo de jogo
		MediadorFluxoDeJogo.getInstance().checarRegistroCenaAtores();
		MediadorFluxoDeJogo.getInstance().checarRegistroTradutorTabuleiro();
		MediadorFluxoDeJogo.getInstance().checarRegistroTabuleiro();
	}
	
	public void adicionarJogadores() {
		this.atoresDosJogadores = new ArrayList<AtoresDoJogador>();
		
		
		for( Jogador jogador : ControladoraDoJogo.getInstance().obterListaDeJogadores() ) {
			AtoresDoJogador atores = new AtoresDoJogador();
			atores.jogador = jogador;
			
			atores.atorJogador = new AtorJogador( jogador.obterPersonagem().obterEnum() );
			MediadorFluxoDeJogo.getInstance().cenaAtores.addActor( atores.atorJogador , 10 );

			jogador.register_JogadorReposicionadoObserved( this );
		
			Casa casaAtual = jogador.obterPosicao();

			Vetor2D_double posicao = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casaAtual.position.x , casaAtual.position.y );
			atores.atorJogador.setVirtualPosition( posicao.x , posicao.y );
			
			atoresDosJogadores.add(atores);
		}
	}
	
	private AtoresDoJogador obterAtoresDoJogador( Jogador jogador ) {
		for( AtoresDoJogador atoresJogador : atoresDosJogadores ) {
			if( atoresJogador.jogador == jogador ) {
				return atoresJogador;
			}
		}
		
		return null;
	}
	
	private AtoresDoJogador obterAtoresDoJogador( PersonagemEnum personagemEnum ) {
		for( AtoresDoJogador atoresJogador : atoresDosJogadores ) {
			if( atoresJogador.jogador.obterPersonagem().obterEnum() == personagemEnum ) {
				return atoresJogador;
			}
		}
		
		return null;
	}	
	
	public void reposicionarJogador( Jogador jogador , Vetor2D_int novaCasa ) {
		Casa casa = MediadorFluxoDeJogo.getInstance().tabuleiro.getCell( novaCasa.x , novaCasa.y );
		
		jogador.definirPosicao( casa ); // Essa chamada forca a chamada do observer
	}
	
	public void ObserverNotify_JogadorReposicionado( PersonagemEnum personagem , Vetor2D_int novaPosicao ) {
		AtoresDoJogador atoresJogador = obterAtoresDoJogador( personagem );
		if( atoresJogador == null ) {
			return;
		}
		
		Casa casa = MediadorFluxoDeJogo.getInstance().tabuleiro.getCell( novaPosicao.x , novaPosicao.y );
		
		Vetor2D_double novaPosicaoDouble = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa(casa);
		atoresJogador.atorJogador.setVirtualPosition( novaPosicaoDouble.x , novaPosicaoDouble.y );
	}
	
	public void definirSombreado( Jogador jogador , boolean estado ) {
		AtoresDoJogador atoresJogador = obterAtoresDoJogador( jogador );
		
		if( atoresJogador == null ) {
			return;
		}
		
		atoresJogador.atorJogador.definirSombreado( estado );
	}
	
	public ArrayList<AtoresDoJogador> obterAtoresDosJogadores()
	{
		return atoresDosJogadores;
	}
	
	public void inicializarAtoresDosJogadores()
	{
		atoresDosJogadores = new ArrayList<AtoresDoJogador>();
	}
	
	public static AtorBotaoMenuJogo.Tipo converterPersonagemEnumTipoBotao( PersonagemEnum personagemEnum ) {
		switch( personagemEnum ) {
			case L:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_L;
				
			case SHERLOCK:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_SHERLOCK;
				
			case CARMEN:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_CARMEN;
				
			case PANTERA:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_PANTERA;
				
			case EDMORT:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_EDMORT;
				
			case BATMAN:
				return AtorBotaoMenuJogo.Tipo.PERSONAGEM_BATMAN;
				
			default:
				return null;
		}
	}
}
