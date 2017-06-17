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
	
	private CenaAtores 			cenaAtores;
	private TradutorTabuleiro 	tradutorTabuleiro;
	private ArrayList<AtoresDoJogador> atoresDosJogadores;
	
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
		}
	}
}
