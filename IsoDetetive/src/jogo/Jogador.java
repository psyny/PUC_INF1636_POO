package jogo;

public class Jogador {
	private Casa 	posicao;
	private String 	nome;
	private Personagem personagem;
	private boolean	emJogo;
	
	public Jogador( PersonagemEnum personagem ) {
		this( personagem , "<SEM NOME>" );
	}
	
	public Jogador( PersonagemEnum personagem , String nomeJogador  ) {
		this.personagem = PersonagemLista.getInstance().obterPersonagem( personagem );
		
		this.nome 		= nomeJogador;
		this.emJogo 	= false;
	}
	
	
	public Personagem obterPersonagem() {
		return this.personagem;
	}

}
