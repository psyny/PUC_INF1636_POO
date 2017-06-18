package atores;

import animacao.*;
import estruturas.Vetor2D_int;

public class Marcador extends Actor {
	public Vetor2D_int casaReferente = new Vetor2D_int(0,0);
	private boolean	marcado = false;

	public Marcador() {
		this(128, 64);
	}	
	
	public Marcador(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		
		addAnimatedSprite( "tileSelector.txt" , new Vetor2D_int(0,0) , 0 );
		
		this.definirMarcado(false);
	}
	
	public void definirMarcado(boolean marca) {
		if( marca == false ) {
			if( marcado == true ) {
				this.getAnimatedSprite().playAnimation(1 , true );
				marcado = false;
			}
		} else {
			if( marcado == false ) {
				this.getAnimatedSprite().playAnimation(2 , true );
				marcado = true;
			}
		}
	}
	
	
	

}
