package atores;

import animacao.*;
import estruturas.Vetor2D_int;

@SuppressWarnings("serial")
public class AtorPiso extends Actor {
	private TileSetSprite spritePiso;
	private TileSetSprite spriteSujeira;

	public AtorPiso() {
		super(128, 128);
		this.spritePiso = addTileSprite( "houseTiles.txt" , new Vetor2D_int(0,0) , 0 );	
	}
	
	public void definirTile( int setID , int tileID ) {
		this.spritePiso.setTile(setID, tileID);
	}
	
	public void talvezAdicionarSujeira( int chance ) {
		if( (int)(Math.random()*100) < chance ) 
		{
			adicionarSujeira();
		}
	}
	
	public void talvezAdicionarSujeira() {
		talvezAdicionarSujeira( 25 );
	}
	
	public void adicionarSujeira() {
		this.spriteSujeira = addTileSprite( "floorMarks.txt" , new Vetor2D_int(0,0) , 1 );	
		this.spriteSujeira.setRandomTile(1);	
	}
	
	public void adicionarTapeteDeEntrada( int setID , int tile ) {
		this.spriteSujeira = addTileSprite( "entrances.txt" , new Vetor2D_int(0,0) , 1 );	
		this.spriteSujeira.setTile(setID, tile);
	}
 
}
