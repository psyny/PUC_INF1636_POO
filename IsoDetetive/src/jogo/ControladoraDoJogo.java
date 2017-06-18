package jogo;

import java.util.ArrayList;

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
		baralho = new Baralho();
		crime = baralho.gerarCrime();
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
	
	public ArrayList<Casa> obterMovimentacaoPossivel() {
		return new ArrayList<Casa>( this.movimentacaoPossivel );
	}	
	
	public EstadoDaJogada obterEstadoDaJogada() {
		return this.estadoDaJogada;
	}	
	
	
	public void adicionarJogador( Jogador jog ) {
		this.listaDeJogadores.add( jog );
	}
	
	public void distribuirCartas()
	{
		while(baralho.baralho.size() > 0) {
			for (Jogador jogador : listaDeJogadores) {
				jogador.adicionarMao(baralho.distribuirCarta());
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
			
			while( (listaDeJogadores.get(idxCandidato)).emJogo == false ) {
				idxCandidato++;
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
}
