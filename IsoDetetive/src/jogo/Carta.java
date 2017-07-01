package jogo;

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
}
