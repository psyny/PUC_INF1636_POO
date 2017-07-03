package jogo;

import java.util.ArrayList;

import observers.Observed_JogadorReposicionado;
import observers.Observer_JogadorReposicionado;

public class Jogador implements Observed_JogadorReposicionado  {
	
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
	
	protected Casa 			posicao;
	protected String 		nome;
	protected Personagem 	personagem;
	protected boolean		emJogo;
	public boolean 			moveuSeForcadamente = false;
	protected Agente		inteligenciaArtificial = null;
	protected ArrayList<Carta> 	mao;
	protected ArrayList<Nota> 	blocoDeNotas;
	
	private ArrayList<Observer_JogadorReposicionado> jogadorReposicionadoObserverList = new ArrayList<Observer_JogadorReposicionado>();
	
	public Jogador( PersonagemEnum personagem, boolean inteligenciaArtificial ) {
		this( personagem , "<SEM NOME>" , inteligenciaArtificial);
	}
	
	public Jogador( PersonagemEnum personagem , String nomeJogador, boolean inteligenciaArtificial  ) {
		this.personagem = PersonagemLista.getInstance().obterPersonagem( personagem );
		
		this.nome 		= nomeJogador;
		this.emJogo 	= true;
		this.mao = new ArrayList<Carta>();
		this.blocoDeNotas = new ArrayList<Nota>();
		if(inteligenciaArtificial)
			this.inteligenciaArtificial = new Agente(this);
		else
			this.inteligenciaArtificial = null;
	}
	
	public void definirPosicao( Casa casa ) {
		posicao = casa;
		
		for( Observer_JogadorReposicionado observer : this.jogadorReposicionadoObserverList ) {
			observer.ObserverNotify_JogadorReposicionado( this.personagem.personagem , casa.position );
		}
	}
	
	public Casa obterPosicao() {
		return posicao;
	}
	
	public Personagem obterPersonagem() {
		return this.personagem;
	}
	
	public Agente obeterInteligenciaArtificial(){
		return inteligenciaArtificial;
	}
	
	public ArrayList<Carta> obterMao()
	{
		return this.mao;
	}
	
	public ArrayList<Nota> obterBlocoDeNotas()
	{
		return this.blocoDeNotas;
	}
	
	public void adicionarCartaAMao(Carta carta) {
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
	
	public boolean temNotaManual(Carta carta) {
		for (Nota nota : blocoDeNotas) {
			if( carta.equals(nota.carta) && nota.certeza == false )
				return true;
		}
		return false;
	}	
	
	public boolean temNotaCerteza(Carta carta) {
		for (Nota nota : blocoDeNotas) {
			if( carta.equals(nota.carta) && nota.certeza == true )
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
		
		CasaType comodoAtual = posicao.type;
		CartaType cartaTipoComodoAtual = Carta.tipoCasaParaTipoCarta( comodoAtual );
		
		for (Carta carta : comodos) {
			if(carta.tipo == cartaTipoComodoAtual) {
				return carta;
			}
		}
		
		return null;
	}
	

	
	public Nota obterNota(Carta carta)
	{
		for (Nota nota : blocoDeNotas) {
			if(carta.equals(nota.carta))
				return nota;
		}
		
		return null;
	}

	@Override
	public void register_JogadorReposicionadoObserved(Observer_JogadorReposicionado observer) {
		jogadorReposicionadoObserverList.add( observer );
	}

	@Override
	public void unRegister_JogadorReposicionadoObserved(Observer_JogadorReposicionado observer) {
		jogadorReposicionadoObserverList.remove( observer );
	}

}
