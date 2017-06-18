package jogo;

import java.util.ArrayList;

public class Palpite {
	public Carta comodo;
	public Carta arma;
	public Carta suspeito;
	
	public Palpite(Carta comodo, Carta arma, Carta suspeito)
	{
		this.comodo = comodo;
		this.arma = arma;
		this.suspeito = suspeito;
	}
	
	public ArrayList<Carta> obterListaCartas()
	{
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		
		cartas.add(comodo);
		cartas.add(arma);
		cartas.add(suspeito);
		
		return cartas;
	}
}
