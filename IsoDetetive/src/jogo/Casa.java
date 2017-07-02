package jogo;

import java.util.ArrayList;

import estruturas.*;

public class Casa {
	// Apenas para Consulta
	public Vetor2D_int 	position;//Posição da Casa
	public CasaType 	type;//Tipo da Casa
	public Casa			casaAnterior;//Casa Anterior
	
	protected boolean		ehAndavel = false;
	protected boolean		ehBloqueado = true;
	
	//Construtor de Cada
	public Casa( int x , int y ) {
		this.position = new Vetor2D_int( x , y );
		this.type = CasaType.CORREDOR;
		this.casaAnterior = null;
		
		this.ehAndavel = false;
		this.ehBloqueado = true;
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
	
	
	public boolean isRoom() {
		switch( type ) {
			default:
				return false;
				
			case COZINHA:
			case SL_MUSICA:
			case SL_INVERNO:
			case SL_JANTAR:
			case SL_JOGOS:
			case SL_ESTAR:
			case ENTRADA:
			case BIBLIOTECA:
			case ESCRITORIO:
				return true;
		}
	}
	
	public boolean isDoor() {
		switch( type ) {
		default:
			return false;
			
		case COZINHA_PORTA:
		case SL_MUSICA_PORTA:
		case SL_INVERNO_PORTA:
		case SL_JANTAR_PORTA:
		case SL_JOGOS_PORTA:
		case SL_ESTAR_PORTA:
		case ENTRADA_PORTA:
		case BIBLIOTECA_PORTA:
		case ESCRITORIO_PORTA:
			return true;
		}
	}
	
	public boolean isStartPosition() {
		switch( type ) {
		default:
			return false;
			
		case INICIO_L:
		case INICIO_SHERLOCK:
		case INICIO_CARMEN:
		case INICIO_PANTERA:
		case INICIO_EDMORT:
		case INICIO_BATMAN:
			return true;
		}
	}	
	
	public boolean isWalkable() {
		if( this.isBlocked() == true ) {
			return false;
		} else {
			return this.ehAndavel;
		}
	}
	
	public boolean isBlocked() {
		return this.ehBloqueado;
	}
	
	public void definirBloqueado( boolean flag ) {
		this.ehBloqueado = flag;
	}


	
	public static CasaType tipoPortaParaTipoSala( CasaType tipoPorta ) {
		switch( tipoPorta ) {
			case COZINHA_PORTA:
				return CasaType.COZINHA;
				
			case SL_MUSICA_PORTA:
				return CasaType.SL_MUSICA;
				
			case SL_INVERNO_PORTA:
				return CasaType.SL_INVERNO;
				
			case SL_JANTAR_PORTA:
				return CasaType.SL_JANTAR;
				
			case SL_JOGOS_PORTA:
				return CasaType.SL_JOGOS;
				
			case SL_ESTAR_PORTA:
				return CasaType.SL_ESTAR;
				
			case ENTRADA_PORTA:
				return CasaType.ENTRADA;
				
			case BIBLIOTECA_PORTA:
				return CasaType.BIBLIOTECA;
				
			case ESCRITORIO_PORTA:
				return CasaType.ESCRITORIO;
		
			default:
				return tipoPorta;
		}
	}
	
	public static CasaType tipoSalaParaTipoPorta( CasaType tipoSala ) {
		switch( tipoSala ) {
			case COZINHA:
				return CasaType.COZINHA_PORTA;
				
			case SL_MUSICA:
				return CasaType.SL_MUSICA_PORTA;
				
			case SL_INVERNO:
				return CasaType.SL_INVERNO_PORTA;
				
			case SL_JANTAR:
				return CasaType.SL_JANTAR_PORTA;
				
			case SL_JOGOS:
				return CasaType.SL_JOGOS_PORTA;
				
			case SL_ESTAR:
				return CasaType.SL_ESTAR_PORTA;
				
			case ENTRADA:
				return CasaType.ENTRADA_PORTA;
				
			case BIBLIOTECA:
				return CasaType.BIBLIOTECA_PORTA;
				
			case ESCRITORIO:
				return CasaType.ESCRITORIO_PORTA;
		
			default:
				return tipoSala;
		}
	}
}
