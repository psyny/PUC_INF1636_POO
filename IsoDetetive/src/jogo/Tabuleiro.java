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
		this.linhas = rows;
		this.colunas = cols;
		
		for( int y = 0 ; y < rows ; y++ ) {
			ArrayList<Casa> newRow = new ArrayList<Casa>();
			casas.add( newRow );
			for( int x = 0 ; x < cols ; x++ ) {
				newRow.add( new Casa( x , y ) );
			}	
		}
	}
	
	//Construtor do Tabuleiro
	public Tabuleiro( ArrayList<ArrayList<Casa>> cells ) {
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
						Personagem.definirCasaInicial( PersonagemEnum.L , casa.position );
						break;
					
					case INICIO_SHERLOCK:
						Personagem.definirCasaInicial( PersonagemEnum.SHERLOCK , casa.position );
						break;
						
					case INICIO_CARMEN:
						Personagem.definirCasaInicial( PersonagemEnum.CARMEN , casa.position );
						break;
						
					case INICIO_PANTERA:
						Personagem.definirCasaInicial( PersonagemEnum.PANTERA , casa.position );
						break;
						
					case INICIO_EDMORT:
						Personagem.definirCasaInicial( PersonagemEnum.EDMORT , casa.position );
						break;
						
					case INICIO_BATMAN:
						Personagem.definirCasaInicial( PersonagemEnum.BATMAN , casa.position );
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
	
	//Retorna a casa contida no (x, y) do tabuleiro
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
	
}
