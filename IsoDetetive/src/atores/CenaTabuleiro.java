package atores;

import java.awt.Color;

import animacao.*;

public class CenaTabuleiro extends CenaIsometrica {

	public CenaTabuleiro(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		this.setBackground( new Color(0,0,0) );
		this.setOpaque( true );
	}


}
