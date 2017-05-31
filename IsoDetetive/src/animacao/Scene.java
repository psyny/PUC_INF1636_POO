package animacao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

public class Scene extends JLayeredPane implements Animavel {
	public double scale;
	
	protected ArrayList<Actor> toAdd = new ArrayList<Actor>();

	public Scene( int x , int y , int w , int h) {
		this.setLayout( null );
		this.setVisible( true );
		this.scale = 1.0;	
		
		if( w <= 0 ) {
			w = 1;
		}
		
		if( h <= 0 ) {
			h = 1;
		}
		
		this.setBounds(x , y , w , h);
		this.setPreferredSize( new Dimension(w , h) );
	}
	
	public void addActor( Actor actor , int layer ) {
		actor.desiredLayer = layer;
		actor.parent = this;
		
		this.toAdd.add( actor );
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
