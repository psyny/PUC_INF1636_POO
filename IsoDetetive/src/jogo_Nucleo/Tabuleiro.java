package jogo_Nucleo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

import atores.AtorPiso;
import estruturas.*;
import jogo_TiposEnumerados.CasaType;


public class Tabuleiro {
	public enum DIRECOES {
		NORTE,
		SUL,
		LESTE,
		OESTE
	}
	
	public ArrayList<ArrayList<Casa>> casas;
	public ArrayList<Casa> registroDeCasas = new ArrayList<Casa>();
	
	
	private int linhas;
	private int colunas;
	
	public Vetor2D_int cellDimensions = new Vetor2D_int( 1 , 1 );
	
	//Construtor do Tabuleiro
	public Tabuleiro( int rows , int cols ) {
		this.linhas = rows;
		this.colunas = cols;
		
		for( int y = 0 ; y < rows ; y++ ) {
			ArrayList<Casa> newRow = new ArrayList<Casa>();
			casas.add( newRow );
			
			for( int x = 0 ; x < cols ; x++ ) {
				Casa casa = new Casa( x , y );
				newRow.add( casa );
				this.registroDeCasas.add( casa );
			}	
		}
	}
	
	//Construtor do Tabuleiro
	public Tabuleiro( ArrayList<ArrayList<Casa>> cells ) {
		this.casas = cells;
		this.linhas = cells.size();
		this.colunas = cells.get(0).size();
		
		for( ArrayList<Casa> linha : cells ) {
			for( Casa casa : linha ) {
				this.registroDeCasas.add( casa );
			}
		}
	}
	
	public int objeterQtdLinhas() {
		return this.linhas;
	}
	
	public int objeterQtdColunas() {
		return this.colunas;
	}	
	
	//Checa se dois inteiros fazem parte do tabuleiro
	public boolean isInGrid( int x , int y ) {
		if( x < 0 ) {
			return false;
		}
		if( x >= this.colunas ) {
			return false;
		}
		if( y < 0 ) {
			return false;
		}
		if( y >= this.linhas ) {
			return false;
		}
		return true;
	}
	
	// Retorna a casa contida no (x, y) do tabuleiro
	public Casa getCell( int x , int y ) {
		if( this.isInGrid(x, y) == false ) {
			return null;
		}
		
		return this.casas.get(y).get(x);
	}
	
	// aaa
	public Casa getCasa_Direcao( int x , int y , Tabuleiro.DIRECOES direcao ) {
		switch( direcao ) {
			case NORTE:
				return this.getCell(x, y-1);
				
			case SUL:
				return this.getCell(x, y+1);
				
			case LESTE:
				return this.getCell(x+1, y);
				
			case OESTE:
				return this.getCell(x-1, y);	
				
			default:
				return null;
		}
	}
	
	public Casa getCasa_Direcao( Casa casa , Tabuleiro.DIRECOES direcao ) { 
		return getCasa_Direcao( casa.position.x , casa.position.y , direcao );
	}
	
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
	public ArrayList<Casa> getNeighbors( int x , int y ) {
		ArrayList<Casa> neighbors = new ArrayList<Casa>();

		Casa center = this.getCell( x , y );
		if( center == null ) {
			return neighbors;
		}
		
		Casa nei;
		
		nei = this.getCasa_Direcao(x, y , DIRECOES.NORTE );
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCasa_Direcao(x, y , DIRECOES.SUL );
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCasa_Direcao(x, y , DIRECOES.LESTE );
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCasa_Direcao(x, y , DIRECOES.OESTE );
		if( nei != null ) {
			neighbors.add( nei );
		}		

		return neighbors;
	}
	
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
		public ArrayList<Casa> getNeighbors( Casa casa ) {
			return getNeighbors( casa.position.x , casa.position.y );
		}
		
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
		public ArrayList<Casa> getWalkableNeighbors( Casa casa ) {
			ArrayList<Casa> neighborsTemp;
			ArrayList<Casa> neighbors = new ArrayList<Casa>();
			
			neighborsTemp = getNeighbors(casa);
			
			for( Casa vizinho : neighborsTemp ) {
				if( isWalkable( vizinho ) ) {
					neighbors.add( vizinho );
				}
			}
			
			return neighbors;
		}
			
	
	// Checa se a casa esta livre para movimentar-se para ela
	public boolean isWalkable( Casa casa ) {
		// Checa se a casa é naturalmente livre para movimentar-se
		if( casa.isWalkable() == false ) {
			return false;
		}
		
		// Checa se a casa esta bloqueada por algum outro motivo
		return isFree(casa);
	}
		
		
	// Checa se a casa esta livre para posicionar um jogador ou objeto
		public boolean isFree( Casa casa ) {
			return !isBlocked(casa);
		}
	
