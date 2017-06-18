package mediadores;

import java.util.ArrayList;

import animacao.Actor;
import atores.AtorJogador;
import atores.AtorPiso;
import atores.CenaTabuleiro;
import atores.Marcador;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;
import jogo.Casa;
import jogo.CasaType;
import jogo.Tabuleiro;

public class TradutorTabuleiro {
	protected Tabuleiro 		tabuleiro;
	protected CenaTabuleiro 	cenaTabuleiro;
	protected int 				casa_largura;
	protected int				casa_altura;
	
	public TradutorTabuleiro( Tabuleiro tabuleiro , CenaTabuleiro cenaTabuleiro , int casa_largura , int casa_altura ) {
		this.tabuleiro 		= tabuleiro;
		this.cenaTabuleiro 	= cenaTabuleiro;
		this.casa_largura	= casa_largura;
		this.casa_altura 	= casa_altura;
	}
	
	public void popularTabuleiroGrafico() {
		// Definir tamanho virtual da cena tabuleiro
		Vetor2D_int tamanhoVirtualDoTabuleiro = new Vetor2D_int( tabuleiro.objeterQtdColunas() * casa_largura , tabuleiro.objeterQtdLinhas() * casa_altura );
		cenaTabuleiro.definirTamanhoVirtual( tamanhoVirtualDoTabuleiro.x , tamanhoVirtualDoTabuleiro.y );
		
		// Popular cena com os atores ( tiles ) gráficos
		for( ArrayList<Casa> linha : this.tabuleiro.casas ) {
			for( Casa casa : linha ) {
				AtorPiso piso = this.gerarPisoParaTipo( casa.type );
				this.cenaTabuleiro.addActor( piso , 0 );	
				
				Vetor2D_double atorPos = this.obterCentroDaCasa( casa );
				
				piso.setVirtualPosition( atorPos.x , atorPos.y );
			}
		}
	}
	
	private AtorPiso gerarPisoParaTipo( CasaType tipo ) {
		AtorPiso piso = new AtorPiso();
		
		switch( tipo ) {
			default:
				piso.definirTile(1, 1);
				break;
				
			case VACUO:
				piso.definirTile(1, 2);
				break;
				
			case CENTRO:
				piso.definirTile(1, 2);
				break;		
				
			case CORREDOR:
				piso.definirTile(1, 1);
				break;					
				
			case COZINHA:
				piso.definirTile(2, 7);
				break;
				
			case SL_MUSICA:
				piso.definirTile(2, 9);
				break;
				
			case SL_INVERNO:
				piso.definirTile(2, 1);
				break;
				
			case SL_JANTAR:
				piso.definirTile(2, 10);
				break;
				
			case SL_JOGOS:
				piso.definirTile(2, 4);
				break;
				
			case SL_ESTAR:
				piso.definirTile(2, 11);
				break;
				
			case ENTRADA:
				piso.definirTile(2, 2);
				break;
				
			case BIBLIOTECA:
				piso.definirTile(2, 3);
				break;	
				
			case ESCRITORIO:
				piso.definirTile(2, 6);
				break;					
				
		}
		
		switch( tipo ) {
			default:
				break;
				
			case CORREDOR:
				piso.talvezAdicionarSujeira(5);
				break;					
				
			case COZINHA:
			case SL_MUSICA:	
			case SL_INVERNO:		
			case SL_JANTAR:	
			case SL_JOGOS:	
			case SL_ESTAR:	
			case ENTRADA:
			case BIBLIOTECA:
			case ESCRITORIO:
				piso.talvezAdicionarSujeira(10);
				break;							
		}		
		
		
		return piso;
	} 
	

	public Vetor2D_double obterCentroDaCasa( Casa casa ) {
		Vetor2D_double centro = new Vetor2D_double( casa.position.x * casa_largura , casa.position.y * casa_altura );

		return centro;
	}
	
	public Vetor2D_double obterCentroDaCasa( int x , int y ) {
		Casa casa = this.tabuleiro.getCell(x, y);
	
		return this.obterCentroDaCasa(casa);
	}
	

} 
