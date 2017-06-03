package jogo;

import java.util.ArrayList;

import estruturas.*;

public class Casa {

	// Apenas para Consulta
	public Vetor2D_int 	position;//Posição da Casa
	public CasaType 	type;//Tipo da Casa

	//Construtor de Cada
	public Casa( int x , int y ) {
		this.position = new Vetor2D_int( x , y );
		this.type = CasaType.CORREDOR;
	}
	

	//Checa se duas Casa são as mesmas, retorna trua casa sim, false caso não
	public boolean isSameOf( Casa cell ) {
		if( this.position.x == cell.position.x && this.position.y == cell.position.y ) {
			return true;
		} else {
			return false;
		}
	}
	
	//Retorna a distancia de manhattan entre duas Casa
	public int getManDistTo( Casa cellTo ) {
		int dist;
		dist = Math.abs( this.position.x - cellTo.position.x ) + Math.abs( this.position.y + cellTo.position.y );
		return dist;
	}
}
