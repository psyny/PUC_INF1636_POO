package jogo;

import estruturas.Vetor2D_int;

public class Personagem {
	private PersonagemEnum 	personagem;
	private String 			nome;
	private Vetor2D_int		casaInicial;
	
	private static Personagem[] personagens;
	
	private Personagem( PersonagemEnum per ) {
		this.personagem = per;
		this.casaInicial = new Vetor2D_int(0,0);
		
		switch( per ) {
			case L:
				this.nome = "L";
				break;
				
			case SHERLOCK:
				this.nome = "Sherlock Holmes";
				break;
				
			case CARMEN:
				this.nome = "Carmen San Diego";
				break;
				
			case PANTERA:
				this.nome = "Pantera Cor-de-Rosa";
				break;
			
			case EDMORT:
				this.nome = "Ed Mort";
				break;
			
			case BATMAN:
				this.nome = "Batman";
				break;
		}
	}
	
	
	{
		Personagem.personagens = new Personagem[6];
		Personagem.personagens[0] = new Personagem( PersonagemEnum.L );
		Personagem.personagens[1] = new Personagem( PersonagemEnum.SHERLOCK );
		Personagem.personagens[2] = new Personagem( PersonagemEnum.CARMEN );
		Personagem.personagens[3] = new Personagem( PersonagemEnum.PANTERA );
		Personagem.personagens[4] = new Personagem( PersonagemEnum.EDMORT );
		Personagem.personagens[5] = new Personagem( PersonagemEnum.BATMAN );			
	}
	
	static Personagem obterPersonagem( PersonagemEnum personagemEnum ) {
		for( Personagem per : Personagem.personagens ) {
			if( per.personagem == personagemEnum ) {
				return per;
			}
		}
		
		return null;
	}
	
	static void definirCasaInicial( PersonagemEnum personagemEnum , Vetor2D_int pos ) {
		Personagem per = Personagem.obterPersonagem( personagemEnum );
		per.casaInicial.x = pos.x;
		per.casaInicial.y = pos.y;
	}
}
