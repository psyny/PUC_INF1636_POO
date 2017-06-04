package animacao;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

/*
Cena composta por outras cenas

Características:
-Ajusta seu proprio tamanho para acomodar outras cenas.

*/

public class CenaComposta extends JLayeredPane implements Animavel {
	protected ArrayList<Scene> cenas = new ArrayList<Scene>();
	
	public CenaComposta( int x , int y ) {
		this.setLayout( null );
		this.setVisible( true );
		
		this.setBounds(x , y , 1 , 1 );
	}
	
	public void setBounds( int x , int y , int w , int h ) {
		super.setBounds(x , y , w , h );
		this.setPreferredSize( new Dimension(w , h) );
	}
	
	public void adicionarCena( Scene cena , int layer ) {
		// Recalculando novo tamanho
		double novaCenaMaxX = cena.getBounds().getX() + cena.getBounds().getWidth();
		double novaCenaMaxY = cena.getBounds().getY() + cena.getBounds().getHeight();
		
		int novoW = this.getWidth();
		int novoH = this.getHeight();
		
		if( novaCenaMaxX > this.getWidth() ) {
			novoW = (int)novaCenaMaxX;
		}
		
		if( novaCenaMaxY > this.getHeight() ) {
			novoH = (int)novaCenaMaxY;
		}
		
		this.setBounds( this.getX() , this.getY() , novoW , novoH );
		
		// Adicionando nova cena
		this.cenas.add( cena );
		this.add( cena );
		this.setLayer( cena , layer );
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
	}	
}
