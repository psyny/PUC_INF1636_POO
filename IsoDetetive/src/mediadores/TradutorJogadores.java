package mediadores;

import java.util.ArrayList;

import atores.*;
import estruturas.*;
import jogo.*;


public class TradutorJogadores {
	private class AtoresDoJogador {
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

		
			Casa casaInicial = jogador.obterPersonagem().obterCasaInicial();
			jogador.definirPosicao( casaInicial );

			Vetor2D_double posicao = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casaInicial.position.x , casaInicial.position.y );
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
	
	public void reposicionarJogador( Jogador jogador , Vetor2D_int novaCasa ) {
		AtoresDoJogador atoresJogador = obterAtoresDoJogador( jogador );
		
		if( atoresJogador == null ) {
			return;
		}
		
		Casa casa = MediadorFluxoDeJogo.getInstance().tabuleiro.getCell( novaCasa.x , novaCasa.y );
		jogador.definirPosicao( casa );
		
		Vetor2D_double novaPosicao = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa(casa);
		atoresJogador.atorJogador.setVirtualPosition( novaPosicao.x , novaPosicao.y );
	}
	
	public void definirSombreado( Jogador jogador , boolean estado ) {
		AtoresDoJogador atoresJogador = obterAtoresDoJogador( jogador );
		
		if( atoresJogador == null ) {
			return;
		}
		
		atoresJogador.atorJogador.definirSombreado( estado );
	}
}
