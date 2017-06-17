package animacao;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import estruturas.*;




public class ActorPhysics extends Actor {
	public Vetor3D_double	speed				= new Vetor3D_double();
	public Vetor3D_double	acceleration		= new Vetor3D_double();
	
	public ActorPhysics( int sizeX , int sizeY ) {
		super( sizeX , sizeY );
	}	
	
	@Override
	public void passTime(long time) {	
		super.passTime(time);
		if( this.isDestroyed() == true ) {
			return;
		}
		
		this.speed.x = this.speed.x + ( this.acceleration.x * time / 1000 );
		this.speed.y = this.speed.y + ( this.acceleration.y * time / 1000 );
		this.speed.z = this.speed.z + ( this.acceleration.z * time / 1000 );	
		
		double newPosX = this.virtualPosition.x + ( this.speed.x * time / 10 );
		double newPosY = this.virtualPosition.y + ( this.speed.y * time / 10 );
		double newPosZ = this.virtualPosition.z + ( this.speed.z * time / 10 );				
		
		this.setVirtualPosition( newPosX , newPosY , newPosZ );
	}
	
	public void motionStop() {
		speed.x = 0;
		speed.y = 0;
		speed.z = 0;
		
		acceleration.x = 0;
		acceleration.y = 0;
		acceleration.z = 0;
	}
	


}
