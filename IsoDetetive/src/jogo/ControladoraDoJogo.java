package jogo;

import java.io.File;
import java.util.ArrayList;

import com.sun.xml.internal.ws.api.Cancelable;

import atores.CameraMenu.Modos;
import interfaceGrafica.JanelaPrincipal;
import interfaceGrafica.QuadroVitoria;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;
import observers.Observed_JogadorReposicionado;

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
	public boolean				debug_mode = true;
	
	protected ArrayList<Casa>	movimentacaoPossivel;	
	protected EstadoDoJogo		estadoDoJogo = null;

	private ControladoraDoJogo() {
	}
	
	public void definirEstadoDoJogo( EstadoDoJogo estadoDoJogo ) {
		this.estadoDoJogo = estadoDoJogo;
	}
	
	public Tabuleiro obterTabuleiro() {
		return estadoDoJogo.tabuleiro;
	}
	

	public ArrayList<Jogador> obterListaDeJogadores() {
		return this.estadoDoJogo.listaDeJogadores;
	}
	
	protected Jogador obterJogadorDoPersonagem( PersonagemEnum personagem ) {
		for( Jogador jogador : this.obterListaDeJogadores() ) {
			if( jogador.personagem.personagem == personagem ) {
				return jogador;
			}
		}
		
		return null;
	}
	
	public Jogador obterJogadorDaVez() {
		return this.estadoDoJogo.jogadorDaVez;
	}
	
	public int obterValorDoUltimoDado() {
		return this.estadoDoJogo.valorDoDado;
	}
	
	public ArrayList<Casa> obterMovimentacaoPossivel() {
		return new ArrayList<Casa>( this.movimentacaoPossivel );
	}	
	
	public EstadoDoJogo.EtapaDaJogada obterEstadoDaJogada() {
		return this.estadoDoJogo.etapaDaJogada;
	}	
	
	public ArrayList<Carta> obterCrime() {
		return estadoDoJogo.crime;
	}
	
	
	
	public void adicionarJogador( Jogador jog ) {
		this.estadoDoJogo.listaDeJogadores.add( jog );
	}

	
	public void iniciarProximaJogada() {
		estadoDoJogo.jogadorDaVez.moveuSeForcadamente = false;
		
		estadoDoJogo.jogadorDaVez = estadoDoJogo.obterProximoJogador();
		
		// Resetar variaveis gerais de turno
		estadoDoJogo.valorDoDado = 0;
		movimentacaoPossivel = new ArrayList<Casa>();
		estadoDoJogo.etapaDaJogada = EstadoDoJogo.EtapaDaJogada.INICIO; 
	}
	
	public int rolarDadoParaMovimentacao( int valorPredeterminado ) {
		estadoDoJogo.valorDoDado = valorPredeterminado;
		calcularMovimentacaoPossivel();
		return estadoDoJogo.valorDoDado;
	}	
	
	public int rolarDadoParaMovimentacao() {
		int rolado = 1 + (int)(Math.random()*6);
		
		return rolarDadoParaMovimentacao(rolado);
	}
	
	private void calcularMovimentacaoPossivel() {
		ArrayList<Casa> casasPossiveis;
		
		casasPossiveis = estadoDoJogo.tabuleiro.obterCasasNaDistancia( estadoDoJogo.jogadorDaVez , estadoDoJogo.valorDoDado );
		
		movimentacaoPossivel = casasPossiveis;
		
		estadoDoJogo.etapaDaJogada = EstadoDoJogo.EtapaDaJogada.AGUARDANDO_MOVIMENTO; 
	}
	
	public void decidindoMovimento() {
		estadoDoJogo.etapaDaJogada = EstadoDoJogo.EtapaDaJogada.CONFIRMANDO_MOVIMENTO;
	}
	
	public void validarPalpite(ArrayList<Carta> palpite)
	{
		// Traz o jogador mencionado para o comodo atual
		for( Carta carta : palpite ) {
			if( carta.isSuspeito() == false  ) {
				continue;
			}
		
			// Obtem jogador mencionado
			PersonagemEnum personagemMencionado = Carta.tipoCartaParaPersonagemEnum( carta.tipo );
			Jogador jogadorMencionado = obterJogadorDoPersonagem( personagemMencionado );
			
			// Reposiciona o jogador mencionado
			Casa casaNova = estadoDoJogo.tabuleiro.obterUmaCasaLivreTipo( this.estadoDoJogo.jogadorDaVez.posicao.type );
			jogadorMencionado.definirPosicao( casaNova );
			jogadorMencionado.moveuSeForcadamente = true;
		}
		
		
		// iterar sobre jogadores, na ordem da roda
		ArrayList<Jogador> ordemDeJogadores = estadoDoJogo.obterProximosJogadores();
		
		for( Jogador jogador : ordemDeJogadores ) {
			if( jogador.temCarta(palpite) )
			{
				MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.ESCOLHA_CARTA);
				TradutorMenus.getInstance().desenharEscolhaCarta(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaEscolhaCarta, jogador, palpite);
				return;
			}
		}
	}
	
	public boolean validarAcusacao(ArrayList<Carta> acusacao)
	{
		if(acusacao.containsAll(estadoDoJogo.crime))
		{
			//TODO - end-Game, alguem vençeu
			System.out.println(estadoDoJogo.jogadorDaVez.personagem.nome + " venceu");
			JanelaPrincipal.getInstance().carregarQuadro( new QuadroVitoria(estadoDoJogo.jogadorDaVez.personagem.personagem, acusacao));
			return true;
		}
		else
		{
			estadoDoJogo.jogadorDaVez.emJogo = false;
			iniciarProximaJogada();
			MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
			return false;
		}
	}
	
	
	public void salvarPartidaEmArquivo( File file ) {
		this.estadoDoJogo.salvarEstadoEmArquivo( file );
	}
}
