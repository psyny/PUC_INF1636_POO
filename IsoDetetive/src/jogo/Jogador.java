package jogo;

import java.util.ArrayList;

public class Jogador {
	
	public class Nota {
		public Carta carta;
		public boolean certeza = false;
		
		public Nota(Carta carta) {
			this.carta = carta;
		}
		
		public Nota(Carta carta, boolean certeza) {
			this.carta = carta;
			this.certeza = certeza;
		}
		
		public void certificarCarta() {
			certeza = true;
		}
	}
	
	protected Casa 	posicao;
	protected String 	nome;
	protected Personagem personagem;
	protected boolean	emJogo;
	protected ArrayList<Carta> mao;
	protected ArrayList<Nota> blocoDeNotas;
	
	public Jogador( PersonagemEnum personagem ) {
		this( personagem , "<SEM NOME>" );
	}
	
	public Jogador( PersonagemEnum personagem , String nomeJogador  ) {
		this.personagem = PersonagemLista.getInstance().obterPersonagem( personagem );
		
		this.nome 		= nomeJogador;
		this.emJogo 	= true;
		this.mao = new ArrayList<Carta>();
		this.blocoDeNotas = new ArrayList<Nota>();
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
	
	public ArrayList<Carta> obterMao()
	{
		return this.mao;
	}
	
	public ArrayList<Nota> obterBlocoDeNotas()
	{
		return this.blocoDeNotas;
	}
	
	public void adicionarMao(Carta carta) {
		mao.add(carta);
		blocoDeNotas.add(new Nota(carta, true));
	}
	
	public void adicionarBlocoDeNotas(Carta carta) {
		blocoDeNotas.add(new Nota(carta));
	}
	
	public void adicionarBlocoDeNotas(Carta carta, boolean certeza) {
		blocoDeNotas.add(new Nota(carta, certeza));
	}
	
	public void certificarNota(Carta carta) {
		for (Nota nota : blocoDeNotas) {
			if(carta.equals(nota.carta))
				nota.certificarCarta();
		}
	}
	
	public boolean temNota(Carta carta) {
		for (Nota nota : blocoDeNotas) {
			if(carta.equals(nota.carta))
				return true;
		}
		return false;
	}
	
	public boolean temNotaCerta(Carta carta) {
		for (Nota nota : blocoDeNotas) {
			if(carta.equals(nota.carta) && nota.certeza)
				return true;
		}
		return false;
	}
	
	public void removerBlocoDeNotas(Nota nota) {
		blocoDeNotas.remove(nota);
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
	
	public ArrayList<Carta> temCartasNaMao(ArrayList<Carta> objetivo)
	{
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		
		for (Carta carta : objetivo) {
			if(mao.contains(carta))
				cartas.add(carta);
		}
		
		return cartas;
	}
	
	public Carta obterCartaPosicao()
	{
		ArrayList<Carta> comodos = Baralho.pilhaComodos;
		
		for (Carta carta : comodos) {
			if(carta.tipo == obterCartaEnumComodo(posicao))
				return carta;
		}
		
		return null;
	}
	
	private CartaEnum obterCartaEnumComodo(Casa casa)
	{
		switch (casa.type) {
		
			case BIBLIOTECA:
				return CartaEnum.BIBLIOTECA;
			case COZINHA:
				return CartaEnum.COZINHA;
			case ENTRADA:
				return CartaEnum.ENTRADA;
			case SL_ESTAR:
				return CartaEnum.SALA_DE_ESTAR;
			case SL_JOGOS:
				return CartaEnum.SALA_DE_JOGOS;
			case SL_INVERNO:
				return CartaEnum.JARDIM_INVERNO;
			case SL_MUSICA:
				return CartaEnum.SALA_DE_MUSICA;
			case ESCRITORIO:
				return CartaEnum.ESCRITORIO;
			case SL_JANTAR:
				return CartaEnum.SALA_DE_JANTAR;
	
			default:
				return null;
		}
	}
	
	public Nota obterNota(Carta carta)
	{
		for (Nota nota : blocoDeNotas) {
			if(carta.equals(nota.carta))
				return nota;
		}
		
		return null;
	}

}
