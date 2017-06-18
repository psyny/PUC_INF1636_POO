package jogo;

import java.util.ArrayList;
import java.util.Random;

import atores.AtorPiso;
import estruturas.*;


public class Tabuleiro {
	public ArrayList<ArrayList<Casa>> casas;
	
	private int linhas;
	private int colunas;
	
	public Vetor2D_int cellDimensions = new Vetor2D_int( 1 , 1 );
	
	//Construtor do Tabuleiro
	public Tabuleiro( int rows , int cols ) {
		ControladoraDoJogo.getInstance().tabuleiro = this;
		
		this.linhas = rows;
		this.colunas = cols;
		
		for( int y = 0 ; y < rows ; y++ ) {
			ArrayList<Casa> newRow = new ArrayList<Casa>();
			casas.add( newRow );
			
			for( int x = 0 ; x < cols ; x++ ) {
				Casa casa = new Casa( x , y );
				newRow.add( casa );
			}	
		}
	}
	
	//Construtor do Tabuleiro
	public Tabuleiro( ArrayList<ArrayList<Casa>> cells ) {
		ControladoraDoJogo.getInstance().tabuleiro = this;
		
		this.casas = cells;
		this.linhas = cells.size();
		this.colunas = cells.get(0).size();
	}
	
	public int objeterQtdLinhas() {
		return this.linhas;
	}
	
	public int objeterQtdColunas() {
		return this.colunas;
	}	
	
	// Coleta dados essenciais
	public void scanGameInfo() {
		// Localiza dados 
		for( ArrayList<Casa> linha : this.casas ) {
			for( Casa casa : linha ) {
				switch( casa.type ) {
					case INICIO_L:
						PersonagemLista.getInstance().obterPersonagem(  PersonagemEnum.L ).definirCasaInicial( casa );
						break;
					
					case INICIO_SHERLOCK:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.SHERLOCK ).definirCasaInicial( casa );
						break;
						
					case INICIO_CARMEN:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.CARMEN ).definirCasaInicial( casa );
						break;
						
					case INICIO_PANTERA:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.PANTERA ).definirCasaInicial(  casa );
						break;
						
					case INICIO_EDMORT:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.EDMORT ).definirCasaInicial(  casa );
						break;
						
