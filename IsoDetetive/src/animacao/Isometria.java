package animacao;

import estruturas.Vetor2D_double;

public class Isometria {
	private static double rotacaoZ;
	private static double rotacaoX;
	private static double escala;
	private static double[][] transMatriz_toIso;
	private static double[][] transMatriz_fromIso;
	private static boolean inicializado = false;
	
	{
		Isometria.inicializar(0, 0);
	}

	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus ) {
		Isometria.inicializar( rotacaoZ_graus, rotacaoX_graus , 1.0 );
	}
	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus , double escala ) {
		Isometria.inicializado = true;
		Isometria.escala = escala;
		
		// Estruturas
		double[][] MatA = new double[2][2];
		double[][] MatB = new double[2][2];
		double[][] MatC;
		Isometria.transMatriz_toIso = new double[2][2];
		Isometria.transMatriz_fromIso = new double[2][2];
		
		// Conversões para radiano 
		Isometria.rotacaoZ = Math.PI * rotacaoZ_graus / 180;
		Isometria.rotacaoX = Math.PI * rotacaoX_graus / 180;
			
		// Matriz de Transformação A: Rotação em torno de Z ( plano da tela )
		MatA[0][0] = Math.cos( Isometria.rotacaoZ );
		MatA[0][1] = Math.sin( Isometria.rotacaoZ ) * -1;
		MatA[1][0] = Math.sin( Isometria.rotacaoZ );
		MatA[1][1] = Math.cos( Isometria.rotacaoZ );
		
		// Matriz de Transformação B: Rotação em torno de X ( escala ) 
		MatB[0][0] = 1;
		MatB[0][1] = 0;
		MatB[1][0] = 0;
		MatB[1][1] = Math.cos( Isometria.rotacaoX );
		
		// Matriz C: Composição: MatB * MatA		
		MatC = Isometria.transMatriz_toIso;
		MatC[0][0] = ( MatA[0][0] * MatB[0][0] ) + ( MatA[1][0] * MatB[0][1] );
		MatC[0][1] = ( MatA[0][1] * MatB[0][0] ) + ( MatA[1][1] * MatB[0][1] );
		MatC[1][0] = ( MatA[0][0] * MatB[1][0] ) + ( MatA[1][0] * MatB[1][1] );
		MatC[1][1] = ( MatA[0][1] * MatB[1][0] ) + ( MatA[1][1] * MatB[1][1] );	
	
		
		// --------------------------------------------------------------------------
		// --------------------------------------------------------------------------
		
		// Matriz de Transformação A2 ( inversa de B ): Rotação em torno de X ( escala ) 
		MatA[0][0] = 1;
		MatA[0][1] = 0;
		MatA[1][0] = 0;
		MatA[1][1] = 1.0 / Math.cos( Isometria.rotacaoX );
		
		// Matriz de Transformação B2 ( inversa de A ): Rotação em torno de X ( escala ) 
		MatB[0][0] = Math.cos( Isometria.rotacaoZ * -1 );
		MatB[0][1] = Math.sin( Isometria.rotacaoZ * -1 ) * -1;
		MatB[1][0] = Math.sin( Isometria.rotacaoZ * -1 );
		MatB[1][1] = Math.cos( Isometria.rotacaoZ * -1 );
		
		// Matriz C: Composição: MatB * MatA		
		MatC = Isometria.transMatriz_fromIso;
		MatC[0][0] = ( MatA[0][0] * MatB[0][0] ) + ( MatA[1][0] * MatB[0][1] );
		MatC[0][1] = ( MatA[0][1] * MatB[0][0] ) + ( MatA[1][1] * MatB[0][1] );
		MatC[1][0] = ( MatA[0][0] * MatB[1][0] ) + ( MatA[1][0] * MatB[1][1] );
		MatC[1][1] = ( MatA[0][1] * MatB[1][0] ) + ( MatA[1][1] * MatB[1][1] );	
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D_double obterVetorIsometrico( Vetor2D_double carVet ) {
		Vetor2D_double isoVet = new Vetor2D_double( 0 , 0 );
		
		// Multiplicando o vetor recebido pela matriz de transformação calculada
		isoVet.x = ( carVet.x * Isometria.transMatriz_toIso[0][0] ) + ( carVet.y * Isometria.transMatriz_toIso[0][1] );
		isoVet.y = ( carVet.x * Isometria.transMatriz_toIso[1][0] ) + ( carVet.y * Isometria.transMatriz_toIso[1][1] );
		
		// Considerando a escala...
		isoVet.x *= Isometria.escala;
		isoVet.y *= Isometria.escala;
		
		return isoVet;
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D_double obterVetorIsometrico( double x , double y ) {
		return Isometria.obterVetorIsometrico( new Vetor2D_double( x , y ) );
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D_double obterVetorCartesiano( Vetor2D_double isoVet ) {
		Vetor2D_double carVet = new Vetor2D_double( 0 , 0 );
		
		// Multiplicando o vetor recebido pela matriz de transformação calculada
		carVet.x = ( isoVet.x * Isometria.transMatriz_fromIso[0][0] ) + ( isoVet.y * Isometria.transMatriz_fromIso[0][1] );
		carVet.y = ( isoVet.x * Isometria.transMatriz_fromIso[1][0] ) + ( isoVet.y * Isometria.transMatriz_fromIso[1][1] );
		
		// Considerando a escala...
		carVet.x *= Isometria.escala;
		carVet.y *= Isometria.escala;
		
		return carVet;
	}
	
	// Obtem caixa delimitadora de um vetor cartesiano transformado para isometrico
	static public Vetor2D_double obterCaixaDelimitadora( Vetor2D_double carVet ) {
		// Captura diagonal primeira e secundaria do vetor passado
		Vetor2D_double carVet_DiagonalPrimaria = carVet;
		Vetor2D_double carVet_DiagonalSecundaria = new Vetor2D_double( carVet.x * -1 , carVet.y );
			
		// Calcula suas transformações para o isometrico
		Vetor2D_double isoVet_A = Isometria.obterVetorIsometrico( carVet_DiagonalPrimaria );
		isoVet_A.x = Math.abs( isoVet_A.x );
		isoVet_A.y = Math.abs( isoVet_A.x );
		
		Vetor2D_double isoVet_B = Isometria.obterVetorIsometrico( carVet_DiagonalSecundaria );
		isoVet_B.x = Math.abs( isoVet_B.x );
		isoVet_B.y = Math.abs( isoVet_B.x );
		
		// Obtem maiores componentes entre as duas diagonais e cria um novo vetor
		Vetor2D_double isoVet = new Vetor2D_double( 0 , 0 );
		if( isoVet_A.x > isoVet_B.x ) {
			isoVet.x = isoVet_A.x;
		}
		else {
			isoVet.x = isoVet_B.x;
		}
		
		if( isoVet_A.y > isoVet_B.y ) {
			isoVet.y = isoVet_A.y;
		}
		else {
			isoVet.y = isoVet_B.y;
		}
		
		return isoVet;
	}
	
	// Obtem caixa delimitadora de um vetor cartesiano transformado para isometrico
	static public Vetor2D_double obterCaixaDelimitadora( double x , double y ) {
		return Isometria.obterCaixaDelimitadora( new Vetor2D_double( x , y ) );
	}
}
