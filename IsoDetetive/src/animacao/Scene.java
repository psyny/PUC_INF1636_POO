package animacao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import estruturas.*;

public class Scene extends JLayeredPane implements Animavel {
	public double scale;
	
	protected ArrayList<Actor> toAdd = new ArrayList<Actor>();

	public Scene( ) {
		this.setLayout( null );
		this.setVisible( true );
		this.scale = 1.0;	
	}
	
	public Scene( int x , int y ) {
		this( x , y , 1 , 1 );
	}
	
	public Scene( int x , int y , int w , int h) {
		this();
		
		if( w <= 0 ) {
			w = 1;
		}
		
		if( h <= 0 ) {
			h = 1;
		}
		
		this.setBounds(x , y , w , h);
	}
	
	public void setBounds( int x , int y , int w , int h ) {
		super.setBounds(x , y , w , h );
		this.setPreferredSize( new Dimension(w , h) );
	}	
	
	public void addActor( Actor actor , int layer ) {
		actor.desiredLayer = layer;
		actor.parentContainer = this;
		actor.parentScene = this;
		
		this.toAdd.add( actor );
	}
	
	// Essa fu��o normalmente ser� chamada por atores
	// Ela pode ser sobreescrita em classes extendidas desta para calculos mais complexos de posicionamento
	// Ex: Isometrico
	public Vetor2D_int getProjection( Vetor2D_double virtualPosition ) {
		return new Vetor2D_int( (int)virtualPosition.x , (int)virtualPosition.y );
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }	
    
    @Override
    public void addNotify() {
        super.addNotify();
    }
    
	@Override
	public void passTime(long time) {
		
    	for( Component comp : this.getComponents() ) {
    		if (comp instanceof Animavel ) {
    			((Animavel) comp).passTime( time );
    		}
    	}
    
    	for( Actor act : this.toAdd ) {
    		
	    		this.add( act );
	    		this.setLayer( act , ((act.getProjectionCenter().y / 20 ) + act.desiredLayer ));
    	}
    	
		this.toAdd.clear();
	}	
}
