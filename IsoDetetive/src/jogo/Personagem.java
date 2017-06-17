package jogo;

import estruturas.Vetor2D_int;

public class Personagem {
	protected PersonagemEnum 	personagem;
	protected String 			nome;
	protected Vetor2D_int		casaInicial;
	
	public Personagem( PersonagemEnum per ) {
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
	

		
	public void definirCasaInicial( Vetor2D_int pos ) {
		this.casaInicial.x = pos.x;
		this.casaInicial.y = pos.y;
	}
	
	public Vetor2D_int obterCasaInicial() {
		return this.casaInicial;
	}
	
	public String obterNome() {
		return this.nome;
	}
	
	public PersonagemEnum obterEnum() {
		return this.personagem;
	}
}