					case INICIO_BATMAN:
						PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.BATMAN ).definirCasaInicial( casa );
						break;	
				}
			}
		}
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
	
	
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
	public ArrayList<Casa> getNeighbors( int x , int y ) {
		ArrayList<Casa> neighbors = new ArrayList<Casa>();

		Casa center = this.getCell( x , y );
		if( center == null ) {
			return neighbors;
		}
		
		Casa nei;
		
		nei = this.getCell(x+1, y);
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCell(x-1, y);
		if( nei != null ) {
			neighbors.add( nei );
		}
		
		nei = this.getCell(x, y+1);
		if( nei != null ) {
			neighbors.add( nei );
		}

		nei = this.getCell(x, y-1);
		if( nei != null ) {
			neighbors.add( nei );
		}		
		
		return neighbors;
	}
	
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
		public ArrayList<Casa> getNeighbors( Casa casa ) {
			ArrayList<Casa> neighbors = new ArrayList<Casa>();

			Casa center = this.getCell( casa.position.x , casa.position.y );
			if( center == null ) {
				return neighbors;
			}
			
			Casa nei;
			
			nei = this.getCell(casa.position.x+1, casa.position.y);
			if( nei != null ) {
				neighbors.add( nei );
			}
			
			nei = this.getCell(casa.position.x-1, casa.position.y);
			if( nei != null ) {
				neighbors.add( nei );
			}
			
			nei = this.getCell(casa.position.x, casa.position.y+1);
			if( nei != null ) {
				neighbors.add( nei );
			}

			nei = this.getCell(casa.position.x, casa.position.y-1);
			if( nei != null ) {
				neighbors.add( nei );
			}		
			
			return neighbors;
		}
		
	//Retorna os vizinhos da casa contida no (x, y) do tabuleiro
			public ArrayList<Casa> getFreeNeighbors( Casa casa ) {
				ArrayList<Casa> neighbors = new ArrayList<Casa>();

				Casa center = this.getCell( casa.position.x , casa.position.y );
				if( center == null ) {
					return neighbors;
				}
				
				Casa nei;
				boolean isDoor = casa.isDoor();
				
				nei = this.getCell(casa.position.x+1, casa.position.y);
				if( nei != null ) {
					if(isFree(nei))
						neighbors.add( nei );
					else if(nei.isRoom() && isDoor)
						neighbors.add( nei );
				}
				
				nei = this.getCell(casa.position.x-1, casa.position.y);
				if( nei != null ) {
					if(isFree(nei))
						neighbors.add( nei );
					else if(nei.isRoom() && isDoor)
						neighbors.add( nei );
				}
				
				nei = this.getCell(casa.position.x, casa.position.y+1);
				if( nei != null ) {
					if(isFree(nei))
						neighbors.add( nei );
					else if(nei.isRoom() && isDoor)
						neighbors.add( nei );
				}

				nei = this.getCell(casa.position.x, casa.position.y-1);
				if( nei != null ) {
					if(isFree(nei))
						neighbors.add( nei );
					else if(nei.isRoom() && isDoor)
						neighbors.add( nei );
				}		
				
				return neighbors;
			}
			
	
	// Checa se a casa esta livre para movimentar-se para ela
	public boolean isFree( Casa casa ) {
		// Checa se a casa é naturalmente acessivel
		if( casa.isWalkable() == false ) {
			return false;
		}
		
		return isBlocked(casa);
	}
	
	// Checa se a casa está bloqueada por um jogador ou movel
	public boolean isBlocked( Casa casa ) {
		// Checa se a casa está bloqueada por algum jogador
		ArrayList<Jogador> Jogadores = ControladoraDoJogo.getInstance().obterListaDeJogadores();
		for( Jogador jogador : Jogadores ) {
			if( jogador.posicao == casa ) {
				return false;
			}
		}
		
		return true;
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
	
	// Obter todas as casas de um determinado tipo
		public ArrayList<Casa> obterCasasDoTipo() {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( int y = 0 ; y < linhas ; y++ ) {
				for( int x = 0 ; x < colunas ; x++ ) {
					casas.add( this.casas.get(y).get(x) );
				}	
			}
			
			return casas;
		}
		
		public ArrayList<Casa> obterCasasDoTipo( CasaType tipo ) {
			ArrayList<Casa> casas = new ArrayList<Casa>();
			
			for( int y = 0 ; y < linhas ; y++ ) {
				for( int x = 0 ; x < colunas ; x++ ) {
					Casa casa = null;
					try{
						casa = this.casas.get(y).get(x);
					}
					catch (Exception e) {
						System.out.println(x);
						System.out.println(y);
					}
					
					if( casa.type == tipo ) {
						casas.add( casa );
					}
				}	
			}
			
			return casas;
		}
	
	// Obtem casas acessiveis a partir da casa X
		public ArrayList<Casa> obterCasasNaDistancia( Jogador jogador , int distancia ) {
			
			ArrayList<Casa> casasDeOrigem = new ArrayList<Casa>();
			ArrayList<Casa> casas;
			ArrayList<Casa> fronteiraTemp = new ArrayList<Casa>();
			ArrayList<Casa> vizinhos = new ArrayList<Casa>();
			
			if( jogador.posicao.isRoom() == false ) {
				casasDeOrigem.add(jogador.posicao);
				casas = new ArrayList<Casa>(casasDeOrigem);
			} else {
				CasaType tipoPorta = Casa.tipoSalaParaTipoPorta( jogador.posicao.type );
				ArrayList<Casa> casas_tipoPorta = obterCasasDoTipo( tipoPorta );
				for (Casa casa : casas_tipoPorta) {
					if(isFree(casa))
						casasDeOrigem.add(casa);
				}
				casas = new ArrayList<Casa>();
				
				switch (jogador.posicao.type) {
					case COZINHA:
					case SL_ESTAR:
					case SL_INVERNO:
					case ESCRITORIO:
						if(jogador.posicao.type != CasaType.COZINHA)
							casas.addAll(obterCasasDoTipo( CasaType.COZINHA ));
						if(jogador.posicao.type != CasaType.SL_ESTAR)
							casas.addAll(obterCasasDoTipo( CasaType.SL_ESTAR ));
						if(jogador.posicao.type != CasaType.SL_INVERNO)
							casas.addAll(obterCasasDoTipo( CasaType.SL_INVERNO ));
						if(jogador.posicao.type != CasaType.ESCRITORIO)
							casas.addAll(obterCasasDoTipo( CasaType.ESCRITORIO ));
						break;
						
					/*case SL_MUSICA:
					case SL_JOGOS:
					case SL_JANTAR:
					case BIBLIOTECA:
					case ENTRADA:
						casas.addAll(obterCasasDoTipo( jogador.posicao.type ));
						break;*/
	
					default:
						casas = new ArrayList<Casa>(casasDeOrigem);
						break;
				}
			}
			
			ArrayList<Casa> fronteira = new ArrayList<Casa>(casasDeOrigem);

			for (Casa casa : casasDeOrigem) {
				casa.casaAnterior = null;
			}
			
			while(distancia > 0) {
				for (Casa casa : fronteira) {
					vizinhos = getFreeNeighbors(casa);
					
					for (Casa casaVizinha : vizinhos) {
						if(casas.indexOf(casaVizinha) == -1) {
							if(!(jogador.posicao.isRoom() && jogador.posicao.type == casaVizinha.type)) {
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
		
		public Casa obterPosicaoLivreTipo(CasaType tipo) {
			ArrayList<Casa> casas = obterCasasDoTipo(tipo);
			ArrayList<Casa> casasDisponiveis = new ArrayList<Casa>();
			Random random = new Random();
			
			for (Casa casa : casas) {
				if(isBlocked(casa))
					casasDisponiveis.add(casa);
			}
			
			int indice = random.nextInt(casasDisponiveis.size());
			
			return casasDisponiveis.get(indice);
		}
}