	// Checa se a casa está bloqueada por um jogador ou objeto
		public boolean isBlocked( Casa casa ) {
			// Checa se a casa é naturalmente bloqueada
			if( casa.ehBloqueado == true ) {
				return true;
			}
			
			// Checa se a casa está bloqueada por algum jogador
			ArrayList<Jogador> Jogadores = ControladoraDoJogo.getInstance().obterListaDeJogadores();
			for( Jogador jogador : Jogadores ) {
				if( jogador.posicao == casa ) {
					return true;
				}
			}
			
			return false;
		}
	
	// Retorna a casa na posicao x e y se a casa é livre para movimentar-se
		public Casa getFreeCell( int x , int y ) {
			Casa casa = getCell(x,y);
			
			if( casa == null ) {
				return null;
			}
			
			if( isFree(casa) == false ) {
				return null;
			}
			
			return casa;
		}
		
	// Retorna todas as casas que são portas
		public ArrayList<Casa> obterCasas_Portas() {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( Casa casa : this.registroDeCasas ) {
				if( casa.isDoor() )
				casas.add( casa );
			}
			
			return casas;
		}
		
	// Retorna todas as casas que são portas de um tipo especifico
		public ArrayList<Casa> obterCasas_Portas(CasaType tipo) {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( Casa casa : this.registroDeCasas ) {
				if( casa.isDoor() && casa.type.equals(tipo) )
				casas.add( casa );
			}
			
			return casas;
		}

	// Obter todas as casas de um determinado tipo
		public ArrayList<Casa> obterCasasDoTipo() {
			return obterCasasDoTipo( CasaType.QUALQUER );
		}
		
		public ArrayList<Casa> obterCasasDoTipo( CasaType tipo ) {
			return obterCasasDoTipo( tipo , false );
		}
			
		public ArrayList<Casa> obterCasasDoTipo( CasaType tipo , boolean apenasLivres ) {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( Casa casa : this.registroDeCasas ) {
				if( casa.type == CasaType.QUALQUER || casa.type == tipo ) {
					if( apenasLivres == false ) {
						casas.add(casa);
					} 
					else if( isFree(casa) ) {
						casas.add(casa);
					}
				}
			}

			return casas;
		}
	
	//obter todas as casa adjacentes a porta
		public ArrayList<Casa> obterCasasEntradaDeComodo()
		{
			return this.obterCasasEntradaDeComodo( CasaType.QUALQUER );
		}
		
	//obter as casa adjacentes a porta do tipo passado
		public ArrayList<Casa> obterCasasEntradaDeComodo( CasaType tipo )
		{
			ArrayList<Casa> casasPorta = this.obterCasas_Portas();
			
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( Casa casa : casasPorta ) {
				if( tipo != CasaType.QUALQUER && tipo != casa.type ) {
					continue;
				}
				
				for(Casa casaVizinha : getNeighbors(casa))
				{
					if( casaVizinha.isRoom() )
						casas.add(casaVizinha);
				}
			}
			
			return casas;
		}
	
