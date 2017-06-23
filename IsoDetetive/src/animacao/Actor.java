package animacao;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import estruturas.*;


public class Actor extends JLayeredPane implements Animavel {
	class innerSprite {
		public Vetor2D_int 	projectionPosition;
		public Sprite		innerSprite;
		
		public innerSprite( Vetor2D_int position , Sprite sprite ) {
			projectionPosition = new Vetor2D_int( position.x , position.y );
			this.innerSprite = sprite;
		}
	}
	
	public enum DestroyStyle {
		NONE,
		DESTROYED,
		DELAY,
		ANIMATION
	}
	
	public enum PosicaoAncora { 
		CENTRO,
		SUPERIOR_CENTRO,
		SUPERIOR_ESQUERDO,
		SUPERIOR_DIREITO,
		INFERIOR_ESQUERDO,
		INFERIOR_DIREITO,
		INFERIOR_CENTRO
	}
	
	
	public Vetor3D_double	virtualPosition		= new Vetor3D_double( 0 , 0 , 0 );
	
	public Vetor2D_double 	projectionPosition 	= new Vetor2D_double( 0 , 0 );
	public Vetor2D_int 		projectionSize 		= new Vetor2D_int( 0 , 0 );
	
	public PosicaoAncora	posicaoAncora		= Actor.PosicaoAncora.CENTRO;
	
	public Scene			parentScene = null;
	
	private ArrayList<innerSprite> innerSprites;
	
	public int desiredLayer = 0;
	
	protected DestroyStyle	destroyStyle	= DestroyStyle.NONE;
	protected AnimatedSprite destroyAfter 	= null;
	protected long			destroyDelay 	= 0;
	protected long			destroyCounter	= 0;
	

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
	
	public void passTime(long time) {	
		// Em processo de destruição
		switch( this.destroyStyle ) {
			case NONE:
				break;
			
			case DELAY:
				this.destroyCounter += time;
				if( this.destroyCounter >= this.destroyDelay  ) {
					this.destroyRoutine();
					return;
				}
				break;
				
			case ANIMATION:
				if( this.destroyAfter == null ){
					this.destroyRoutine();
				} else {
					if( this.destroyAfter.isPaused() == true) {
						this.destroyRoutine();
						return;
					}
				}
				break;
			
		}
		
		// Anima os sprites internos
		for( innerSprite iSpr : this.innerSprites ) {
    		if (iSpr.innerSprite instanceof Animavel ) {
    			((Animavel) iSpr.innerSprite).passTime( time );
    		}
		}
	}
	
	public void setVirtualPosition( double x , double y , double z ) {
		this.virtualPosition.z = z;
		this.setVirtualPosition(x, y);
	}
		
	public void setVirtualPosition( double x , double y ) {
		this.virtualPosition.x = x;
		this.virtualPosition.y = y;
		
		this.updateLocation();
	}

	private void updateLocation() {
		setSize( projectionSize.x , projectionSize.y );	
		
		Vetor2D_int centerPosition;
		if( this.parentScene != null ) {
			centerPosition = this.parentScene.getProjection(virtualPosition.getVetor2D());
			centerPosition.y -= this.virtualPosition.z;
		} 
		else {
			centerPosition = new Vetor2D_int( (int)this.virtualPosition.x , (int)this.virtualPosition.y );
			centerPosition.y -= this.virtualPosition.z;
		}
		
		this.projectionPosition.x = centerPosition.x - ( this.projectionSize.x / 2 );
		this.projectionPosition.y = centerPosition.y - ( this.projectionSize.y / 2 )  - this.virtualPosition.z;
		
		super.setLocation( (int)this.projectionPosition.x , (int)this.projectionPosition.y );
		
		this.revalidateLayer();
	}	
	
