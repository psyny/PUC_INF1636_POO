package mediadores;

import animacao.*;
import atores.*;
import estruturas.*;
import jogo.*;

class Observer_animationEnd_Dados implements AnimationEndObserver {
	@Override
	public void animationEndNotify(AnimationEndObserved observed) {
		MediadorFluxoDeJogo.getInstance().iniciarMovimentacao();
	}
}

public class MediadorFluxoDeJogo {
	// Singleton com LazyHolder para tratar threads
	private static class LazyHolder {
		static MediadorFluxoDeJogo instance = new MediadorFluxoDeJogo();
		
		private static void resetSingleton() {
			LazyHolder.instance = new MediadorFluxoDeJogo();
		}
	}
	
	public static MediadorFluxoDeJogo getInstance() {
		return LazyHolder.instance;
	}
	
	public static void resetSingleton() {
		LazyHolder.resetSingleton();
	}	
	
	// ----------------------------------------
	
	// Variaveis externas: principais componentes do jogo
	public Tabuleiro 			tabuleiro = null;
	
	public CenaTabuleiro 		cenaTabuleiro = null;
	public CenaAtores 			cenaAtores = null;
	
	public TradutorJogadores 	tradutorJogadores = null;
	public TradutorMovimentacao tradutorMovimentacao = null;
	public TradutorTabuleiro 	tradutorTabuleiro = null;
	
	// Variaveis internas
	private Dado dado1 = null;
	private Dado dado2 = null;
	

	// ------
	
	public MediadorFluxoDeJogo() {
		
	}
	
	// Checadores de registro
		public boolean checarRegistroTabuleiro() {
			if( tabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Tabuleiro não registrado");
			else return true;
			return false;
		}
		
		public boolean checarRegistroCenaTabuleiro() {
			if( cenaTabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: CenaTabuleiro não registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroCenaAtores() {
			if( cenaAtores == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: CenaAtores não registrado");
			else return true;
			return false;
		}		
	
		public boolean checarRegistroTradutorJogadores() {
			if( tradutorJogadores == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorJogadores não registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroTradutorMovimentacao() {
			if( tradutorMovimentacao == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorMovimentacao não registrado");
			else return true;
			return false;
		}		
		
		public boolean checarRegistroTradutorTabuleiro() {
			if( tradutorTabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorTabuleiro não registrado");
			else return true;
			return false;
		}		
	
		
	// Nova Jogada ------------------------------------------------------------------		
		public void iniciarJogadaDaVez() {
			ControladoraDoJogo.getInstance().iniciarProximaJogada();
		}
		
	// Movimentacao ------------------------------------------------------------------
		public void rolarDados() {
			deletarDados();
			
			dado1 = new Dado();
			cenaAtores.addActor( dado1 , 10 );
			
			dado2 = new Dado();
			cenaAtores.addActor( dado2 , 10 );
			
			dado1.animationEndRegister( new Observer_animationEnd_Dados() );
			
			int valorAleatorio1 = (int)(Math.random()*6 + 1);
			int valorAleatorio2 = (int)(Math.random()*6 + 1);
	
			tradutorMovimentacao.desmarcarCasas();	
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			
			Casa casa = jogadorDaVez.obterPosicao();
			Vetor2D_double centro1 = tradutorTabuleiro.obterCentroDaCasa(casa);
			Vetor2D_double centro2 = tradutorTabuleiro.obterCentroDaCasa(casa);
			
			centro1.x += 100;
			centro2.x -= 100;
			
			dado1.Lancar( centro1 , valorAleatorio1 );
			dado2.Lancar( centro2 , valorAleatorio2 );
			
			ControladoraDoJogo.getInstance().rolarDadoParaMovimentacao( valorAleatorio1 + valorAleatorio2 );
		}
		
		public void deletarDados() {
			if( dado1 != null ) {
				dado1.setToDestroy( 0 );
				dado1 = null;
			}
			
			if( dado2 != null ) {
				dado2.setToDestroy( 0 );
				dado2 = null;
			}
		}
		
		public void iniciarMovimentacao() {
			tradutorMovimentacao.desmarcarCasas();	
			tradutorMovimentacao.marcarCasas();
		}
	
}
