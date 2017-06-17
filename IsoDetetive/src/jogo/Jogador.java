package jogo;

public class Jogador {
	protected Casa 	posicao;
	protected String 	nome;
	protected Personagem personagem;
	protected boolean	emJogo;
	
	public Jogador( PersonagemEnum personagem ) {
		this( personagem , "<SEM NOME>" );
	}
	
	public Jogador( PersonagemEnum personagem , String nomeJogador  ) {
		this.personagem = PersonagemLista.getInstance().obterPersonagem( personagem );
		
		this.nome 		= nomeJogador;
		this.emJogo 	= true;
	}
	
	public void definirPosicao( Casa casa ) {
		posicao = casa;
	}
	
	public Casa obterPosicao() {
		return posicao;
	}
	
	public Personagem obterPersonagem() {
		return this.personagem;
	}

}
