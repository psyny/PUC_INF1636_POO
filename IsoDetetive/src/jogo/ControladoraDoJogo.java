package jogo;

import java.util.ArrayList;

public class ControladoraDoJogo {
	// Singleton com LazyHolder para tratar threads
	private static class LazyHolder {
		static ControladoraDoJogo instance = new ControladoraDoJogo();
		
		private static void resetSingleton() {
			LazyHolder.instance = new ControladoraDoJogo();
		}
	}
	
	public static ControladoraDoJogo getInstance() {
		return LazyHolder.instance;
	}
	
	public static void resetSingleton() {
		LazyHolder.resetSingleton();
	}	
	
	// ----------------------------
	
	private ArrayList<Jogador> listaDeJogadores;
	
	private ControladoraDoJogo() {
		listaDeJogadores = new ArrayList<Jogador>();
	}
	
	public void adicionarJogador( Jogador jog ) {
		this.listaDeJogadores.add( jog );
	}
	
	public ArrayList<Jogador> obterListaDeJogadores() {
		return this.listaDeJogadores;
	}
}
