package mediadores;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import atores.*;
import estruturas.*;
import jogo.*;
import observers.*;


public class TradutorMovimentacao implements Observer_CasaSelecionada , MouseListener {
	private ArrayList<Marcador>	casasMarcadas = new ArrayList<Marcador>();
	
	public TradutorMovimentacao( ) {
		// Checa se os objetos necessarios foram registrados no fluxo de jogo
		MediadorFluxoDeJogo.getInstance().checarRegistroCenaTabuleiro();
		MediadorFluxoDeJogo.getInstance().checarRegistroTradutorTabuleiro();
		MediadorFluxoDeJogo.getInstance().checarRegistroTradutorJogadores();
		
		MediadorFluxoDeJogo.getInstance().cenaTabuleiro.register_casaSelecionadaObserved(this);
		MediadorFluxoDeJogo.getInstance().cenaTabuleiro.addMouseListener(this);
	}
	
	public void marcarCasas() {
		ArrayList<Casa> casas = ControladoraDoJogo.getInstance().obterMovimentacaoPossivel();
		for( Casa casa : casas ) {
			Marcador marcador = new Marcador();
			marcador.casaReferente.x = casa.position.x;
			marcador.casaReferente.y = casa.position.y;
			
			casasMarcadas.add( marcador );	
			MediadorFluxoDeJogo.getInstance().cenaTabuleiro.addActor( marcador , 1 );

			Vetor2D_double posicao = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casa.position.x , casa.position.y );
			marcador.setVirtualPosition( posicao.x , posicao.y , 0 );
		}
		
		MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.esconderParedes( casas );
	}
	
	public void desmarcarCasas() {
		for( Marcador ator : casasMarcadas ) {
			ator.setToDestroy();
		}
		casasMarcadas.clear();
		
		MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.exibirParedes();
	}

	@Override
	public void observerNotify(Observed_CasaSelecionada observed) {
		Vetor2D_int casaSelecionada = observed.obterCasaSelecionada();
		
		for( Marcador ator : casasMarcadas ) {
			ator.definirMarcado(false);
			
			if( ator.casaReferente.x == casaSelecionada.x && ator.casaReferente.y == casaSelecionada.y ) {
				ator.definirMarcado(true);
				MediadorFluxoDeJogo.getInstance().sombrearDados();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch (ControladoraDoJogo.getInstance().obterEstadoDaJogada()) {
			case AGUARDANDO_MOVIMENTO:
			case CONFIRMANDO_MOVIMENTO:
				break;
	
			default:
				return;
		}
		
		Vetor2D_int casaDestino = MediadorFluxoDeJogo.getInstance().cenaTabuleiro.ultimaCasaApontada;
		
		// Posiciona camera no local clicado
		Vetor2D_double localDoClique = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casaDestino.x , casaDestino.y );
		MediadorFluxoDeJogo.getInstance().centralizarCameraEmPosicaoVirtual( localDoClique );
		
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

		// Vai para a casa desejada
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		ControladoraDoJogo.getInstance().decidindoMovimento();
		MediadorFluxoDeJogo.getInstance().tradutorJogadores.reposicionarJogador( jogadorDaVez , casaDestino );
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

} 
