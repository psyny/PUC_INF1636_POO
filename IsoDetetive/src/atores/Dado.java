package atores;

import java.util.ArrayList;

import animacao.*;
import estruturas.*;

public class Dado extends ActorPhysics implements AnimationEndObserved {
	private ArrayList<AnimationEndObserver> animationEndObserverList = new ArrayList<AnimationEndObserver>();
	
	protected int resultado = 1;
	protected boolean lancado = false;
	
	private Vetor3D_double posicaoAlvo = new Vetor3D_double( 0, 0 ,0 );
	
	public Dado( int sizeX , int sizeY ) {
		super(sizeX,sizeY);
		
		setVisible(false);
		
		addAnimatedSprite( "dice.txt" , new Vetor2D_int(0,0) , 0 );
	}

	public Dado() {
		this(180, 180);
	}
	
	public void Lancar( Vetor2D_double posicaoAlvo , int resultado ) {
		this.posicaoAlvo.x = posicaoAlvo.x;
		this.posicaoAlvo.y = posicaoAlvo.y;
		this.posicaoAlvo.z = 0;
		
		setVirtualPosition( posicaoAlvo.x , posicaoAlvo.y - 300 , 100 );
		getAnimatedSprite().playAnimation(100, (int)(Math.random()*25) );
		setVisible(true);
		lancado = true;
		
		this.resultado = resultado;
		acceleration.z = -1;
		speed.y = 2;	
	}
	
	@Override
	public void passTime(long time) {	
		super.passTime(time);
		if( isDestroyed() == true ) {
			return;
		}

		if( virtualPosition.z < 0 ) {
			motionStop();
			setVirtualPosition( posicaoAlvo.x , posicaoAlvo.y , posicaoAlvo.z);
			getAnimatedSprite().playAnimation(resultado);
			animationEndNotityObservers();
		}
	}
	
	public void definirSombreado(boolean estado) {
		int novoID = this.getAnimatedSprite().getCurrentAnimationID();
		if( estado == true ) {
			if( novoID > 0 && novoID < 10 ) {
				novoID += 10;
			}
		} else {
			if( novoID > 10 && novoID < 20 ) {
				novoID -= 10;
			}
		}
		
		this.getAnimatedSprite().playAnimation(novoID, true);
	}
	
	// OBSERVER PATTERN Method Group
	public void animationEndRegister(AnimationEndObserver observer) {
		animationEndObserverList.add(observer);
	}
	
	public void animationEndUnRegister(AnimationEndObserver observer) {
		animationEndObserverList.remove(observer);
	}
	
	private void animationEndNotityObservers() {
		for( AnimationEndObserver ob : animationEndObserverList ) {
			ob.animationEndNotify(this);
		}
	}	
}