	// Obtem casas acessiveis a partir da casa X
		public ArrayList<Casa> obterMovimentoNaDistancia( Jogador jogador , int distancia ) {
			
			ArrayList<Casa> casasDeOrigem = new ArrayList<Casa>();
			ArrayList<Casa> casas;
			ArrayList<Casa> fronteiraTemp = new ArrayList<Casa>();
			ArrayList<Casa> vizinhos = new ArrayList<Casa>();
			
			// Checa se posicao inicial do jogador é uma sala
			if( jogador.posicao.isRoom() == false ) {
				casasDeOrigem.add(jogador.posicao);
				casas = new ArrayList<Casa>(casasDeOrigem);
			} else {
				// Adiciona como Origem do movimento as saidas das salas
				CasaType tipoPorta = Casa.tipoSalaParaTipoPorta( jogador.posicao.type );
				ArrayList<Casa> casas_tipoPorta = obterCasasDoTipo( tipoPorta );
				for (Casa casa : casas_tipoPorta) {
					if(isWalkable(casa)) {
						casasDeOrigem.add(casa);
					}
				}
				
				// Adiciona já como destino possivel, casas da passagem secreta
				casas = obterCasasPelaPasagemSecreta( jogador.posicao.type );
				
				// Caso o jogador tenha sido movido de maneira forcada, adicionar o comodo atual como possivel destino
				if( jogador.moveuSeForcadamente == true ) {
					ArrayList<Casa> casasComodo = obterCasasDoTipo( jogador.posicao.type , true );
					
					for( Casa casa : casasComodo ) {
						if( casas.indexOf( casa ) < 0 ) {
							casas.add( casa );
						}
					}
				}
			}
			
			// Prepara dados para fazer expansao da distancia
			ArrayList<Casa> fronteira = new ArrayList<Casa>(casasDeOrigem);
			for (Casa casa : casasDeOrigem) {
				casa.casaAnterior = null;
			}
			
			// Expansao de distancia pelo valor determinado
			while(distancia >= 0) {
				for (Casa casa : fronteira) {
					// Casos especiais: distancia zero, movimento acabou mas temos algo a calcular ainda
					if( distancia == 0 ){
						// So estamos interessados nas entradas das portas
						if ( casa.isDoor() == false ) {
							continue;
						}
					}
					
					// Casos normais:
					vizinhos = getWalkableNeighbors(casa);

					// Determina quais vizinhos sao elegiveis
					for (Casa casaVizinha : vizinhos) {
						// Checa se casa ja nao esta na lista
						if(casas.indexOf(casaVizinha) == -1) {
							// Checa possibilidade da casa ser uma fronteira
							boolean willAdd = false;
							if( distancia <= 0 ) {
								// Ja sabemos que a casa atual é uma porta, checamos no inicio do while
								if( casaVizinha.isRoom() && isWalkable(casaVizinha) ) {
									willAdd = true;
								}
							}
							else {
								if(isWalkable(casaVizinha)) {
									willAdd = true;
								}
							}
							
							// Checa se o jogador foi movido forcadamente ou nao
							if( jogador.moveuSeForcadamente == false && jogador.posicao.isRoom() ) {
								if( casaVizinha.type == jogador.posicao.type ) {
									willAdd = false;
								}
							}

							
							// Adicionar a casa a lista de casas diponiveis e nova fronteira
							if( willAdd == true ) {
								casaVizinha.casaAnterior = casa;
								casas.add(casaVizinha);
								fronteiraTemp.add(casaVizinha);
							} 
						}
					}	
				}
				
				fronteira = new ArrayList<Casa>(fronteiraTemp);
				fronteiraTemp.clear();
				distancia--;
			}
			
			return casas;
		}
		
		public Casa obterUmaCasaLivreTipo(CasaType tipo) {
			ArrayList<Casa> casas = obterCasasDoTipo(tipo);
			ArrayList<Casa> casasDisponiveis = new ArrayList<Casa>();
			Random random = new Random();
			
			for (Casa casa : casas) {
				if(isFree(casa) && casa.isWalkable() == false )
					casasDisponiveis.add(casa);
			}
			
			int indice = random.nextInt(casasDisponiveis.size());
			
			return casasDisponiveis.get(indice);
		}
		
		public ArrayList<Casa> obterCasasPelaPasagemSecreta( CasaType tipoOrigem ) {
			ArrayList<Casa> casas 		= new ArrayList<Casa>();
			ArrayList<Casa> casasTemp 	= new ArrayList<Casa>();
			
			// Caso a sala de origem nao tenha passagem secreta, retornar lista vazia
			switch( tipoOrigem ) {
				case COZINHA:
				case ESCRITORIO:
				case SL_INVERNO:
				case SL_ESTAR:
					break;
				
				default:
					return casas;
			}
			
			// Adiciona todas as casas possiveis
			switch( tipoOrigem ) {
				case COZINHA:
					casasTemp = obterCasasDoTipo( CasaType.ESCRITORIO );
					break;
					
				case ESCRITORIO:
					casasTemp = obterCasasDoTipo( CasaType.COZINHA );
					break;
					
				case SL_INVERNO:
					casasTemp = obterCasasDoTipo( CasaType.SL_ESTAR );
					break;			
					
				case SL_ESTAR:
					casasTemp = obterCasasDoTipo( CasaType.SL_INVERNO );
					break;
					
				default:
					break;
			}
				
			for( Casa casa : casasTemp ) {
				// Elimina casas que estao bloqueadas
				if( isBlocked(casa) ) {
					continue;
				}
				
				// Adiciona casa a lista final
				casas.add( casa );
			}

			return casas;
		}
}
