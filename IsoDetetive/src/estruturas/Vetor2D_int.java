package estruturas;

public class Vetor2D_int {
	public int x;
	public int y;
	
	public Vetor2D_int( int x , int y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vetor2D_int( Vetor2D_int oVet ) {
		this.x = oVet.x;
		this.y = oVet.y;
	}
	
	public double getModulus() {
		return Vetor2D_funcoesGerais.getModulus( this.x , this.y );
	}
	
	public double getAbsoluteValue() {
		return Vetor2D_funcoesGerais.getModulus( this.x , this.y );
	}
	
	public void normalize() {
		double abs = this.getModulus();
		
		if( abs > 0 ) {
			this.x = (int)(this.x / abs);
			this.y = (int)(this.y / abs);
		} else {
			this.x = 0;
			this.y = 0;
		}
	}
	
	public Vetor2D_int getDistanceToVector( Vetor2D_int vectorTo ) {
		return new Vetor2D_int( vectorTo.x - this.x , vectorTo.y - this.y );
	}
	
	public double getDirectionRAD() {
		return Vetor2D_funcoesGerais.getDirectionRAD( this.x , this.y );
	}
	
}
