package jogo;

import java.util.ArrayList;
import estruturas.*;




public class Tabuleiro {
	public ArrayList<ArrayList<Casa>> cells;
	
	public int rows;
	public int cols;
	
	public int waterQtd = 0;
	
	public Vetor2D_int cellDimensions = new Vetor2D_int( 20 , 20 );
	
	public Tabuleiro( int rows , int cols ) {
		this.rows = rows;
		this.cols = cols;
		
		for( int y = 0 ; y < rows ; y++ ) {
			ArrayList<Casa> newRow = new ArrayList<Casa>();
			cells.add( newRow );
			for( int x = 0 ; x < cols ; x++ ) {
				newRow.add( new Casa( x , y ) );
			}	
		}
	}
	
	public Tabuleiro( ArrayList<ArrayList<Casa>> cells ) {
		this.cells = cells;
		this.rows = cells.size();
		this.cols = cells.get(0).size();
	}
	
	public boolean isInGrid( int x , int y ) {
		if( x < 0 ) {
			return false;
		}
		if( x >= this.cols ) {
			return false;
		}
		if( y < 0 ) {
			return false;
		}
		if( y >= this.rows ) {
			return false;
		}
		return true;
	}
	
	public Casa getCell( int x , int y ) {
		if( this.isInGrid(x, y) == false ) {
			return null;
		}
		
		return this.cells.get(y).get(x);
	}
	
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
