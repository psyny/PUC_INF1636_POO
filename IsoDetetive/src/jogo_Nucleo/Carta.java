package jogo_Nucleo;

import jogo_TiposEnumerados.CartaType;
import jogo_TiposEnumerados.CasaType;
import jogo_TiposEnumerados.PersonagemType;

public class Carta {
	
	public CartaType	tipo;

	public Carta(CartaType carta)
	{
		this.tipo = carta;
	}
	
	public boolean isArma()
	{
		for (Carta carta : Baralho.pilhaArmas) {
			if(carta.equals(this))
				return true;
		}
		return false;
	}
	
	public boolean isComodo()
	{
		for (Carta carta : Baralho.pilhaComodos) {
			if(carta.equals(this))
				return true;
		}
		return false;
	}
	
	public boolean isSuspeito()
	{
		for (Carta carta : Baralho.pilhaSuspeitos) {
			if(carta.equals(this))
				return true;
		}
		return false;
	}
	
	public static CartaType tipoCasaParaTipoCarta( CasaType casaType ) {
		switch (casaType) {	
			case BIBLIOTECA:
				return CartaType.BIBLIOTECA;
			case COZINHA:
				return CartaType.COZINHA;
			case ENTRADA:
				return CartaType.ENTRADA;
			case SL_ESTAR:
				return CartaType.SALA_DE_ESTAR;
			case SL_JOGOS:
				return CartaType.SALA_DE_JOGOS;
			case SL_INVERNO:
				return CartaType.JARDIM_INVERNO;
			case SL_MUSICA:
				return CartaType.SALA_DE_MUSICA;
			case ESCRITORIO:
				return CartaType.ESCRITORIO;
			case SL_JANTAR:
				return CartaType.SALA_DE_JANTAR;
	
			default:
				return null;
		}
	}
	
	public static CasaType tipoCartaParaTipoCasa(CartaType cartaTipo)
	{
		switch (cartaTipo) {
			case ENTRADA:
				return CasaType.ENTRADA;
			case BIBLIOTECA:
				return CasaType.BIBLIOTECA;
			case SALA_DE_ESTAR:
				return CasaType.SL_ESTAR;
			case SALA_DE_JANTAR:
				return CasaType.SL_JANTAR;
			case SALA_DE_JOGOS:
				return CasaType.SL_JOGOS;
			case SALA_DE_MUSICA:
				return CasaType.SL_MUSICA;
			case COZINHA:
				return CasaType.COZINHA;
			case JARDIM_INVERNO:
				return CasaType.SL_INVERNO;
			case ESCRITORIO:
				return CasaType.ESCRITORIO;
				
			default:
				return null;
		}
	}
	
	public static PersonagemType tipoCartaParaPersonagemEnum(CartaType cartaTipo)
	{
		switch (cartaTipo) {
			case L:
				return PersonagemType.L;
			case SHERLOCK:
				return PersonagemType.SHERLOCK;
			case CARMEN:
				return PersonagemType.CARMEN;
			case PANTERA:
				return PersonagemType.PANTERA;
			case EDMORT:
				return PersonagemType.EDMORT;
			case BATMAN:
				return PersonagemType.BATMAN;				
				
			default:
				return null;
		}
	}	
}
