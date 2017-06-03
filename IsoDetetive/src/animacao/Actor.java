package animacao;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import estruturas.*;


class innerSprite {
	public Vetor2D_int 	projectionPosition;
	public Sprite		sprite;
	
	public innerSprite( Vetor2D_int position , Sprite sprite ) {
		this.projectionPosition = new Vetor2D_int( position.x , position.y );
		this.sprite = sprite;
	}
}

public class Actor extends JLayeredPane implements Animavel {
	public Vetor2D_double	virtualPosition		= new Vetor2D_double( 0 , 0 );
	public double			virtualHeight		= 0;
	
	public Vetor2D_double 	projectionPosition 	= new Vetor2D_double( 0 , 0 );
	public Vetor2D_int 		projectionSize 		= new Vetor2D_int( 0 , 0 );
	
	public Container 		parentContainer = null;
	public Scene			parentScene = null;
	
	private ArrayList<innerSprite> innerSprites;
	
	public int desiredLayer = 0;
	
	private boolean 	destroing 		= false;
	private long		destroyDelay 	= 0;
	private long		destroyCounter	= 0;
	

	public Actor( int sizeX , int sizeY ) {
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

	// Adiciona um sprite animado a este ator
	public AnimatedSprite addAnimatedSprite( String fileName , Vetor2D_int relativePosition , int layer ) {
		AnimatedSprite aSpr;
		aSpr = new AnimatedSprite( fileName );
		aSpr.playAnimation();

		this.addSprite( aSpr , relativePosition, layer );
		
		return aSpr;
	}

	// Adiciona um sprite de tile a este ator	
	public TileSetSprite addTileSprite( String fileName , Vetor2D_int relativePosition , int layer ) {
		TileSetSprite tSpr;
		tSpr = new TileSetSprite( fileName );

		this.addSprite( tSpr , relativePosition, layer );
		
		return tSpr;
	}
	
	// Adiciona um sprite qualquer a este ator	
	public Sprite addSprite( Sprite sprite , Vetor2D_int relativePosition , int layer ) {
		Vetor2D_int spriteSize = sprite.getDimension();
		if( spriteSize.x > this.projectionSize.x ) {
			this.projectionSize.x = spriteSize.x;
			this.updateLocation();
		}
		if( spriteSize.y > this.projectionSize.y ) {
			this.projectionSize.y = spriteSize.y;
			this.updateLocation();
		}
		
		innerSprite newInnerSprite = new innerSprite( relativePosition , sprite );
		this.innerSprites.add( newInnerSprite );
		sprite.insertInto( this );
		this.setLayer( sprite , layer);
		
		Vetor2D_int spritePosition =  new Vetor2D_int( 0 , 0 );
		spritePosition.x = ( this.projectionSize.x / 2 ) + relativePosition.x - ( sprite.getDimension().x / 2 ) ;
		spritePosition.y = ( this.projectionSize.y / 2 ) + relativePosition.y - ( sprite.getDimension().y / 2 ) ;
		
		sprite.setBounds(spritePosition.x, spritePosition.y, spriteSize.x, spriteSize.y);
		
		return sprite;
	}
	
	@Override
	public void passTime(long time) {	
		// Em processo de destruição
		if( this.destroing == true ) {
			this.destroyCounter += time;
			if( this.destroyCounter >= this.destroyDelay  ) {
				this.setVisible(false);
				//Toolkit.getDefaultToolkit().sync();
				return;
			}
		}

		// Remove Ator
		if( this.parentContainer != null ) {
			if( this.getComponents().length == 0 ) {
				this.setVisible(false);
				Toolkit.getDefaultToolkit().sync();
				this.parentContainer.remove(this);
			}
		}
		
		// Anima os sprites internos
		for( innerSprite iSpr : this.innerSprites ) {
    		if (iSpr.sprite instanceof Animavel ) {
    			((Animavel) iSpr.sprite).passTime( time );
    		}
		}
	}
	
	public void setVirtualPosition( double x , double y , double z ) {
		this.virtualHeight = z;
		this.setVirtualPosition(x, y);
	}
		
	public void setVirtualPosition( double x , double y ) {
		this.virtualPosition.x = x;
		this.virtualPosition.y = y;
		
		this.updateLocation();
	}

	private void updateLocation() {
		this.setSize( this.projectionSize.x , this.projectionSize.y );
		
		Vetor2D_int centerPosition;
		if( this.parentScene != null ) {
			centerPosition = this.parentScene.getProjection(virtualPosition);
		} 
		else {
			centerPosition = new Vetor2D_int( (int)this.virtualPosition.x , (int)this.virtualPosition.y );
		}
		
		this.projectionPosition.x = centerPosition.x - ( this.projectionSize.x / 2 );
		this.projectionPosition.y = centerPosition.y - ( this.projectionSize.y / 2 );
		
		super.setLocation( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
	}	
	
	// Lembrando: essa é a posição REAL ( em tela )
	public void setLocation( int x , int y ) {
		this.setSize( this.projectionSize.x , this.projectionSize.y );		
		
		this.projectionPosition.x = x - ( this.projectionSize.x / 2 );
		this.projectionPosition.y = y - ( this.projectionSize.y / 2 );
		
		super.setLocation( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
	}
	
	public Vetor2D_int getProjectionCenter() {
		Vetor2D_int projectionCenter = new Vetor2D_int( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
		projectionCenter.x += ( this.projectionSize.x / 2 );
		projectionCenter.y += ( this.projectionSize.y / 2 );
		
		return projectionCenter;
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
	
	public void destroyFx( ) {
		if( this.destroing == false ) {
			this.destroing = true;
		}
	}
}
