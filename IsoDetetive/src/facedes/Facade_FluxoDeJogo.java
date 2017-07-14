package facedes;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import animacao.*;
import atores.*;
import atores.CameraMenu.Modos;
import estruturas.*;
import interfaceGrafica.JanelaPrincipal;
import jogo_Nucleo.*;
import jogo_TiposEnumerados.AcoesPossiveisType;
import jogo_TiposEnumerados.PersonagemType;

class Observer_animationEnd_Dados implements AnimationEndObserver {
	@Override
	public void animationEndNotify(AnimationEndObserved observed) {
		Facade_FluxoDeJogo.getInstance().iniciarMovimentacao();
	}
}

public class Facade_FluxoDeJogo {
	// Singleton com LazyHolder para tratar threads
	private static class LazyHolder {
		static Facade_FluxoDeJogo instance = new Facade_FluxoDeJogo();
		
		private static void resetSingleton() {
			LazyHolder.instance = new Facade_FluxoDeJogo();
		}
	}
	
	public static Facade_FluxoDeJogo getInstance() {
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
	
	public Facade_Jogadores 	tradutorJogadores = null;
	public Facade_Movimentacao tradutorMovimentacao = null;
	public Facade_Tabuleiro 	tradutorTabuleiro = null;
	
	// Variaveis internas
	private Dado dado1 = null;
	private Dado dado2 = null;
	
	
	// Flags Internas
	public boolean	estaAguardandoMovimentacao = false;

	// ------
	
	public Facade_FluxoDeJogo() {
		
	}
	
	// Checadores de registro
		public boolean checarRegistroCamera() {
			if( camera == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Camera não registrado");
			else return true;
			return false;
		}	
		
		public boolean checarRegistroCameraMenu() {
			if( cameraMenu == null )
				System.out.println("Mediador Fluxo de Jogo: Erro: Camera Menu não registrado");
			else return true;
			return false;
		}	
	
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
			iniciarJogadaDaVez( false );
		}
		
		public void iniciarJogadaDaVez( boolean jogoRecemCarregado ) {
			// Limpando Interface Grafica
			this.deletarDados();
			this.tradutorMovimentacao.desmarcarCasas();			
			
			// Notificando a controladora do jogo
			ControladoraDoJogo.getInstance().iniciarProximaJogada( jogoRecemCarregado );
			
			// Decide se o proximo jogador é humano ou maquina
			if(ControladoraDoJogo.getInstance().obterJogadorDaVez().obeterInteligenciaArtificial() == null) {
				iniciarJogada_Humano();
			}				
			else {
				iniciarJogada_InteligenciaArtificial();
			}
			
			// Posicionar Camera no personagem
			Vetor2D_double posicaoVirtualJogador = tradutorTabuleiro.obterCentroDaCasa( ControladoraDoJogo.getInstance().obterJogadorDaVez().obterPosicao() );
			this.centralizarCameraEmPosicaoVirtual(posicaoVirtualJogador);
		}
		
	// Jogada de um jogador humano
		private void iniciarJogada_Humano() {
			Facade_Menus.getInstance().menuOpcoesJogada_atualizar();
		}
		
	// Jogada de um jogador que e uma inteligencia artificial
		private void iniciarJogada_InteligenciaArtificial() {
			// Esconde botoes do menu de opções de jogada
			Facade_Menus.getInstance().menuOpcoesJogada_esconderBotoes();
			//pega o jogador da vez
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			//rola os dados
			rolarDados();
			//pega todas as casas que pode ir
			ArrayList<Casa> casasPossiveis = ControladoraDoJogo.getInstance().obterMovimentacaoPossivel();
			//IA sugere a melhor casa para ir
			Casa casaSugerida = jogadorDaVez.obeterInteligenciaArtificial().sugerirMovimento(casasPossiveis);
			//vai para essa casa
			jogadorDaVez.definirPosicao(casaSugerida);
			//faz alterações de interface grafica
			deletarDados();
			tradutorMovimentacao.desmarcarCasas();
			tradutorJogadores.definirSombreado( ControladoraDoJogo.getInstance().obterJogadorDaVez() , false );
			// Reseta Flag
			this.estaAguardandoMovimentacao = false;	
			// Notificar controladores
			ControladoraDoJogo.getInstance().confirmarMovimento();
			
			if(casaSugerida.isRoom())//se terminou o turno num quarto
			{
				//faz um palpite
				ArrayList<Carta> palpite = jogadorDaVez.obeterInteligenciaArtificial().sugerirPalpite();
				
				palpite.add(jogadorDaVez.obterCartaPosicao());
				
				//debug palpite da IA
				/*System.out.println();
				System.out.println(jogadorDaVez.obterPersonagem().obterNome());
				for (Carta carta : palpite) {
					System.out.println(carta.tipo);
				}*/
				
				ControladoraDoJogo.getInstance().validarPalpite(palpite);
				
				Facade_FluxoDeJogo.getInstance().cameraMenu.definirModo( CameraMenu.Modos.ESCOLHA_CARTA );
				Facade_Menus.getInstance().desenharEscolhaCarta(Facade_FluxoDeJogo.getInstance().cameraMenu.cenaEscolhaCarta);
			}
			else//caso contrario
			{
				//termina a jogada
				terminarJogada_InteligenciaArtificial();
			}
		}
		
		public void terminarJogada_InteligenciaArtificial()
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			
			//tentar acusar
			ArrayList<Carta> acusacao = jogadorDaVez.obeterInteligenciaArtificial().sugerirAcusação();
			if(acusacao != null)//se tem uma acusação valida
			{
				//acusa
				Facade_Menus.getInstance().gerarAcusacao(acusacao);
				//tendo acusado, ou vençe o jogo, ou sai dele. Logo não ha mais ações necessarias
			}
			//caso contrario termina o turno, não ha mais acões necessarias
			
			finalizarJogada();
		}
		
	// Finalizar a jogada
		public void finalizarJogada() {
			// Inicializando uma nova jogada
			this.iniciarJogadaDaVez();
		}
		
	// Carrega dados iniciais da controladora de jogo  ----------------------------------------------------------------
		public void carregarDadosDaControladora()
		{
			this.tabuleiro = ControladoraDoJogo.getInstance().obterTabuleiro();
		}		
			
	// Inicio do Jogo ----------------------------------------------------------------
		public void iniciarJogo()
		{
			iniciarJogadaDaVez(true);
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
			// Marcacao no chao
			tradutorMovimentacao.desmarcarCasas();	
			tradutorMovimentacao.marcarCasas();
			
			// Marcar ator do jogador
			tradutorJogadores.definirSombreado( ControladoraDoJogo.getInstance().obterJogadorDaVez() , true );
			camera.setIsFixedOnTarget( false );
			
			// Flag de aguardando movimentacao
			this.estaAguardandoMovimentacao = true;
		}
		
		public void confirmarMovimento() {
			//caso o jogador esteja em um comodo, colocar ele numa posiçao random do comodo
			deletarDados();
			tradutorMovimentacao.desmarcarCasas();
			tradutorJogadores.definirSombreado( ControladoraDoJogo.getInstance().obterJogadorDaVez() , false );
			
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			if(jogadorDaVez.obterPosicao().isRoom())
			{
				Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.PALPITE );
				tradutorMovimentacao.reposicionarJogador(jogadorDaVez, tabuleiro.obterUmaCasaLivreTipo(jogadorDaVez.obterPosicao().type).position);
			}

			// Reseta Flag
			this.estaAguardandoMovimentacao = false;
			
			// Notificar controladores
			ControladoraDoJogo.getInstance().confirmarMovimento();
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
