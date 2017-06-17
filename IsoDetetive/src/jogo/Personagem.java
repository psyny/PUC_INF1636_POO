package jogo;

import estruturas.Vetor2D_int;

public class Personagem {
	protected PersonagemEnum 	personagem;
	protected String 			nome;
	protected Casa				casaInicial;
	
	public Personagem( PersonagemEnum per ) {
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
	
	public PersonagemEnum obterEnum() {
		return this.personagem;
	}
}
