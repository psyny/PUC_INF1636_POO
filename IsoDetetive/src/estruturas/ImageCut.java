package estruturas;

import java.awt.Image;

public class ImageCut {
	public Image image;
	public Vetor2D_double origin;
	public Vetor2D_double size;
	
	public ImageCut( Image img , int originX , int originY , int sizeX , int sizeY ) {
		this.image = img;
		
		origin = 	new Vetor2D_double( originX , originY );
		size 	= 	new Vetor2D_double( sizeX , sizeY );
	}
}