	// Lembrando: essa é a posição REAL ( em tela )
	public void setLocation( int x , int y ) {
		setSize( projectionSize.x , projectionSize.y );	
		
		Vetor2D_double offSet = new Vetor2D_double(0,0);
		
		switch( this.posicaoAncora ) {
			case CENTRO:
				offSet.x = -(projectionSize.x / 2 );
				offSet.y = -(( projectionSize.y / 2 ) - virtualPosition.z );
				break;
				
			case SUPERIOR_DIREITO:
				offSet.x = -(projectionSize.x );
				offSet.y = 0;
				break;
				
			case SUPERIOR_ESQUERDO:
				offSet.x = 0;
				offSet.y = 0;
				break;
				
			case SUPERIOR_CENTRO:
				offSet.x = -(projectionSize.x / 2 );
				offSet.y = 0;
				break;
				
			case INFERIOR_DIREITO:
				offSet.x = -(projectionSize.x );
				offSet.y = -( projectionSize.y  - virtualPosition.z );
				break;
				
			case INFERIOR_ESQUERDO:
				offSet.x = 0;
				offSet.y = -( projectionSize.y - virtualPosition.z );
				break;
				
			case INFERIOR_CENTRO:
				offSet.x = -(projectionSize.x / 2 );
				offSet.y = -( projectionSize.y - virtualPosition.z );
				break;
		}
		
		projectionPosition.x = x + offSet.x;
		projectionPosition.y = y + offSet.y;
		
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
    		if (iSpr.innerSprite instanceof Animavel ) {
    			return (AnimatedSprite)iSpr.innerSprite;
    		}
		}
		
		return null;
	}
	
	public Sprite getSprite(int num) {
		if( num >= this.innerSprites.size() ) {
			return null;
		} else {
			return this.innerSprites.get(0).innerSprite;
		}	
	}
	
	public void setToDestroy() {
		this.setToDestroy(0);
	
	}
	
	public void setToDestroy( long delay_ms ) {
		this.destroyStyle = DestroyStyle.DELAY;
		this.destroyDelay = delay_ms;	
	}
	
	public void setToDestroy( AnimatedSprite aSpr ) {
		if( aSpr == null ) {
			this.destroyAfter = this.getAnimatedSprite();
		} else {
			this.destroyAfter = aSpr;
		}
		this.destroyStyle = DestroyStyle.ANIMATION;
	}
	
	
	public boolean isDestroyed() {
		if( this.destroyStyle == DestroyStyle.DESTROYED ) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getLayer() {
		int layer = 0;
		
		if( parentScene instanceof CenaIsometrica ) {
			layer = ((getProjectionCenter().y / 10 ) + desiredLayer );
		}
		else {
			layer = desiredLayer;
		}
		
		return layer;
	}
	
	public void revalidateLayer() {
		if( this.parentScene == null ) {
			return;
		}

		this.parentScene.setLayer( this , getLayer() );
	}
	
	public void clearActor() {
		for( innerSprite spr : innerSprites ) {
			spr.innerSprite.delete();
		}
		this.innerSprites.clear();
		this.removeAll();
		this.revalidate();
	}
	
	private void destroyRoutine() {
		this.setVisible(false);
		this.clearActor();
		this.destroyStyle = DestroyStyle.DESTROYED;
		//Toolkit.getDefaultToolkit().sync();
	}
	
	// Define o tamanho do ator como o tamanho do maior sprite que ele possui
	public void autoSize() {
		Vetor2D_int tamanhoTemporario = new Vetor2D_int(0,0);
		Vetor2D_int spriteLastSize;
		
		for( innerSprite sprite : innerSprites ) {
			if( sprite == null || sprite.innerSprite == null) {
				continue;				
			}
			spriteLastSize = sprite.innerSprite.getLastSize();

			tamanhoTemporario.x = ( Math.abs( sprite.projectionPosition.x  ) + (spriteLastSize.x / 2) ) * 2;
			tamanhoTemporario.y = ( Math.abs( sprite.projectionPosition.y  ) + (spriteLastSize.y / 2) ) * 2;

			if( tamanhoTemporario.x > projectionSize.x ) {
				projectionSize.x = tamanhoTemporario.x;
			}
			
			if( tamanhoTemporario.y > projectionSize.y ) {
				projectionSize.y = tamanhoTemporario.y;
			}
		}
		
		setSize( projectionSize.x , projectionSize.y );	
	}
}
