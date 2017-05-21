package Isometrico;

import Ferramentas.Vetor2D;

public class Ferramentas {
	private static double rotacaoZ;
	private static double rotacaoX;
	private static double escala;
	private static double[][] transMatriz;
	private static boolean inicializado = false;

	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus ) {
		Ferramentas.inicializar( rotacaoZ_graus, rotacaoX_graus , 1.0 );
	}
	
	static public void inicializar( double rotacaoZ_graus , double rotacaoX_graus , double escala ) {
		Ferramentas.inicializado = true;
		Ferramentas.escala = escala;
		
		// Conversões para radiano 
		Ferramentas.rotacaoZ = Math.PI * rotacaoZ_graus / 180;
		Ferramentas.rotacaoX = Math.PI * rotacaoX_graus / 180;
		
		
		// Matriz de Transformação A: Rotação em torno de Z ( plano da tela )
		double[][] MatA = new double[2][2];
		MatA[0][0] = Math.cos( Ferramentas.rotacaoZ );
		MatA[0][1] = Math.sin( Ferramentas.rotacaoZ ) * -1;
		MatA[1][0] = Math.sin( Ferramentas.rotacaoZ );
		MatA[1][1] = Math.cos( Ferramentas.rotacaoZ );
		
		// Matriz de Transformação B: Rotação em torno de X ( escala ) 
		double[][] MatB = new double[2][2];
		MatB[0][0] = 1;
		MatB[0][1] = 0;
		MatB[1][0] = Math.cos( Ferramentas.rotacaoX );
		MatB[1][1] = 0;
		
		// Matriz C: Composição: MatB * MatA
		Ferramentas.transMatriz = new double[2][2];
		double[][] MatC = Ferramentas.transMatriz;
		MatC[0][0] = ( MatA[0][0] * MatB[0][0] ) + ( MatA[1][0] * MatB[0][1] );
		MatC[0][1] = ( MatA[0][1] * MatB[0][0] ) + ( MatA[1][1] * MatB[0][1] );
		MatC[1][0] = ( MatA[0][0] * MatB[1][0] ) + ( MatA[1][0] * MatB[1][1] );
		MatC[1][1] = ( MatA[0][1] * MatB[1][0] ) + ( MatA[1][1] * MatB[1][1] );	
	}
	
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D obterVetorTransformado( Vetor2D carVet ) {
		Vetor2D isoVet = new Vetor2D( 0 , 0 );
		
		// Multiplicando o vetor recebido pela matriz de transformação calculada
		isoVet.x = ( carVet.x * Ferramentas.transMatriz[0][0] ) + ( carVet.y * Ferramentas.transMatriz[0][1] );
		isoVet.y = ( carVet.x * Ferramentas.transMatriz[1][0] ) + ( carVet.y * Ferramentas.transMatriz[1][1] );
		
		// Considerando a escala...
		isoVet.x *= Ferramentas.escala;
		isoVet.y *= Ferramentas.escala;
		
		return isoVet;
	}
	
	// Transforma a posição cartesiana na posição isometrica equivalente
	static public Vetor2D transformarVetor( double x , double y ) {
		return Ferramentas.obterVetorTransformado( new Vetor2D( x , y ) );
	}
	
	// Obtem caixa delimitadora de um vetor cartesiano transformado para isometrico
	static public Vetor2D obterCaixaDelimitadora( Vetor2D carVet ) {
		// Captura diagonal primeira e secundaria do vetor passado
		Vetor2D carVet_DiagonalPrimaria = carVet;
		Vetor2D carVet_DiagonalSecundaria = new Vetor2D( carVet.x * -1 , carVet.y );
			
		// Calcula suas transformações para o isometrico
		Vetor2D isoVet_A = Ferramentas.obterVetorTransformado( carVet_DiagonalPrimaria );
		isoVet_A.x = Math.abs( isoVet_A.x );
		isoVet_A.y = Math.abs( isoVet_A.x );
		
		Vetor2D isoVet_B = Ferramentas.obterVetorTransformado( carVet_DiagonalSecundaria );
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
		return Ferramentas.obterCaixaDelimitadora( new Vetor2D( x , y ) );
	}
}
