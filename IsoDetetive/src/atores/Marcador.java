package atores;

import animacao.*;
import estruturas.Vetor2D_int;

public class Marcador extends Actor {

	public Marcador() {
		this(128, 64);
	}	
	
	public Marcador(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		
		addAnimatedSprite( "tileSelector.txt" , new Vetor2D_int(0,0) , 0 );
	}
	
	
	

}
