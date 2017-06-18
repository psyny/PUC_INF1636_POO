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
	protected int 				casa_largura;
	protected int				casa_altura;
	
	public TradutorTabuleiro( int casa_largura , int casa_altura ) {
		// Checa se os objetos necessarios foram registrados no fluxo de jogo
		MediadorFluxoDeJogo.getInstance().checarRegistroCenaTabuleiro();
		MediadorFluxoDeJogo.getInstance().checarRegistroTabuleiro();
		
		this.casa_largura	= casa_largura;
		this.casa_altura 	= casa_altura;
	}
	
	public void popularTabuleiroGrafico() {
		// Definir tamanho virtual da cena tabuleiro
		int tamanho_x = MediadorFluxoDeJogo.getInstance().tabuleiro.objeterQtdColunas() * casa_largura;
		int tamanho_y = MediadorFluxoDeJogo.getInstance().tabuleiro.objeterQtdLinhas() * casa_altura;
		Vetor2D_int tamanhoVirtualDoTabuleiro = new Vetor2D_int( tamanho_x , tamanho_y );
		MediadorFluxoDeJogo.getInstance().cenaTabuleiro.definirTamanhoVirtual( tamanhoVirtualDoTabuleiro.x , tamanhoVirtualDoTabuleiro.y );
		
		// Popular cena com os atores ( tiles ) gráficos
		for( ArrayList<Casa> linha : MediadorFluxoDeJogo.getInstance().tabuleiro.casas ) {
			for( Casa casa : linha ) {
				AtorPiso piso = this.gerarPisoParaTipo( casa.type );
				MediadorFluxoDeJogo.getInstance().cenaTabuleiro.addActor( piso , 0 );	
				
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
		
		
		// Adicionar outros efeitos visuais
		switch( tipo ) {
			default:
				break;
				
			case CORREDOR:
				piso.talvezAdicionarSujeira(5);
				break;	
				
			case COZINHA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 7);
				break;
				
			case SL_MUSICA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 9);
				break;
				
			case SL_INVERNO_PORTA:
				piso.adicionarTapeteDeEntrada(2, 1);
				break;
				
			case SL_JANTAR_PORTA:
				piso.adicionarTapeteDeEntrada(2, 10);
				break;
				
			case SL_JOGOS_PORTA:
				piso.adicionarTapeteDeEntrada(2, 4);
				break;
				
			case SL_ESTAR_PORTA:
				piso.adicionarTapeteDeEntrada(2, 11);
				break;
				
			case ENTRADA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 2);
				break;
				
			case BIBLIOTECA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 3);
				break;	
				
			case ESCRITORIO_PORTA:
				piso.adicionarTapeteDeEntrada(2, 6);
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
		Casa casa = MediadorFluxoDeJogo.getInstance().tabuleiro.getCell(x, y);
	
		return this.obterCentroDaCasa(casa);
	}
	

} 
