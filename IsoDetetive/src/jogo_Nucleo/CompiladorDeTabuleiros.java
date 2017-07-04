package jogo_Nucleo;

import java.util.ArrayList;

import jogo_TiposEnumerados.CasaType;
import jogo_TiposEnumerados.PersonagemType;

public abstract class CompiladorDeTabuleiros {
	
	
	public static void compilarTabuleiro( Tabuleiro tabuleiro ) {
		// Definicoes q so dependem da casa em si
		for( Casa casa : tabuleiro.registroDeCasas ) {
			compilarEstadoIsoladoDaCasa( casa );
		}
		
		// Definicoes que dependem das casas ao redor
		for( Casa casa : tabuleiro.registroDeCasas ) {
			compilarEstadoConjuntoDaCasa( tabuleiro , casa );
		}
	}
	
	private static void compilarEstadoIsoladoDaCasa( Casa casa ) {
		// Configurações que dependem apenas da casa atual
		if( casa.type == CasaType.CORREDOR || casa.isStartPosition() || casa.isDoor() ) {
			casa.ehAndavel = true;
			casa.ehBloqueado = false;
		}
		else if( casa.isRoom() ) {
			casa.ehAndavel = false;
			casa.ehBloqueado = false;
		}
		else {
			casa.ehAndavel = false;
			casa.ehBloqueado = true;
		}
	}
	
	private static void compilarEstadoConjuntoDaCasa( Tabuleiro tabuleiro , Casa casa ) {
		ArrayList<Casa> vizinhos = new ArrayList<Casa>();
		
		checarEntradaDeSala(tabuleiro , casa , vizinhos);
		checarPosicaoInicial( tabuleiro , casa );
	}
	
	// Preenche aray de vizinhos apenas se ja nao estiver preenchido
	private static void preencheVizinhos( Tabuleiro tabuleiro , Casa casa , ArrayList<Casa> vizinhos ) {
		if( vizinhos.size() == 0 ) {
			vizinhos.addAll( tabuleiro.getNeighbors(casa) );
		}
	}
	
	private static void checarEntradaDeSala( Tabuleiro tabuleiro , Casa casa , ArrayList<Casa> vizinhos ) {
		preencheVizinhos( tabuleiro , casa , vizinhos );
		
		if( casa.isDoor() ) {
			for( Casa vizinho : vizinhos ) {
				if( vizinho.type == Casa.tipoPortaParaTipoSala( casa.type ) ) {
					vizinho.ehAndavel = true;
				}
			}
		}
	}
	
	private static void checarPosicaoInicial( Tabuleiro tabuleiro , Casa casa ) {
		switch( casa.type ) {
			case INICIO_L:
				PersonagemLista.getInstance().obterPersonagem(  PersonagemType.L ).definirCasaInicial( casa );
				break;
			
			case INICIO_SHERLOCK:
				PersonagemLista.getInstance().obterPersonagem( PersonagemType.SHERLOCK ).definirCasaInicial( casa );
				break;
				
			case INICIO_CARMEN:
				PersonagemLista.getInstance().obterPersonagem( PersonagemType.CARMEN ).definirCasaInicial( casa );
				break;
				
			case INICIO_PANTERA:
				PersonagemLista.getInstance().obterPersonagem( PersonagemType.PANTERA ).definirCasaInicial(  casa );
				break;
				
			case INICIO_EDMORT:
				PersonagemLista.getInstance().obterPersonagem( PersonagemType.EDMORT ).definirCasaInicial(  casa );
				break;
				
			case INICIO_BATMAN:
				PersonagemLista.getInstance().obterPersonagem( PersonagemType.BATMAN ).definirCasaInicial( casa );
				break;	
				
			default:
				break;
		}
	}
	

}
