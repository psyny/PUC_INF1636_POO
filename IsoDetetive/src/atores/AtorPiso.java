package atores;

import animacao.*;
import estruturas.Vetor2D_int;

@SuppressWarnings("serial")
public class AtorPiso extends Actor {
	private TileSetSprite spritePiso;
	

	public AtorPiso(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		this.spritePiso = addTileSprite( "houseTiles.txt" , new Vetor2D_int(0,0) , 0 );	
	}
	
	public void definirTile( int setID , int tileID ) {
		this.spritePiso.setTile(setID, tileID);
	}

}
