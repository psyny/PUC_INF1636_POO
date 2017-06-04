package mediadores;

import java.util.ArrayList;

import animacao.Actor;
import atores.AtorPiso;
import atores.CenaAtores;
import atores.CenaTabuleiro;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;
import jogo.Casa;
import jogo.CasaType;
import jogo.Tabuleiro;

public class GeradorAtores {
	private Tabuleiro 		tabuleiro;
	private CenaTabuleiro 	cenaTabuleiro;
	private CenaAtores 		cenaAtores;	
	private int 			casa_largura;
	private int				casa_altura;
	
	public GeradorAtores( Tabuleiro tabuleiro , CenaTabuleiro cenaTabuleiro , CenaAtores cenaAtores ) {
		this.tabuleiro 		= tabuleiro;
		this.cenaTabuleiro 	= cenaTabuleiro;
		this.cenaAtores 	= cenaAtores;
	}
	
	public void popularJogadores() {
		
	}
	

} 
