package estruturas;

public class Vetor2D_double {
	public double x;
	public double y;
	
	public Vetor2D_double( double x , double y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vetor2D_double( Vetor2D_double oVet ) {
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
			this.x = this.x / abs;
			this.y = this.y / abs;
		} else {
			this.x = 0;
			this.y = 0;
		}
	}
	
	public Vetor2D_double getDistanceToVector( Vetor2D_double vectorTo ) {
		return new Vetor2D_double( vectorTo.x - this.x , vectorTo.y - this.y );
	}
	
	public double getDirectionRAD() {
		return Vetor2D_funcoesGerais.getDirectionRAD( this.x , this.y );
	}
	
}
