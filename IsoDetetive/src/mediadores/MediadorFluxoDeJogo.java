package mediadores;

import animacao.*;
import atores.*;
import atores.CameraMenu.Modos;
import estruturas.*;
import jogo.*;
import jogo.ControladoraDoJogo.EstadoDaJogada;

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
	public Camera				camera = null;
	public CameraMenu			cameraMenu = null;
	
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
		public boolean checarRegistroCamera() {
			if( camera == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Camera n�o registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroCameraMenu() {
			if( cameraMenu == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Camera Menu n�o registrado");
			else return true;
			return false;
		}	
	
		public boolean checarRegistroTabuleiro() {
			if( tabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Tabuleiro n�o registrado");
			else return true;
			return false;
		}
		
		public boolean checarRegistroCenaTabuleiro() {
			if( cenaTabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: CenaTabuleiro n�o registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroCenaAtores() {
			if( cenaAtores == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: CenaAtores n�o registrado");
			else return true;
			return false;
		}		
	
		public boolean checarRegistroTradutorJogadores() {
			if( tradutorJogadores == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorJogadores n�o registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroTradutorMovimentacao() {
			if( tradutorMovimentacao == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorMovimentacao n�o registrado");
			else return true;
			return false;
		}		
		
		public boolean checarRegistroTradutorTabuleiro() {
			if( tradutorTabuleiro == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: TradutorTabuleiro n�o registrado");
			else return true;
			return false;
		}		
	
		
	// Nova Jogada ------------------------------------------------------------------		
		public void iniciarJogadaDaVez() {
			// Limpando Interface Grafica
			this.deletarDados();
			this.tradutorMovimentacao.desmarcarCasas();
			
			// Notificando a controladora do jogo
			ControladoraDoJogo.getInstance().iniciarProximaJogada();
			
			// Criando o menu com as op��es disponiveis para o jogador atual
				cameraMenu.menuPrincipal.esconderBotoes();
				Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
				
				// Icone do Jogador
					switch( jogadorDaVez.obterPersonagem().obterEnum() ) {
						case L:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_L );
							break;
							
						case SHERLOCK:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_SHERLOCK );
							break;
							
						case CARMEN:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_CARMEN );
							break;
							
						case PANTERA:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_PANTERA );
							break;
							
						case EDMORT:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_EDMORT );
							break;
							
						case BATMAN:
							cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.PERSONAGEM_BATMAN );
							break;	
					}
					
				// Bot�es sempre disponiveis
				cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.BOTAO_ACUSAR );
				cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.BOTAO_MAO );
				cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.BOTAO_NOTAS );
				
				// Bot�es de situacao
				// TODO
				cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.BOTAO_DADO );
				cameraMenu.menuPrincipal.ativarBotao( AtorBotaoMenuJogo.Tipo.BOTAO_PASSAR );
			
			// Posicionar Camera no personagem
			Vetor2D_double posicaoVirtualJogador = tradutorTabuleiro.obterCentroDaCasa(ControladoraDoJogo.getInstance().obterJogadorDaVez().obterPosicao());
			this.centralizarCameraEmPosicaoVirtual(posicaoVirtualJogador);
		}
			
	// Inicio do Jogo ----------------------------------------------------------------
		public void iniciarJogo()
		{
			ControladoraDoJogo.getInstance().iniciarPartida();
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
			
			// Posicionar Camera
			
			Vetor2D_int centroProjetado = cenaTabuleiro.getProjection( tradutorTabuleiro.obterCentroDaCasa(casa) );
			camera.setIsFixedOnTarget( true );
			camera.setTarget( centroProjetado.x , centroProjetado.y );
			//camera.setPositionCenteredOn( centroProjetado.x , centroProjetado.y );

			// Jogo
			ControladoraDoJogo.getInstance().rolarDadoParaMovimentacao( valorAleatorio1 + valorAleatorio2 );
		}
		
		public void sombrearDados() {
			if( dado1 != null ) {
				dado1.definirSombreado(true);
			}
			
			if( dado2 != null ) {
				dado2.definirSombreado(true);
			}
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
			tradutorJogadores.definirSombreado( ControladoraDoJogo.getInstance().obterJogadorDaVez() , true );
			camera.setIsFixedOnTarget( false );
		}
		
		public EstadoDaJogada obterEstadoDoJogo() {
			return ControladoraDoJogo.getInstance().obterEstadoDaJogada();
		}
		
		public void confirmarMovimento() {
			//caso o jogador esteja em um comodo, colocar ele numa posi�ao random do comodo
			deletarDados();
			tradutorJogadores.definirSombreado( ControladoraDoJogo.getInstance().obterJogadorDaVez() , false );
			
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			if(jogadorDaVez.obterPosicao().isRoom())
			{
				cameraMenu.definirModo(Modos.PALPITE);
				TradutorMenus.getInstance().desenharMenuPalpite(cameraMenu.cenaPalpite);
				tradutorJogadores.reposicionarJogador(jogadorDaVez, tabuleiro.obterPosicaoLivreTipo(jogadorDaVez.obterPosicao().type).position);
			}

			tradutorMovimentacao.desmarcarCasas();
		}
		
	// Camera
		public void centralizarCameraEmPosicaoVirtual( Vetor2D_double posicaoVirtual ) {
			Vetor2D_int posicaoReal = cenaTabuleiro.getProjection( posicaoVirtual );
			this.centralizarCameraEmPosicaoReal(posicaoReal);
		}
		
		public void centralizarCameraEmPosicaoReal( Vetor2D_double posicaoReal ) {
			this.centralizarCameraEmPosicaoReal( new Vetor2D_int( (int)posicaoReal.x , (int)posicaoReal.y ));
		}			
		
		public void centralizarCameraEmPosicaoReal( Vetor2D_int posicaoReal ) {
			camera.setIsFixedOnTarget( true );
			camera.setTarget( posicaoReal.x , posicaoReal.y );
			//camera.setPositionCenteredOn( centroProjetado.x , centroProjetado.y );
		}	
	
}
