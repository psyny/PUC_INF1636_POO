package jogo_Nucleo;

import java.io.File;
import java.util.ArrayList;

import atores.AtorBotaoMenuJogo;
import interfaceGrafica.JanelaPrincipal;
import interfaceGrafica.QuadroInicial;
import interfaceGrafica.QuadroJogo;
import interfaceGrafica.QuadroSelecaoDeJogadores;
import jogo_TiposEnumerados.AcoesPossiveisType;
import jogo_TiposEnumerados.PersonagemType;

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
	protected Jogador			jogadorReacao = null;
	protected ArrayList<Carta>	palpiteReacao;

	protected Jogador 			jogadorVitorioso = null;
	
	
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
	
	protected Jogador obterJogadorDoPersonagem( PersonagemType personagem ) {
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
	
	public Jogador obterJogadorReacao() {
		return this.jogadorReacao;
	}
	
	public ArrayList<Carta> obterPalpiteReacao() {
		return this.palpiteReacao;
	}
	
	public ArrayList<Casa> obterMovimentacaoPossivel() {
		return new ArrayList<Casa>( this.movimentacaoPossivel );
	}	
	
	public ArrayList<Carta> obterCrime() {
		return estadoDoJogo.crime;
	}
	
	public void adicionarJogador( Jogador jog ) {
		this.estadoDoJogo.listaDeJogadores.add( jog );
	}
	
	
	// Acoes da Jogada
		private void removerAcaoPossivel( AcoesPossiveisType opcao ) {
			if( estadoDoJogo.acoesPossiveis.indexOf( opcao ) >= 0 ) {
				estadoDoJogo.acoesPossiveis.remove( opcao );
			}
		}
		
		private void removerTodasAcoesPossiveis() {
			estadoDoJogo.acoesPossiveis.clear();
		}
		
		private void adicionarAcaoPossivel( AcoesPossiveisType opcao ) {
			if( estadoDoJogo.acoesPossiveis.indexOf( opcao ) < 0 ) {
				estadoDoJogo.acoesPossiveis.add( opcao );
			}
		}
		
		public boolean jogadorPodeRealizarAcao( AcoesPossiveisType opcao ) {
			if( estadoDoJogo.acoesPossiveis.indexOf( opcao ) >= 0 ) {
				return true;
			} else {
				return false;
			}
		}	
		
		public ArrayList<AcoesPossiveisType> obterAcoesPossiveis() {
			return new ArrayList<AcoesPossiveisType>( estadoDoJogo.acoesPossiveis );
		}
	
	// Fluxo dos turnos
		public void iniciarJogo() {
			// Checa condicoes de inicio de partida
			if( this.estadoDoJogo == null ) {
				System.out.println( "Partida nao pode ser inicializada: estado de jogo nao configurado." );
				System.exit(1);
			}
			
			if( this.estadoDoJogo.tabuleiro == null ) {
				System.out.println( "Partida nao pode ser inicializada: tabuleiro nao carregado." );
				System.exit(1);
			}
			
			if( this.estadoDoJogo.listaDeJogadores == null || this.estadoDoJogo.listaDeJogadores.size() < 3 ) {
				System.out.println( "Partida nao pode ser inicializada: jogadores insuficientes." );
				System.exit(1);
			}
			
			if( this.estadoDoJogo.crime == null || this.estadoDoJogo.crime.size() != 3 ) {
				System.out.println( "Partida nao pode ser inicializada: crime nao calculado." );
				System.exit(1);
			}
			
			if( this.estadoDoJogo.crime.size() != 3 ) {
				System.out.println( "Partida nao pode ser inicializada: cartas do crime diferente de 3." );
				System.exit(1);
			}
			
			// Tudo ok, inicializa jogo
			this.iniciarProximaJogada(true);
		}
		
		private void finalizarJogadaAtual() {
			// Jogador nao pode ter movido forcadamente
			estadoDoJogo.jogadorDaVez.moveuSeForcadamente = false;
			
			// Flag que define que condicoes da jogada precisam ser recalculadas
			estadoDoJogo.opcoesDeJogadaCalculadas = false;
		}
		
		private void configurarInicioDeJogada() {
			// Obtem proximo jogador
			estadoDoJogo.jogadorDaVez = estadoDoJogo.obterProximoJogador();
			
			// Resetar variaveis gerais de turno
			estadoDoJogo.valorDoDado = 0;
			movimentacaoPossivel = new ArrayList<Casa>();
			
			// Acoes que o jogador SEMPRE tem acesso
			removerTodasAcoesPossiveis();
			adicionarAcaoPossivel( AcoesPossiveisType.SALVAR );
			adicionarAcaoPossivel( AcoesPossiveisType.PALPITE );
			adicionarAcaoPossivel( AcoesPossiveisType.ACUSAR );
			adicionarAcaoPossivel( AcoesPossiveisType.VER_MAO );
			adicionarAcaoPossivel( AcoesPossiveisType.VER_NOTAS );
			
			// Acoes que dependem do estado inicial
				// Decide se jogador pode mover-se
				if( estadoDoJogo.jogadorDaVez.obterPosicao().isRoom() ) {
					ArrayList<Casa> casasPossiveis = estadoDoJogo.tabuleiro.obterMovimentoNaDistancia( estadoDoJogo.jogadorDaVez , 1 );
															
					// Checa se o jogador tem condicoes de sair da sala
					if( casasPossiveis.size() == 0 ) {
						adicionarAcaoPossivel( AcoesPossiveisType.PASSAR_A_VEZ );
					} else {
						adicionarAcaoPossivel( AcoesPossiveisType.ROLAR_DADOS );
					}
				} else {
					adicionarAcaoPossivel( AcoesPossiveisType.PASSAR_A_VEZ );
					adicionarAcaoPossivel( AcoesPossiveisType.ROLAR_DADOS );
				}			
				
			
			// Sinaliza Flag das opcoes iniciais dessa jogada ja foram calculadas
			estadoDoJogo.opcoesDeJogadaCalculadas = true;
		}
		
		public void iniciarProximaJogada() {
			this.iniciarProximaJogada( false );
		}
		 
		public void iniciarProximaJogada( boolean jogoRecemCarregado ) {
			// Finaliza estado atual do jogo
			if( jogoRecemCarregado == false ) {
				this.finalizarJogadaAtual();
			}
			
			// Configura opcoes de inicio de jogada, se elas ainda nao foram calculadas
			// OBS.: etapa importante para considerar jogo carregado na metade da jogada ( load )
			if( estadoDoJogo.opcoesDeJogadaCalculadas == false ) {
				this.configurarInicioDeJogada();
			}
		}
		
		
	// Fluxo dentro da jogada
		public int rolarDadoParaMovimentacao( int valorPredeterminado ) {
			removerAcaoPossivel( AcoesPossiveisType.ROLAR_DADOS );
			
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
			
			casasPossiveis = estadoDoJogo.tabuleiro.obterMovimentoNaDistancia( estadoDoJogo.jogadorDaVez , estadoDoJogo.valorDoDado );			
			movimentacaoPossivel = casasPossiveis;
			
			adicionarAcaoPossivel( AcoesPossiveisType.MOVER );
		}
		
		public void confirmarMovimento() {
			adicionarAcaoPossivel( AcoesPossiveisType.PASSAR_A_VEZ );
			removerAcaoPossivel( AcoesPossiveisType.MOVER );
		}
		
		public void validarPalpite(ArrayList<Carta> palpite)
		{
			removerAcaoPossivel( AcoesPossiveisType.PALPITE );
			
			palpiteReacao = palpite;
			// Traz o jogador mencionado para o comodo atual
			for( Carta carta : palpite ) {
				if( carta.isSuspeito() == false  ) {
					continue;
				}
			
				// Obtem jogador mencionado
				PersonagemType personagemMencionado = Carta.tipoCartaParaPersonagemEnum( carta.tipo );
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
					jogadorReacao = jogador;
					return;
				}
			}
		}
		
		public void validarAcusacao(ArrayList<Carta> acusacao)
		{
			removerAcaoPossivel( AcoesPossiveisType.ACUSAR );
			
			if(acusacao.containsAll(estadoDoJogo.crime))
			{
				// Jogador que acusou venceu!
				System.out.println(estadoDoJogo.jogadorDaVez.personagem.nome + " venceu");
				
				this.jogadorVitorioso = estadoDoJogo.jogadorDaVez;
			}
			else
			{
				// Jogador que sobrou venceu!
				estadoDoJogo.jogadorDaVez.emJogo = false;
				
				// Descobrir se so sobrou 1
				int jogadoresEmJogo = 0;
				Jogador ultimoJogadorEmJogo = null;
				for( Jogador jogador : estadoDoJogo.listaDeJogadores ) {
					if( jogador.emJogo == true ) {
						ultimoJogadorEmJogo = jogador;
						jogadoresEmJogo++;
					}
				}
				
				
				if( jogadoresEmJogo == 1 ) {
					this.jogadorVitorioso = ultimoJogadorEmJogo;
				}
			}
		}
	
	// Final de jogo
		public boolean jogoAcabou() {
			if (this.jogadorVitorioso == null ) {
				return false;
			}
			else {
				return true;
			}
		}
		
		public PersonagemType obterPersonagemVitorioso() {
			if (this.jogadorVitorioso == null ) {
				return null;
			}
			else {
				return this.jogadorVitorioso.obterPersonagem().obterEnum();
			}
		}
	
	// Salvando o estado do jogo
	public void salvarPartidaEmArquivo( File file ) {
		this.estadoDoJogo.salvarEstadoEmArquivo( file );
	}
	
}
