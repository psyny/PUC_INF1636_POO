package jogo_Nucleo;

import estruturas.Vetor2D_int;
import jogo_TiposEnumerados.PersonagemType;

public class Personagem {
	protected PersonagemType 	personagem;
	protected String 			nome;
	protected Casa				casaInicial;
	
	public Personagem( PersonagemType per ) {
		this.personagem = per;
		this.casaInicial = null;
		
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
	

		
	public void definirCasaInicial( Casa casa ) {
		casaInicial = casa;
	}
	
	public Casa obterCasaInicial() {
		return this.casaInicial;
	}
	
	public String obterNome() {
		return this.nome;
	}
	
	public PersonagemType obterEnum() {
		return this.personagem;
	}
}
