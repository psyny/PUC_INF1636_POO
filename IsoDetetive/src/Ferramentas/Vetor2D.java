package Ferramentas;

public class Vetor2D {
	public double x;
	public double y;
	
	public Vetor2D( double x , double y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vetor2D( Vetor2D oVet ) {
		this.x = oVet.x;
		this.y = oVet.y;
	}
	
}
