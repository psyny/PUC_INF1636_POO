package facedes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import atores.*;
import estruturas.*;
import jogo_Nucleo.*;
import observers.*;


public class Facade_Movimentacao implements Observer_CasaSelecionada ,  Observed_CasaClicada , MouseListener {
	private ArrayList<Marcador>	casasMarcadas = new ArrayList<Marcador>();
	private ArrayList<Observer_CasaClicada>	Observer_CasaClicada_Register = new ArrayList<Observer_CasaClicada>();
	
	
	public Facade_Movimentacao( ) {
		// Checa se os objetos necessarios foram registrados no fluxo de jogo
		Facade_FluxoDeJogo.getInstance().checarRegistroCenaTabuleiro();
		Facade_FluxoDeJogo.getInstance().checarRegistroTradutorTabuleiro();
		Facade_FluxoDeJogo.getInstance().checarRegistroTradutorJogadores();
		
		Facade_FluxoDeJogo.getInstance().cenaTabuleiro.register_casaSelecionadaObserved(this);
		Facade_FluxoDeJogo.getInstance().cenaTabuleiro.addMouseListener(this);
	}
	
	public void marcarCasas() {
		ArrayList<Casa> casas = ControladoraDoJogo.getInstance().obterMovimentacaoPossivel();
		for( Casa casa : casas ) {
			Marcador marcador = new Marcador();
			marcador.casaReferente.x = casa.position.x;
			marcador.casaReferente.y = casa.position.y;
			
			casasMarcadas.add( marcador );	
			Facade_FluxoDeJogo.getInstance().cenaTabuleiro.addActor( marcador , 1 );

			Vetor2D_double posicao = Facade_FluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casa.position.x , casa.position.y );
			marcador.setVirtualPosition( posicao.x , posicao.y , 0 );
		}
		
		Facade_FluxoDeJogo.getInstance().tradutorTabuleiro.esconderParedes( casas );
	}
	
	public void desmarcarCasas() {
		for( Marcador ator : casasMarcadas ) {
			ator.setToDestroy();
		}
		casasMarcadas.clear();
		
		Facade_FluxoDeJogo.getInstance().tradutorTabuleiro.exibirParedes();
	}
	
	public void reposicionarJogador( Jogador jogador , Vetor2D_int novaCasa ) {
		Casa casa = Facade_FluxoDeJogo.getInstance().tabuleiro.getCell( novaCasa.x , novaCasa.y );
		
		jogador.definirPosicao( casa ); // Essa chamada forca a chamada do observer
	}	

	@Override
	public void observerNotify_CasaSelecionada(Observed_CasaSelecionada observed) {
		Vetor2D_int casaSelecionada = observed.obterCasaSelecionada();
		
		for( Marcador ator : casasMarcadas ) {
			ator.definirMarcado(false);
			
			if( ator.casaReferente.x == casaSelecionada.x && ator.casaReferente.y == casaSelecionada.y ) {
				ator.definirMarcado(true);
				Facade_FluxoDeJogo.getInstance().sombrearDados();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {	
		// Obter Casa Clicada
		Vetor2D_int casaDestino = Facade_FluxoDeJogo.getInstance().cenaTabuleiro.ultimaCasaApontada;
		
		// Posiciona camera no local clicado
		Vetor2D_double localDoClique = Facade_FluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casaDestino.x , casaDestino.y );
		Facade_FluxoDeJogo.getInstance().centralizarCameraEmPosicaoVirtual( localDoClique );
		
 		// ----
		if( Facade_FluxoDeJogo.getInstance().estaAguardandoMovimentacao == false ) {
			return;
		}		
		
		// Checa se é possivel ir para a casa desejada
		boolean exitFlag = true;
		for( Marcador marcador : casasMarcadas ) {
			if( marcador.casaReferente.x == casaDestino.x && marcador.casaReferente.y == casaDestino.y ) {
				exitFlag = false;
				break;
			}
		}
		if( exitFlag == true ) {
			return;
		}
		
		// Chamando os OBSERVERS:
		for( Observer_CasaClicada observer : this.Observer_CasaClicada_Register ) {
			observer.observerNotify_CasaClicada(this);
		}		

		// Vai para a casa desejada
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		reposicionarJogador( jogadorDaVez , casaDestino );
	}
	
		

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void register_casaClicada(Observer_CasaClicada observer) {
		Observer_CasaClicada_Register.add( observer );
	}

	@Override
	public void unRegister_casaClicada(Observer_CasaClicada observer) {
		Observer_CasaClicada_Register.remove( observer );
	}

	@Override
	public Vetor2D_int obterCasaClicada() {
		return Facade_FluxoDeJogo.getInstance().cenaTabuleiro.ultimaCasaApontada;
	}

} 
