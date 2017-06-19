package atores;

import animacao.Actor;
import animacao.AnimatedSprite;
import estruturas.Vetor2D_int;
import jogo.Carta;

public class AtorCarta extends Actor {
	
	protected boolean selecionado = false;
	
	public AtorCarta(Carta carta) {
		super(120, 180);
	}

	public AtorCarta(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		
	}
	
	public void definirCarta(String arquivo)
	{
		addAnimatedSprite(arquivo, new Vetor2D_int(0, 0), 1);
		getAnimatedSprite().playAnimation(2);
	}
	
	public boolean getSelecionado()
	{
		return selecionado;
	}
	
	public void definirSelecionado(boolean flag)
	{
		selecionado = flag;
		AnimatedSprite spr = getAnimatedSprite();
		
		if(spr == null)
			return;
		
		if(selecionado)
			spr.playAnimation(3);
		else
			spr.playAnimation(2);
	}

}
