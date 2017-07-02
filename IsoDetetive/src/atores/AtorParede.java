package atores;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.Tabuleiro;

public class AtorParede extends Actor {
	protected TileSetSprite parede;
	protected int	tileSet = 1;
	protected int	tile = 1;

	public AtorParede( Tabuleiro.DIRECOES direcao , int paredeFlag ) {
		super( 128 , 256 );
	
		Vetor2D_int offset = new Vetor2D_int( 0 , 0 );
		
		// Definindo a direcao...
		switch( direcao ) {
			case NORTE:
				tileSet = 1;
				break;
			
			case OESTE:
				tileSet = 2;
				break;
				
			case SUL:
				tileSet = 1;
				offset.x = -37;
				offset.y = +18;
				break;
			
			case LESTE:
				tileSet = 2;
				offset.x = +37;
				offset.y = +19;
				break;
		}
		
		// Definindo os cortes...
		switch( paredeFlag ) {
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
		
		this.parede = this.addTileSprite( "wallTiles.txt", offset , 0 );
		
		
		// Desenhando o sprite em si
		ehPelaMetade( true );
	}
	
	public void ehPelaMetade( boolean flag ) {
		if( flag == true ) {
			parede.setTile( tileSet + 10 , tile );
		}
		else {
			parede.setTile( tileSet + 0 , tile );
		}
	}
	
}
