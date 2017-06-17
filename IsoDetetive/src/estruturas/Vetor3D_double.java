package estruturas;

public class Vetor3D_double {
	public double x;
	public double y;
	public double z;
	
	public Vetor3D_double( ) {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vetor3D_double( double x , double y , double z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vetor3D_double( Vetor3D_double oVet ) {
		this.x = oVet.x;
		this.y = oVet.y;
		this.z = oVet.z;
	}
		
	
	public double getModulus() {
		return Vetor2D_funcoesGerais.getModulus( this.x , this.y , this.z );
	}
	
	public double getAbsoluteValue() {
		return Vetor2D_funcoesGerais.getModulus( this.x , this.y , this.z );
	}
	
	public void normalize() {
		double abs = this.getModulus();
		
		if( abs > 0 ) {
			this.x = this.x / abs;
			this.y = this.y / abs;
			this.z = this.z / abs;
		} else {
			this.x = 0;
			this.y = 0;
			this.z = 0;
		}
	}
	
	public Vetor3D_double getDistanceToVector( Vetor3D_double vectorTo ) {
		return new Vetor3D_double( vectorTo.x - this.x , vectorTo.y - this.y , vectorTo.z - this.z );
	}
	
	public Vetor2D_double getVetor2D() {
		return new Vetor2D_double( this.x , this.y );
	}
}
