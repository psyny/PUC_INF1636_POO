package atores;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import animacao.*;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;

public class CenaTabuleiro extends CenaIsometrica {
	
	class MouseController implements MouseListener
	{
		CenaTabuleiro cena;
		
		public MouseController(CenaTabuleiro cena)
		{
			this.cena = cena;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			Vetor2D_int destino = cena.obterCasaClicada(e.getPoint());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}

	public CenaTabuleiro(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		this.setBackground( new Color(0,0,0) );
		this.setOpaque( true );
		
		this.addMouseListener(new MouseController(this));
	}

	public Vetor2D_int obterCasaClicada(Point p)
	{
		Vetor2D_double vet = Isometria.obterVetorCartesiano(new Vetor2D_double(p.x - this.getIsometricOffset().x, p.y - this.getIsometricOffset().y));
		
		Vetor2D_int casa = new Vetor2D_int(0, 0);
		casa.x = (int)( 42 + vet.x) / 63;
		casa.y = (int)( 42 + vet.y) / 63;
		
		return casa;
	}
}
