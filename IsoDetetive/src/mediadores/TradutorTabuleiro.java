package mediadores;

import java.util.ArrayList;

import animacao.Actor;
import atores.AtorJogador;
import atores.AtorParede;
import atores.AtorPilar;
import atores.AtorPiso;
import atores.CenaTabuleiro;
import atores.Marcador;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;
import jogo.Casa;
import jogo.CasaType;
import jogo.ControladoraDoJogo;
import jogo.Tabuleiro;

public class TradutorTabuleiro {
	public class Celula {
		public int x;
		public int y;
		
		public ArrayList<Actor> atoresCortaveis = new ArrayList<Actor>();
		public ArrayList<Actor> atoresOcultaveis = new ArrayList<Actor>();
		public ArrayList<Actor> atoresTemporarios = new ArrayList<Actor>();
		
		public Casa casa = null;
		
		public Celula( int x , int y ) {
			this.x = x;
			this.y = y;
		}
		
	}
	
	private static int			wallBaseLayer = 7;
	
	protected int 				casa_largura;
	protected int				casa_altura;
	
	protected int				linhas;
	protected int				colunas;
	
	protected ArrayList<ArrayList<Celula>> 	celulasGraficas = new ArrayList<ArrayList<Celula>>();
	protected ArrayList<Celula> 			registro_celulasGraficas = new ArrayList<Celula>();
	
	public TradutorTabuleiro( int casa_largura , int casa_altura ) {
		// Checa se os objetos necessarios foram registrados no fluxo de jogo
		MediadorFluxoDeJogo.getInstance().checarRegistroCenaTabuleiro();
		MediadorFluxoDeJogo.getInstance().checarRegistroTabuleiro();
		
		this.casa_largura	= casa_largura;
		this.casa_altura 	= casa_altura;
		
		this.gerarCelulas();
	}
	
	public void popularTabuleiroGrafico() {
		// Definir tamanho virtual da cena tabuleiro
		int tamanho_x = MediadorFluxoDeJogo.getInstance().tabuleiro.objeterQtdColunas() * casa_largura;
		int tamanho_y = MediadorFluxoDeJogo.getInstance().tabuleiro.objeterQtdLinhas() * casa_altura;
		Vetor2D_int tamanhoVirtualDoTabuleiro = new Vetor2D_int( tamanho_x , tamanho_y );
		MediadorFluxoDeJogo.getInstance().cenaTabuleiro.definirTamanhoVirtual( tamanhoVirtualDoTabuleiro.x , tamanhoVirtualDoTabuleiro.y );
		
		// Popular cena com os atores ( tiles ) gráficos
		for( ArrayList<Casa> linha : MediadorFluxoDeJogo.getInstance().tabuleiro.casas ) {
			for( Casa casa : linha ) {
				AtorPiso piso = this.gerarPisoParaTipo( casa.type );
				MediadorFluxoDeJogo.getInstance().cenaTabuleiro.addActor( piso , 0 );	
				
				Vetor2D_double atorPos = this.obterCentroDaCasa( casa );
				
				piso.setVirtualPosition( atorPos.x , atorPos.y );
			}
		}
	}
	
	private AtorPiso gerarPisoParaTipo( CasaType tipo ) {
		AtorPiso piso = new AtorPiso();
		
		switch( tipo ) {
			default:
				piso.definirTile(1, 1);
				break;
				
			case VACUO:
				piso.definirTile(1, 2);
				break;
				
			case CENTRO:
				piso.definirTile(1, 2);
				break;		
				
			case CORREDOR:
				piso.definirTile(1, 1);
				break;					
				
			case COZINHA:
				piso.definirTile(2, 7);
				break;
				
			case SL_MUSICA:
				piso.definirTile(2, 9);
				break;
				
			case SL_INVERNO:
				piso.definirTile(2, 1);
				break;
				
			case SL_JANTAR:
				piso.definirTile(2, 10);
				break;
				
			case SL_JOGOS:
				piso.definirTile(2, 4);
				break;
				
			case SL_ESTAR:
				piso.definirTile(2, 11);
				break;
				
			case ENTRADA:
				piso.definirTile(2, 2);
				break;
				
			case BIBLIOTECA:
				piso.definirTile(2, 3);
				break;	
				
			case ESCRITORIO:
				piso.definirTile(2, 6);
				break;					
				
		}
		
		
		// Adicionar outros efeitos visuais
		switch( tipo ) {
			default:
				break;
				
			case CORREDOR:
				piso.talvezAdicionarSujeira(5);
				break;	
				
			case COZINHA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 7);
				break;
				
			case SL_MUSICA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 9);
				break;
				
			case SL_INVERNO_PORTA:
				piso.adicionarTapeteDeEntrada(2, 1);
				break;
				
			case SL_JANTAR_PORTA:
				piso.adicionarTapeteDeEntrada(2, 10);
				break;
				
