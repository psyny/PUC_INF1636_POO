package jogo;

import java.util.ArrayList;

import com.sun.xml.internal.ws.api.Cancelable;

import atores.CameraMenu.Modos;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;

public class ControladoraDoJogo {
	// Singleton com LazyHolder para tratar threads
	private static class LazyHolder {
		static ControladoraDoJogo instance = new ControladoraDoJogo();
		
		private static void resetSingleton() {
			LazyHolder.instance = new ControladoraDoJogo();
		}
	}
	
	public static ControladoraDoJogo getInstance() {
		return LazyHolder.instance;
	}
	
	public static void resetSingleton() {
		LazyHolder.resetSingleton();
	}	
	
	// ----------------------------
	public enum EstadoDaJogada {
		INICIO,
		CONFIRMANDO_MOVIMENTO,
		AGUARDANDO_MOVIMENTO,
		PALPITE,
		ACUSACAO
	}	
	
	protected ArrayList<Jogador> listaDeJogadores;
	
	protected EstadoDaJogada 	estadoDaJogada = EstadoDaJogada.INICIO; 
	protected Jogador 			jogadorDaVez = null;
	protected int				valorDoDado = 0;
	protected Baralho 			baralho;
	protected ArrayList<Casa>	movimentacaoPossivel;
	protected ArrayList<Carta> 	crime;
	
	public Tabuleiro	tabuleiro = null;
	
	private ControladoraDoJogo() {
		listaDeJogadores = new ArrayList<Jogador>();
	}
	
	public ArrayList<Jogador> obterListaDeJogadores() {
		return this.listaDeJogadores;
	}
	
	public Jogador obterJogadorDaVez() {
		return this.jogadorDaVez;
	}
	
	public int obterValorDoUltimoDado() {
		return this.valorDoDado;
	}
	
	public ArrayList<Carta> obterCrime() {
		return this.crime;
	}
	
	public ArrayList<Casa> obterMovimentacaoPossivel() {
		return new ArrayList<Casa>( this.movimentacaoPossivel );
	}	
	
	public EstadoDaJogada obterEstadoDaJogada() {
		return this.estadoDaJogada;
	}	
	
	
	public void adicionarJogador( Jogador jog ) {
		this.listaDeJogadores.add( jog );
	}
	
	public void iniciarPartida()
	{
		baralho = new Baralho();
		crime = baralho.gerarCrime();
		for (Carta carta : crime) {
			System.out.println(carta.tipo);
		}
		distribuirCartas();
	}
	
	public void distribuirCartas()
	{
		while(baralho.baralho.size() > 0) {
			for (Jogador jogador : listaDeJogadores) {
				if(jogador.emJogo)
					jogador.adicionarMao(baralho.distribuirCarta());
				if(baralho.baralho.size() == 0)
					break;
			}
		}
	}

	
	public void iniciarProximaJogada() {
		// Descobrir quem é o proximo jogador
		if( jogadorDaVez == null ) {
			jogadorDaVez = listaDeJogadores.get(0);
		} else {
			int idx = listaDeJogadores.indexOf( jogadorDaVez ); 
			int idxCandidato;
			
			if( idx == listaDeJogadores.size() - 1 ) {
				idxCandidato = 0;
			} else {
				idxCandidato = idx + 1;
			}
			
			while( (listaDeJogadores.get(idxCandidato)).emJogo == false) {
				idxCandidato++;
				if(idxCandidato == listaDeJogadores.size())
					idxCandidato = 0;
			}
			
			jogadorDaVez = listaDeJogadores.get(idxCandidato);
		}
		
		// Resetar variaveis de turno
		valorDoDado = 0;
		movimentacaoPossivel = new ArrayList<Casa>();
		estadoDaJogada = EstadoDaJogada.INICIO; 
	}
	
	public int rolarDadoParaMovimentacao( int valorPredeterminado ) {
		valorDoDado = valorPredeterminado;
		calcularMovimentacaoPossivel();
		return valorDoDado;
	}	
	
	public int rolarDadoParaMovimentacao() {
		int rolado = 1 + (int)(Math.random()*6);
		
		return rolarDadoParaMovimentacao(rolado);
	}
	
	private void calcularMovimentacaoPossivel() {
		ArrayList<Casa> casasPossiveis;
		
		casasPossiveis = tabuleiro.obterCasasNaDistancia( jogadorDaVez , valorDoDado );
		
		// Adiciona a propria posicao atual do jogador
		//casasPossiveis.add( jogadorDaVez.posicao );
		
		movimentacaoPossivel = casasPossiveis;
		
		estadoDaJogada = EstadoDaJogada.AGUARDANDO_MOVIMENTO; 
	}
	
	public void decidindoMovimento() {
		estadoDaJogada = EstadoDaJogada.CONFIRMANDO_MOVIMENTO;
	}
	
	public void validarPalpite(ArrayList<Carta> palpite)
	{
		//iterar sobre jogadores, na ordem da roda
		int idxInicial = listaDeJogadores.indexOf( jogadorDaVez );
		int idxProximo;
		
		
		if( idxInicial == listaDeJogadores.size() - 1 ) {
			idxProximo = 0;
		} else {
			idxProximo = idxInicial + 1;
		}
		
		while(idxProximo != idxInicial)
		{
			Jogador candidato = listaDeJogadores.get(idxProximo);
			if(candidato.temCarta(palpite))
			{
				
				MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.ESCOLHA_CARTA);
				TradutorMenus.getInstance().desenharEscolhaCarta(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaEscolhaCarta, candidato, palpite);
				return;
			}
			
			idxProximo++;
			if( idxProximo == listaDeJogadores.size() ) {
				idxProximo = 0;
			}
		}
	}
	
	public boolean validarAcusacao(ArrayList<Carta> acusacao)
	{
		if(acusacao.containsAll(crime))
		{
			//TODO - end-Game, alguem vençeu
			System.out.println(jogadorDaVez.personagem.nome + " venceu");
			return true;
		}
		else
		{
			jogadorDaVez.emJogo = false;
			MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
			return false;
		}
	}
	
}
