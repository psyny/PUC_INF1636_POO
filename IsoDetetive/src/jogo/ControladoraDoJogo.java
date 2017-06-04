package jogo;

import java.util.ArrayList;

public class ControladoraDoJogo {
	private static ControladoraDoJogo singleton;
	
	private ArrayList<Jogador> listaDeJogadores;
	
	private ControladoraDoJogo() {
		
	}
	
	public static void inicializar() {
		ControladoraDoJogo.singleton = new ControladoraDoJogo();
		ControladoraDoJogo.singleton.listaDeJogadores = new ArrayList<Jogador>();
	}
	
	public static ControladoraDoJogo obterInstancia() {
		return ControladoraDoJogo.singleton;
	}
	
}
