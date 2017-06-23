package animacao;

import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import estruturas.*;



public class Camera extends JScrollPane implements Runnable {
	private final int 				DELAY = 25;
	private Thread 					cameraThread;
	private ArrayList<Component>  	scenes;
	
	private Vetor2D_double			cameraTarget;
	private Vetor2D_double			cameraPosition;
	private Vetor2D_double			cameraValidPosition;
	private double					maxMoveSpeed = 1000;
	private double					minMoveSpeed = 1;
	
	
	private boolean					fixedTarget = true;
	private boolean					animated = true;
	
	private ArrayList<Animavel>		animableList = new ArrayList<Animavel>();
	
	
	
	public Camera( Component viewPort , int x , int y ) {
		super();
		this.scenes = new ArrayList<Component>();
		this.scenes.add(viewPort);
		this.cameraTarget = new Vetor2D_double( x , y );
		this.cameraPosition = new Vetor2D_double( x , y );
		this.cameraValidPosition = new Vetor2D_double( 0 , 0 );
		this.setViewportView( viewPort );
		this.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE); // ???
		
		//this.setBounds(0, 0, x, y);
		//this.revalidate();
		//this.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS );
	}

    @Override
    public void addNotify() {
        super.addNotify();
        
        this.setAutoscrolls( true );

        this.cameraThread = new Thread(this);
        this.cameraThread.start();
    }	
	
    private void threadCycle( long passed ) {
    	// Anima a Cena 
    	// synchronized( Singletons.gameCamera ) {	
	    	for( Component comp : this.scenes ) {
	    		if (comp instanceof Animavel ) {
	    			((Animavel) comp).passTime( passed );
	    		}
	    	}
    	// }
    	
    	// Anima outros elementos gráficos
    	for( Animavel anim : this.animableList ) {
    		anim.passTime( passed );
    	}
    	
    	// Controle da posição da camera
    	if( this.fixedTarget == true ) {
	    	// Set Position
	    	Vetor2D_double offset = new Vetor2D_double( this.getSize().getWidth() / 2 , this.getSize().getHeight() / 2 );
	    	Vetor2D_double effectiveTarget = new Vetor2D_double( this.cameraTarget.x - offset.x , this.cameraTarget.y - offset.y );
	    	
			// Boundaries
			if( effectiveTarget.x < 0 ) {
				effectiveTarget.x = 0;
			} else if ( effectiveTarget.x > 100000 ) {
				effectiveTarget.x = 100000;
			}
			
			if( effectiveTarget.y < 0 ) {
				effectiveTarget.y = 0;
			} else if ( effectiveTarget.y > 100000 ) {
				effectiveTarget.y = 100000;
			} 
			
			if( this.animated == false ) {
	    		this.cameraPosition.x = effectiveTarget.x;
	    		this.cameraPosition.y = effectiveTarget.y;
			} else {
		    	// Calculate Move Speed
		    	Vetor2D_double distance = this.cameraPosition.getDistanceToVector( effectiveTarget );
		    	if( distance.getModulus() > 1 ) {
		    		Vetor2D_double moveSpeed = new Vetor2D_double( distance.x / 10 , distance.y / 10 );
		    		distance.normalize();
		    		int xDir = 1;
		    		int yDir = 1;
		    		
		    		
		    		if( Math.abs( this.cameraPosition.x - effectiveTarget.x ) >= 1 ) {
		    			if( moveSpeed.x < 0 ) {
		    				xDir = -1;
		    			}
		    			
			    		if( Math.abs(moveSpeed.x) < this.minMoveSpeed ) {
			    			moveSpeed.x = this.minMoveSpeed * xDir;
			    		} else if ( Math.abs(moveSpeed.x) > this.maxMoveSpeed ) {
			    			moveSpeed.x = this.maxMoveSpeed * xDir;
			    		}
		    		} else {
		    			moveSpeed.x = 0;
		    		}	
		    		
		    		if( Math.abs( this.cameraPosition.y - effectiveTarget.y ) >= 1 ) {
		    			if( moveSpeed.y < 0 ) {
		    				yDir = -1;
		    			}
		    			
			    		if( Math.abs(moveSpeed.y) < this.minMoveSpeed ) {
			    			moveSpeed.y = this.minMoveSpeed * yDir;
			    		} else if ( Math.abs(moveSpeed.y) > this.maxMoveSpeed ) {
			    			moveSpeed.y = this.maxMoveSpeed * yDir;
			    		}
		    		} else {
		    			moveSpeed.y = 0;
		    		}
		
		    		this.cameraPosition.x += moveSpeed.x ;
		    		this.cameraPosition.y +=  moveSpeed.y ;
		    		
		       		// Finally Set   
		    		this.getViewport().setViewPosition( new Point( (int)this.cameraPosition.x , (int)this.cameraPosition.y ));
		    	} else {
		    		this.cameraPosition.x = effectiveTarget.x;
		    		this.cameraPosition.y = effectiveTarget.y;
		    	}
			}
    	}
    	
    	//Toolkit.getDefaultToolkit().sync();
    }	
	
	@Override
	public void run() {
		
        long beforeTime, timeDiff, sleep;
        timeDiff = 0;

        beforeTime = System.currentTimeMillis();

        while (true) {

        	threadCycle(DELAY);
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            beforeTime = System.currentTimeMillis();
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
           
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }   
        }	
	}
	
	public void setTarget( int x , int y ) {
		this.cameraTarget.x = x;
		this.cameraTarget.y = y;
	}
	
	public void setIsFixedOnTarget( boolean mode ) {
		this.fixedTarget = mode;
		
		if( mode == true ) {
			Point currentPosition = this.getViewport().getViewPosition();
	    	this.cameraPosition.x = currentPosition.x;
	    	this.cameraPosition.y = currentPosition.y;
		}
	}
	
	public void setPositionCenteredOn( int x , int y ) {
    	// Set Position
    	Vetor2D_double offset = new Vetor2D_double( this.getSize().getWidth() / 2 , this.getSize().getHeight() / 2 );
    	Vetor2D_double effectivePosition = new Vetor2D_double( x - offset.x , y - offset.y );
    	
		// Boundaries
		if( effectivePosition.x < 0 ) {
			effectivePosition.x = 0;
		} else if ( effectivePosition.x > 100000 ) {
			effectivePosition.x = 100000;
		}
		
		if( effectivePosition.y < 0 ) {
			effectivePosition.y = 0;
		} else if ( effectivePosition.y > 100000 ) {
			effectivePosition.y = 100000;
		} 
		
		this.cameraPosition.x = effectivePosition.x;
		this.cameraPosition.y = effectivePosition.y;
		
		this.getViewport().setViewPosition( new Point( (int)this.cameraPosition.x , (int)this.cameraPosition.y ));
	}
	
	
	public void addToAnimableList( Animavel anim ) {
		this.animableList.add( anim );
	}
 
	public void hideScrolls() {
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
}
