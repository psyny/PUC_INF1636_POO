package atores;

import animacao.*;
import estruturas.Vetor2D_int;

public class AtorBackground extends Actor {

	public AtorBackground( String fileName ) {
		super(100,100);
		
		addAnimatedSprite( fileName ,  new Vetor2D_int(0,0) , 0 );
				
		this.autoSize();
		
		this.posicaoAncora = Actor.PosicaoAncora.SUPERIOR_ESQUERDO;
	}
	

}
