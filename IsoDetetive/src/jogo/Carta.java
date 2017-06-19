package jogo;

public class Carta {
	
	public CartaEnum	tipo;

	public Carta(CartaEnum carta)
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
}
