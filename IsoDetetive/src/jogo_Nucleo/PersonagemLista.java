package jogo_Nucleo;

import jogo_TiposEnumerados.PersonagemType;

public class PersonagemLista {
	// Singleton com LazyHolder para tratar threads
	private static class LazyHolder {
		static PersonagemLista instance = new PersonagemLista();
		
		private static void resetSingleton() {
			LazyHolder.instance = new PersonagemLista();
		}
	}
	
	public static PersonagemLista getInstance() {
		return LazyHolder.instance;
	}
	
	public static void resetSingleton() {
		LazyHolder.resetSingleton();
	}	
	
	// ----------------------------
	
	private Personagem[] personagens;
	
	private PersonagemLista( ) {
		this.personagens = new Personagem[6];
		this.personagens[0] = new Personagem( PersonagemType.L );
		this.personagens[1] = new Personagem( PersonagemType.SHERLOCK );
		this.personagens[2] = new Personagem( PersonagemType.CARMEN );
		this.personagens[3] = new Personagem( PersonagemType.PANTERA );
		this.personagens[4] = new Personagem( PersonagemType.EDMORT );
		this.personagens[5] = new Personagem( PersonagemType.BATMAN );	
	}
	

	public Personagem obterPersonagem( PersonagemType personagemEnum ) {
		for( Personagem per : this.personagens ) {
			if( per.obterEnum() == personagemEnum ) {
				return per;
			}
		}
		
		return null;
	}
}
