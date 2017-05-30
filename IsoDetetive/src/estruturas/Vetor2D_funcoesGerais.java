package estruturas;

public abstract class Vetor2D_funcoesGerais {

	public static double getModulus( double x , double y ) {
		return Math.sqrt( ( x * x ) + ( y * y ) );		
	}
	
	public static double getDirectionRAD( double x , double y ) {
		double direction = 0;

		if( x == 0 ) {
			if( y > 0 ) {
				direction = Math.PI * 0.5;
			} else {
				direction = Math.PI * 1.5;
			}		
		} else if ( y == 0 ) {
			if( x > 0 ) {
				direction = 0;
			} else {
				direction = Math.PI;
			}
		} else {
			direction = Math.atan2( y , x );
		}
		
		return direction;
	}
	

}
