package estruturas;

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
	
	public double getModulus() {
		return Math.sqrt( ( this.x * this.x ) + ( this.y * this.y ) );
	}
	
	public double getAbsoluteValue() {
		return this.getModulus();
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
	
	public Vetor2D getDistanceToVector( Vetor2D vectorTo ) {
		return new Vetor2D( vectorTo.x - this.x , vectorTo.y - this.y );
	}
	
	public double getDirectionRAD() {
		double direction = 0;

		if( this.x == 0 ) {
			if( this.y > 0 ) {
				direction = Math.PI * 0.5;
			} else {
				direction = Math.PI * 1.5;
			}		
		} else if ( this.y == 0 ) {
			if( this.x > 0 ) {
				direction = 0;
			} else {
				direction = Math.PI;
			}
		} else {
			direction = Math.atan2( this.y , this.x );
		}
		
		return direction;
	}
	
}
