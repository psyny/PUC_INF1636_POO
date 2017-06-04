package jogo;

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
		this.personagens[0] = new Personagem( PersonagemEnum.L );
		this.personagens[1] = new Personagem( PersonagemEnum.SHERLOCK );
		this.personagens[2] = new Personagem( PersonagemEnum.CARMEN );
		this.personagens[3] = new Personagem( PersonagemEnum.PANTERA );
		this.personagens[4] = new Personagem( PersonagemEnum.EDMORT );
		this.personagens[5] = new Personagem( PersonagemEnum.BATMAN );	
	}
	

	public Personagem obterPersonagem( PersonagemEnum personagemEnum ) {
		for( Personagem per : this.personagens ) {
			if( per.obterEnum() == personagemEnum ) {
				return per;
			}
		}
		
		return null;
	}
}
