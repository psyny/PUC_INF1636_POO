package isometrico;

import estruturas.Vetor2D;

public class Transformacoes {
	private static double rotacaoZ;
	private static double rotacaoX;
	private static double escala;
	private static double[][] transMatriz_toIso;
	private static double[][] transMatriz_fromIso;
	private static boolean inicializado = false;

	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus ) {
		Transformacoes.inicializar( rotacaoZ_graus, rotacaoX_graus , 1.0 );
	}
	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus , double escala ) {
		Transformacoes.inicializado = true;
		Transformacoes.escala = escala;
		
		// Estruturas
		double[][] MatA = new double[2][2];
		double[][] MatB = new double[2][2];
		double[][] MatC;
		Transformacoes.transMatriz_toIso = new double[2][2];
		Transformacoes.transMatriz_fromIso = new double[2][2];
		
		// Conversões para radiano 
		Transformacoes.rotacaoZ = Math.PI * rotacaoZ_graus / 180;
		Transformacoes.rotacaoX = Math.PI * rotacaoX_graus / 180;
			
		// Matriz de Transformação A: Rotação em torno de Z ( plano da tela )
		MatA[0][0] = Math.cos( Transformacoes.rotacaoZ );
		MatA[0][1] = Math.sin( Transformacoes.rotacaoZ ) * -1;
		MatA[1][0] = Math.sin( Transformacoes.rotacaoZ );
		MatA[1][1] = Math.cos( Transformacoes.rotacaoZ );
		
		// Matriz de Transformação B: Rotação em torno de X ( escala ) 
		MatB[0][0] = 1;
		MatB[0][1] = 0;
		MatB[1][0] = 0;
		MatB[1][1] = Math.cos( Transformacoes.rotacaoX );
		
		// Matriz C: Composição: MatB * MatA		
		MatC = Transformacoes.transMatriz_toIso;
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
		MatA[1][1] = 1.0 / Math.cos( Transformacoes.rotacaoX );
		
		// Matriz de Transformação B2 ( inversa de A ): Rotação em torno de X ( escala ) 
		MatB[0][0] = Math.cos( Transformacoes.rotacaoZ * -1 );
		MatB[0][1] = Math.sin( Transformacoes.rotacaoZ * -1 ) * -1;
		MatB[1][0] = Math.sin( Transformacoes.rotacaoZ * -1 );
		MatB[1][1] = Math.cos( Transformacoes.rotacaoZ * -1 );
		
		// Matriz C: Composição: MatB * MatA		
		MatC = Transformacoes.transMatriz_fromIso;
		MatC[0][0] = ( MatA[0][0] * MatB[0][0] ) + ( MatA[1][0] * MatB[0][1] );
		MatC[0][1] = ( MatA[0][1] * MatB[0][0] ) + ( MatA[1][1] * MatB[0][1] );
		MatC[1][0] = ( MatA[0][0] * MatB[1][0] ) + ( MatA[1][0] * MatB[1][1] );
		MatC[1][1] = ( MatA[0][1] * MatB[1][0] ) + ( MatA[1][1] * MatB[1][1] );	
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D obterVetorIsometrico( Vetor2D carVet ) {
		Vetor2D isoVet = new Vetor2D( 0 , 0 );
		
		// Multiplicando o vetor recebido pela matriz de transformação calculada
		isoVet.x = ( carVet.x * Transformacoes.transMatriz_toIso[0][0] ) + ( carVet.y * Transformacoes.transMatriz_toIso[0][1] );
		isoVet.y = ( carVet.x * Transformacoes.transMatriz_toIso[1][0] ) + ( carVet.y * Transformacoes.transMatriz_toIso[1][1] );
		
		// Considerando a escala...
		isoVet.x *= Transformacoes.escala;
		isoVet.y *= Transformacoes.escala;
		
		return isoVet;
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D obterVetorIsometrico( double x , double y ) {
		return Transformacoes.obterVetorIsometrico( new Vetor2D( x , y ) );
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D obterVetorCartesiano( Vetor2D isoVet ) {
		Vetor2D carVet = new Vetor2D( 0 , 0 );
		
		// Multiplicando o vetor recebido pela matriz de transformação calculada
		carVet.x = ( isoVet.x * Transformacoes.transMatriz_fromIso[0][0] ) + ( isoVet.y * Transformacoes.transMatriz_fromIso[0][1] );
		carVet.y = ( isoVet.x * Transformacoes.transMatriz_fromIso[1][0] ) + ( isoVet.y * Transformacoes.transMatriz_fromIso[1][1] );
		
		// Considerando a escala...
		carVet.x *= Transformacoes.escala;
		carVet.y *= Transformacoes.escala;
		
		return carVet;
	}
	
	// Obtem caixa delimitadora de um vetor cartesiano transformado para isometrico
	static public Vetor2D obterCaixaDelimitadora( Vetor2D carVet ) {
		// Captura diagonal primeira e secundaria do vetor passado
		Vetor2D carVet_DiagonalPrimaria = carVet;
		Vetor2D carVet_DiagonalSecundaria = new Vetor2D( carVet.x * -1 , carVet.y );
			
		// Calcula suas transformações para o isometrico
		Vetor2D isoVet_A = Transformacoes.obterVetorIsometrico( carVet_DiagonalPrimaria );
		isoVet_A.x = Math.abs( isoVet_A.x );
		isoVet_A.y = Math.abs( isoVet_A.x );
		
		Vetor2D isoVet_B = Transformacoes.obterVetorIsometrico( carVet_DiagonalSecundaria );
		isoVet_B.x = Math.abs( isoVet_B.x );
		isoVet_B.y = Math.abs( isoVet_B.x );
		
		// Obtem maiores componentes entre as duas diagonais e cria um novo vetor
		Vetor2D isoVet = new Vetor2D( 0 , 0 );
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
	static public Vetor2D obterCaixaDelimitadora( double x , double y ) {
		return Transformacoes.obterCaixaDelimitadora( new Vetor2D( x , y ) );
	}
}
