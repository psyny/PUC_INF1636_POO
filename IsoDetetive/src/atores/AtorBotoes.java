package atores;

import animacao.*;
import estruturas.Vetor2D_int;

@SuppressWarnings("serial")
public class AtorBotoes extends Actor {
	private AnimatedSprite botao;

	public AtorBotoes( String botaoFile ) {
		super(100, 30);
		this.botao = addAnimatedSprite( botaoFile , new Vetor2D_int(0,0) , 0 );	
	}
	
}
