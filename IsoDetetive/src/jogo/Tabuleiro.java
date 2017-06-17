package jogo;

import java.util.ArrayList;

import atores.AtorPiso;
import estruturas.*;


public class Tabuleiro {
	public ArrayList<ArrayList<Casa>> casas;
	
	private int linhas;
	private int colunas;
	
	public Vetor2D_int cellDimensions = new Vetor2D_int( 1 , 1 );
	
	//Construtor do Tabuleiro
	public Tabuleiro( int rows , int cols ) {
		ControladoraDoJogo.getInstance().tabuleiro = this;
		
		this.linhas = rows;
		this.colunas = cols;
		
		for( int y = 0 ; y < rows ; y++ ) {
			ArrayList<Casa> newRow = new ArrayList<Casa>();
			casas.add( newRow );
			
			for( int x = 0 ; x < cols ; x++ ) {
				Casa casa = new Casa( x , y );
				newRow.add( casa );
			}	
		}
	}
	
	//Construtor do Tabuleiro
	public Tabuleiro( ArrayList<ArrayList<Casa>> cells ) {
		ControladoraDoJogo.getInstance().tabuleiro = this;
		
		this.casas = cells;
		this.linhas = cells.size();
		this.colunas = cells.get(0).size();
	}
	
	public int objeterQtdLinhas() {
		return this.linhas;
	}
	
	public int objeterQtdColunas() {
		return this.linhas;
	}	
	
	// Coleta dados essenciais
	public void scanGameInfo() {
		// Localiza dados 
		for( ArrayList<Casa> linha : this.casas ) {
			for( Casa casa : linha ) {
				switch( casa.type ) {
					case INICIO_L:
						PersonagemLista.getInstance().obterPersonagem(  PersonagemEnum.L ).definirCasaInicial( casa.position );
						break;
					
					case INICIO_SHERLOCK:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.SHERLOCK ).definirCasaInicial( casa.position );
						break;
						
					case INICIO_CARMEN:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.CARMEN ).definirCasaInicial( casa.position );
						break;
						
					case INICIO_PANTERA:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.PANTERA ).definirCasaInicial(  casa.position );
						break;
						
					case INICIO_EDMORT:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.EDMORT ).definirCasaInicial(  casa.position );
						break;
						
					case INICIO_BATMAN:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.BATMAN ).definirCasaInicial( casa.position );
						break;	
				}
			}
		}
	}
	
	//Checa se dois inteiros fazem parte do tabuleiro
	public boolean isInGrid( int x , int y ) {
		if( x < 0 ) {
			return false;
		}
		if( x >= this.colunas ) {
			return false;
		}
		if( y < 0 ) {
			return false;
		}
		if( y >= this.linhas ) {
			return false;
		}
		return true;
	}
	
	// Retorna a casa contida no (x, y) do tabuleiro
	public Casa getCell( int x , int y ) {
		if( this.isInGrid(x, y) == false ) {
			return null;
		}
		
		return this.casas.get(y).get(x);
	}
	
	
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
	public ArrayList<Casa> getNeighbors( int x , int y ) {
		ArrayList<Casa> neighbors = new ArrayList<Casa>();

		Casa center = this.getCell( x , y );
		if( center == null ) {
			return neighbors;
		}
		
		Casa nei;
		
		nei = this.getCell(x+1, y);
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCell(x-1, y);
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCell(x, y+1);
		if( nei != null ) {
			neighbors.add( nei );
		}

		nei = this.getCell(x, y-1);
		if( nei != null ) {
			neighbors.add( nei );
		}		
		
		return neighbors;
	}
			
	
	// Checa se a casa esta livre para movimentar-se para ela
	public boolean isFree( Casa casa ) {
		// Checa se a casa é naturalmente acessivel
		if( casa.isWalkable() == false ) {
			return false;
		}
		
		// Checa se a casa está bloqueada por algum jogador
		ArrayList<Jogador> Jogadores = ControladoraDoJogo.getInstance().obterListaDeJogadores();
		for( Jogador jogador : Jogadores ) {
			if( jogador.posicao == casa ) {
				return false;
			}
		}
		
		return true;
	}
	
	// Retorna a casa na posicao x e y se a casa é livre para movimentar-se
	public Casa getFreeCell( int x , int y ) {
		Casa casa = getCell(x,y);
		
		if( casa == null ) {
			return null;
		}
		
		if( isFree(casa) == false ) {
			return null;
		}
		
		return casa;
	}
	
	// Obter todas as casas de um determinado tipo
		public ArrayList<Casa> obterCasasDoTipo() {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( int y = 0 ; y < linhas ; y++ ) {
				for( int x = 0 ; x < colunas ; x++ ) {
					casas.add( this.casas.get(x).get(y) );
				}	
			}
			
			return casas;
		}
		
		public ArrayList<Casa> obterCasasDoTipo( CasaType tipo ) {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( int y = 0 ; y < linhas ; y++ ) {
				for( int x = 0 ; x < colunas ; x++ ) {
					Casa casa = this.casas.get(x).get(y);
					
					if( casa.type == tipo ) {
						casas.add( casa );
					}
				}	
			}
			
			return casas;
		}
	
	// Obtem casas acessiveis a partir da casa X
		public ArrayList<Casa> obterCasasNaDistancia( ArrayList<Casa> casasDeOrigem , int distancia ) {
			// TODO, no momento retornamos apenas uma lista arbitraria de casas
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( int x = 0 ; x <= 10 ; x++ ) {
				for( int y = 0 ; y <= x ; y++ ) {
					Casa casa = getFreeCell( x , y );
					if( casa != null ) {
						casas.add( casa );
					}		
				}
			}
			
			return casas;
		}
		
		public ArrayList<Casa> obterCasasNaDistancia( Casa casaDeOrigem , int distancia ) {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			casas.add( casaDeOrigem );
			
			return obterCasasNaDistancia( casas , distancia );
		}
	
}
