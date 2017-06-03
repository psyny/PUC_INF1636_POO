package animacao;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import estruturas.*;




public class Actor_Physics extends Actor implements Animavel {
	public Vetor2D_double	realPosition		= new Vetor2D_double( 0 , 0 );
	
	public Vetor2D_double 	projectionPosition 	= new Vetor2D_double( 0 , 0 );
	public Vetor2D_int 		projectionSize 		= new Vetor2D_int( 0 , 0 );
	
	public Container parent = null;
	
	// public GameScene stage = null;
	
	private ArrayList<innerSprite> innerSprites;
	
	public int desiredLayer = 0;
	
	public boolean 		destroing 		= false;
	public long			destroyDelay 	= 0;
	private long		destroyCounter	= 0;
	
	public double 		moveSpeed = 0;
	public double 		moveDirection = 0;
	
	public int shotsOnTheFly = 0;
	

	public Actor_Physics( int sizeX , int sizeY ) {
		super(sizeX,sizeY);
		
		this.init();
		
		this.projectionSize.x = sizeX;
		this.projectionSize.y = sizeY;
	}
	
	private void init() {
		this.setVisible( true );
		this.setOpaque( false );
		this.setLayout( null );
		this.setBounds(0,0,1,1);
		
		this.setSize( 1 , 1 );
		
		this.innerSprites = new ArrayList<innerSprite>();
	}

	
	@Override
	public void passTime(long time) {	
		// Position
		this.setRealPosition( this.realPosition.x + ( this.moveSpeed * Math.cos( this.moveDirection ) ) , this.realPosition.y + ( this.moveSpeed * Math.sin( this.moveDirection ) ) );
		
		// Destroy
		if( this.destroing == true ) {
			this.destroyCounter += time;
			if( this.destroyCounter >= this.destroyDelay  ) {
				this.setVisible(false);
				//Toolkit.getDefaultToolkit().sync();
				//Singletons.actorScene.remove(this);
			}
		}
		
		for( innerSprite iSpr : this.innerSprites ) {
    		if (iSpr.sprite instanceof Animavel ) {
    			((Animavel) iSpr.sprite).passTime( time );
    		}
		}
		
		// Set to remove
		if( this.parent != null ) {
			if( this.getComponents().length == 0 ) {
				this.setVisible(false);
				Toolkit.getDefaultToolkit().sync();
				this.parent.remove(this);
			}
		}
	}
	
	public void setRealPosition( double x , double y ) {
		this.realPosition.x = x;
		this.realPosition.y = y;
		
		this.updateLocation();
	}
	
	public void setLocation( int x , int y ) {
		this.setSize( this.projectionSize.x , this.projectionSize.y );		
		
		this.projectionPosition.x = x - ( this.projectionSize.x / 2 );
		this.projectionPosition.y = y - ( this.projectionSize.y / 2 );
		
		super.setLocation( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
	}
	
	private void updateLocation() {
		/*
		this.setSize( this.projectionSize.x , this.projectionSize.y );
		
		Vetor2D_int centerPosition = IsoGrid.getProjection( this.realPosition.x ,  this.realPosition.y , true , false );
		this.projectionPosition.x = centerPosition.x - ( this.projectionSize.x / 2 );
		this.projectionPosition.y = centerPosition.y - ( this.projectionSize.y / 2 );
		
		super.setLocation( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
		*/
	}
	
	public AnimatedSprite getAnimatedSprite() {
		for( innerSprite iSpr : this.innerSprites ) {
    		if (iSpr.sprite instanceof Animavel ) {
    			return (AnimatedSprite)iSpr.sprite;
    		}
		}
		
		return null;
	}
	
	public Sprite getSprite(int num) {
		if( num >= this.innerSprites.size() ) {
			return null;
		} else {
			return this.innerSprites.get(0).sprite;
		}	
	}
	
	public Vetor2D_int getProjectionCenter() {
		Vetor2D_int projectionCenter = new Vetor2D_int( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
		projectionCenter.x += ( this.projectionSize.x / 2 );
		projectionCenter.y += ( this.projectionSize.y / 2 );
		
		return projectionCenter;
	}
	

	
	public void destroyFx( ) {
		if( this.destroing == false ) {
			this.destroing = true;
		}
	}
}
