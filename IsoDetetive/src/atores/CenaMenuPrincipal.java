package atores;

import java.awt.Color;

import animacao.Actor;
import animacao.Scene;
import estruturas.Vetor2D_int;

public class CenaMenuPrincipal extends Scene {
	
	public CenaMenuPrincipal()
	{
		super(50, 50, 900, 600);
		
		setBackground(Color.BLACK);
		setOpaque(true);
		
		Actor testActor = new Actor( 200 , 200 );
		testActor.setLocation( 200, 100 );   
		testActor.addAnimatedSprite( "tileSelector.txt" , new Vetor2D_int(0,0) , 0 );
		testActor.getAnimatedSprite().playAnimation(1);
		this.addActor( testActor , 10 );
	}
	
	public CenaMenuPrincipal(int x, int y, int w, int h)
	{
		super(x, y, w, h);
	}
}
