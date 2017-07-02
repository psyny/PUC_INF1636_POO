package atores;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.Tabuleiro;
import mediadores.TradutorTabuleiro;

public class AtorPilar extends Actor {
	protected TileSetSprite spritePilar;
	protected int	tile = 1;

	public AtorPilar( int flagPilar ) {
		super( 128 , 256 );
	
		Vetor2D_int offset = new Vetor2D_int( 0 , 0 );
		
		switch( flagPilar ){
			case 1:
				tile = 1;
				break;
				
			case 2:
				tile = 2;
				break;
				
			case 3:
				tile = 3;
				break;
				
			case 4:
				tile = 4;
				break;				
		}

		this.spritePilar = this.addTileSprite( "pillarTiles.txt", offset , 0 );
		
		
		// Desenhando o sprite em si
		ehPelaMetade( true );
	}
	
	public void ehPelaMetade( boolean flag ) {
		if( flag == true ) {
			spritePilar.setTile( 11 , tile );
		}
		else {
			spritePilar.setTile( 1 , tile );
		}
	}
	
}