			case SL_JOGOS_PORTA:
				piso.adicionarTapeteDeEntrada(2, 4);
				break;
				
			case SL_ESTAR_PORTA:
				piso.adicionarTapeteDeEntrada(2, 11);
				break;
				
			case ENTRADA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 2);
				break;
				
			case BIBLIOTECA_PORTA:
				piso.adicionarTapeteDeEntrada(2, 3);
				break;	
				
			case ESCRITORIO_PORTA:
				piso.adicionarTapeteDeEntrada(2, 6);
				break;	
				
			case COZINHA:
			case SL_MUSICA:	
			case SL_INVERNO:		
			case SL_JANTAR:	
			case SL_JOGOS:	
			case SL_ESTAR:	
			case ENTRADA:
			case BIBLIOTECA:
			case ESCRITORIO:
				piso.talvezAdicionarSujeira(10);
				break;							
		}		
		
		
		return piso;
	} 
	

	public Vetor2D_double obterCentroDaCasa( Casa casa ) {
		Vetor2D_double centro = new Vetor2D_double( casa.position.x * casa_largura , casa.position.y * casa_altura );

		return centro;
	}
	
	public Vetor2D_double obterCentroDaCasa( int x , int y ) {
		Casa casa = MediadorFluxoDeJogo.getInstance().tabuleiro.getCell(x, y);
	
		return this.obterCentroDaCasa(casa);
	}
	

	// Celulas
		public void gerarCelulas() {
			this.linhas = ControladoraDoJogo.getInstance().obterTabuleiro().objeterQtdLinhas();
			this.colunas = ControladoraDoJogo.getInstance().obterTabuleiro().objeterQtdColunas();
			
			for( int y = 0 ; y < this.linhas ; y++ ) {
				ArrayList<Celula> newRow = new ArrayList<Celula>();
				this.celulasGraficas.add( newRow );
				
				for( int x = 0 ; x < this.colunas ; x++ ) {
					Celula celulaGrafica = new Celula( x , y );
					celulaGrafica.casa = ControladoraDoJogo.getInstance().obterTabuleiro().getCell( x , y );
					
					newRow.add( celulaGrafica );
					registro_celulasGraficas.add( celulaGrafica );
				}	
			}
		}
		
		// Processa as informacoes das celulas, gerando atores graficos e alterando a controladora de jogo
		public void compilarCelulas() {
			
			for( Celula celulaGrafica : registro_celulasGraficas ) {
				// Verifica se e parede
				processarParede( celulaGrafica );
			}
		}
		
		private void processarParede( Celula celulaGrafica ) {
			// So existem paredes em 2 tipos de casa: Comodo ou Vazio
			
			if( celulaGrafica.casa.isRoom() == true ) {
				if( celulaGrafica.casa.isWalkable() == false ) {
					processarParede_comodo( celulaGrafica );
				}
			}
			else if ( celulaGrafica.casa.type == CasaType.VACUO ) {
				processarParede_vacuo( celulaGrafica );
			}
			
			return;
		}
		
		private void processarParede_comodo( Celula celulaGrafica ) {
			// Depende dos vizinhos...
			Casa casaCentro = celulaGrafica.casa;
			Casa casaNorte = ControladoraDoJogo.getInstance().obterTabuleiro().getCasa_Direcao( casaCentro , Tabuleiro.DIRECOES.NORTE );
			Casa casaSul = ControladoraDoJogo.getInstance().obterTabuleiro().getCasa_Direcao( casaCentro , Tabuleiro.DIRECOES.SUL );
			Casa casaLeste = ControladoraDoJogo.getInstance().obterTabuleiro().getCasa_Direcao( casaCentro , Tabuleiro.DIRECOES.LESTE );
			Casa casaOeste = ControladoraDoJogo.getInstance().obterTabuleiro().getCasa_Direcao( casaCentro , Tabuleiro.DIRECOES.OESTE );
			

			int paredeFlagNorte = obterFlagParede( casaCentro , casaNorte , casaOeste , casaLeste );
			int paredeFlagSul = obterFlagParede( casaCentro , casaSul , casaOeste , casaLeste );
			int paredeFlagLeste = obterFlagParede( casaCentro , casaLeste , casaSul , casaNorte );
			int paredeFlagOeste = obterFlagParede( casaCentro , casaOeste , casaSul , casaNorte );

			adicionarParedeACena( celulaGrafica , Tabuleiro.DIRECOES.NORTE , paredeFlagNorte );
			adicionarParedeACena( celulaGrafica , Tabuleiro.DIRECOES.SUL , paredeFlagSul );
			adicionarParedeACena( celulaGrafica , Tabuleiro.DIRECOES.LESTE , paredeFlagLeste );
			adicionarParedeACena( celulaGrafica , Tabuleiro.DIRECOES.OESTE , paredeFlagOeste );
			
			int[] flagsPilar = this.obterFlagsPilar( paredeFlagNorte , paredeFlagSul , paredeFlagLeste , paredeFlagOeste);
			this.adicionarPilaresACena(celulaGrafica, flagsPilar);
			
			
		}
		
		private void processarParede_vacuo( Celula celulaGrafica ) {
			// TODO
			return;
		}
		
		private int obterFlagParede( Casa centro , Casa frente , Casa esquerda , Casa direita ) {
			int paredeFlag = 0;
			
			paredeFlag = 0;
			if( frente.type != centro.type ) {
				paredeFlag = 1;
				
				if( esquerda.type != centro.type || esquerda.isWalkable() ) {
					paredeFlag = 2;
				}
				
				if( direita.type != centro.type || direita.isWalkable() ) {
					if( paredeFlag == 2 ) {
						paredeFlag = 4;
					} else {
						paredeFlag = 3;
					}
				}
			}
			
			return paredeFlag;
		}
		
		private void adicionarParedeACena( Celula celulaGrafica , Tabuleiro.DIRECOES direcao , int paredeFlag ) {
			// Verifica se ha alguma parede pare adicionar
			if( paredeFlag == 0 ) {
				return;
			}
			
			Casa centro = celulaGrafica.casa;
			
			// Define o layer de acordo com a direcao
			int layer = TradutorTabuleiro.wallBaseLayer;
			if( direcao == Tabuleiro.DIRECOES.NORTE || direcao == Tabuleiro.DIRECOES.OESTE ) {
				layer += 1;
			}
			else if( direcao == Tabuleiro.DIRECOES.SUL || direcao == Tabuleiro.DIRECOES.LESTE ) {
				layer += 3;
			}
			
			// Adiciona
			Vetor2D_double posicao;
			AtorParede atorParede;
			
			posicao = obterCentroDaCasa( centro.position.x , centro.position.y );
			atorParede = new AtorParede( direcao , paredeFlag );
			MediadorFluxoDeJogo.getInstance().cenaAtores.addActor( atorParede , layer );
			atorParede.setVirtualPosition( posicao.x , posicao.y , 0 );	
			
			celulaGrafica.atoresCortaveis.add( atorParede );
		}
		
		private int[] obterFlagsPilar( int paredeFlag_NORTE , int paredeFlag_SUL , int paredeFlag_LESTE , int paredeFlag_OESTE ) {
			int pillarFlags[] = { 0 , 0 , 0 , 0 };
			
			if( paredeFlag_NORTE == 0 && paredeFlag_SUL == 0 && paredeFlag_LESTE == 0 && paredeFlag_OESTE == 0 ) {
				return pillarFlags;
			}
			
			if( paredeFlag_NORTE == 2 || paredeFlag_NORTE == 4 || paredeFlag_OESTE == 3 || paredeFlag_OESTE == 4 ) {
				pillarFlags[0] = 1;
			}
			
			if( paredeFlag_NORTE == 3 || paredeFlag_NORTE == 4 || paredeFlag_LESTE == 3 || paredeFlag_LESTE == 4 ) {
				pillarFlags[1] = 2;
			}
			
			if( paredeFlag_SUL == 2 || paredeFlag_SUL == 4 || paredeFlag_OESTE == 2 || paredeFlag_OESTE == 4 ) {
				pillarFlags[3] = 4;
			}			
			
			if( paredeFlag_SUL == 3 || paredeFlag_SUL == 4 || paredeFlag_LESTE == 2 || paredeFlag_LESTE == 4 ) {
				pillarFlags[2] = 3;
			}	
			
			return pillarFlags;
		}		
		

		private void adicionarPilaresACena( Celula celulaGrafica , int[] flagsPilares ) {
			Casa centro = celulaGrafica.casa;
			
			int layer = 0;
			
			for( int i = 0 ; i < 4 ; i++ ) {
				if( flagsPilares[i] == 0 ) {
					continue;
				}
				
				// Define o layer
				layer = TradutorTabuleiro.wallBaseLayer;
				
				switch( flagsPilares[i] ){
				case 1:
					layer += 0;
					break;
					
				case 2:
					layer += 2;
					break;
					
				case 3:
					layer += 4;
					break;
					
				case 4:
					layer += 2;
					break;				
				}
				
				
				// Adiciona
				Vetor2D_double posicao;
				AtorPilar atorPilar;
				
				posicao = obterCentroDaCasa( centro.position.x , centro.position.y );
				atorPilar = new AtorPilar( flagsPilares[i] );
				MediadorFluxoDeJogo.getInstance().cenaAtores.addActor( atorPilar , layer );
				atorPilar.setVirtualPosition( posicao.x , posicao.y , 0 );	
				
				celulaGrafica.atoresCortaveis.add( atorPilar );
			}
		}		
} 
