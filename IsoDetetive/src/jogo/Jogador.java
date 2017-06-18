package jogo;

import java.util.ArrayList;

public class Jogador {
	protected Casa 	posicao;
	protected String 	nome;
	protected Personagem personagem;
	protected boolean	emJogo;
	protected ArrayList<Carta> mao;
	protected ArrayList<Carta> blocoDeNotas;
	
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
	
	public void adicionarMao(Carta carta) {
		mao.add(carta);
		blocoDeNotas.add(carta);
	}
	
	public void adicionarBlocoDeNotas(Carta carta) {
		blocoDeNotas.add(carta);
	}
	
	public void removerBlocoDeNotas(Carta carta) {
		blocoDeNotas.remove(carta);
	}
	
	public boolean temCarta(Carta objetivo)
	{
		if(mao.contains(objetivo))
			return true;
		
		return false;
	}

	public boolean temCarta(ArrayList<Carta> objetivo)
	{
		for (Carta carta : objetivo) {
			if(mao.contains(carta))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<Carta> cartasNaMao(ArrayList<Carta> objetivo)
	{
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		
		for (Carta carta : objetivo) {
			if(mao.contains(carta))
				cartas.add(carta);
		}
		
		return cartas;
	}

}
