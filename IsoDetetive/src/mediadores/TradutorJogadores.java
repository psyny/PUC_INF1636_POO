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
	
	protected CenaAtores 			cenaAtores;
	protected TradutorTabuleiro 	tradutorTabuleiro;
	protected ArrayList<AtoresDoJogador> atoresDosJogadores;
	
	public TradutorJogadores( CenaAtores cenaAtores , TradutorTabuleiro tradutorTabuleiro ) {
		this.cenaAtores = cenaAtores;
		this.tradutorTabuleiro = tradutorTabuleiro;
	}
	
	public void adicionarJogadores() {
		this.atoresDosJogadores = new ArrayList<AtoresDoJogador>();
		

		
		for( Jogador jogador : ControladoraDoJogo.getInstance().obterListaDeJogadores() ) {
			AtoresDoJogador atores = new AtoresDoJogador();
			atores.jogador = jogador;
			
			atores.atorJogador = new AtorJogador( jogador.obterPersonagem().obterEnum() );
			this.cenaAtores.addActor( atores.atorJogador , 10 );

		
			Casa casaInicial = jogador.obterPersonagem().obterCasaInicial();
			jogador.definirPosicao( casaInicial );

			Vetor2D_double posicao = this.tradutorTabuleiro.obterCentroDaCasa( casaInicial.position.x , casaInicial.position.y );
			atores.atorJogador.setVirtualPosition( posicao.x , posicao.y );
			
			atoresDosJogadores.add(atores);
		}
	}
	
	public void reposicionarJogador( Jogador jogador , Vetor2D_int novaCasa ) {
		for( AtoresDoJogador atoresJogador : atoresDosJogadores ) {
			if( atoresJogador.jogador == jogador ) {
				System.out.println("AAA");
				
				Casa casa = tradutorTabuleiro.tabuleiro.getCell( novaCasa.x , novaCasa.y );
				jogador.definirPosicao( casa );
				
				Vetor2D_double novaPosicao = tradutorTabuleiro.obterCentroDaCasa(casa);
				atoresJogador.atorJogador.setVirtualPosition( novaPosicao.x , novaPosicao.y );
			}
		}
	}
}
