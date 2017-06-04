package atores;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.PersonagemEnum;

@SuppressWarnings("serial")
public class AtorJogador extends Actor {
	private AnimatedSprite spriteJogador;

	public AtorJogador( PersonagemEnum personagem ) {
		super(512, 512);
		
		String fileName = "";
		
		switch( personagem ) {
			case L:
				fileName = "player_white.txt";
				break;
				
			case SHERLOCK:
				fileName = "player_blue.txt";
				break;
				
			case CARMEN:
				fileName = "player_red.txt";
				break;
				
			case PANTERA:
				fileName = "player_pink.txt";
				break;
				
			case EDMORT:
				fileName = "player_yellow.txt";
				break;
				
			case BATMAN:
				fileName = "player_black.txt";
				break;
		}
				
		
		this.spriteJogador = addAnimatedSprite( fileName , new Vetor2D_int(0,-40) , 0 );	
	}
	
	

}
