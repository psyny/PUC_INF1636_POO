package mediadores;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import animacao.Actor;
import atores.AtorJogador;
import atores.AtorPiso;
import atores.CenaAtores;
import atores.CenaTabuleiro;
import atores.Marcador;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;
import jogo.Casa;
import jogo.CasaType;
import jogo.ControladoraDoJogo;
import jogo.Jogador;
import jogo.Tabuleiro;

public class TradutorMovimentacao implements CasaSelecionadaObserver , MouseListener {
	private TradutorJogadores 	tradutorJogadores;

	private ArrayList<Marcador>	casasMarcadas = new ArrayList<Marcador>();
	
	public TradutorMovimentacao( TradutorJogadores tradutorJogadores ) {
		this.tradutorJogadores = tradutorJogadores;
		
		tradutorJogadores.tradutorTabuleiro.cenaTabuleiro.casaSelecionadaObservedRegister(this);
		tradutorJogadores.tradutorTabuleiro.cenaTabuleiro.addMouseListener(this);
	}
	
	public void marcarCasas() {
		for( Casa casa : ControladoraDoJogo.getInstance().obterMovimentacaoPossivel() ) {
			Marcador marcador = new Marcador();
			marcador.casaReferente.x = casa.position.x;
			marcador.casaReferente.y = casa.position.y;
			
			casasMarcadas.add( marcador );	
			tradutorJogadores.tradutorTabuleiro.cenaTabuleiro.addActor( marcador , 1 );

			Vetor2D_double posicao = tradutorJogadores.tradutorTabuleiro.obterCentroDaCasa( casa.position.x , casa.position.y );
			marcador.setVirtualPosition( posicao.x , posicao.y , 0 );
		}
	}
	
	public void desmarcarCasas() {
		for( Marcador ator : casasMarcadas ) {
			ator.setToDestroy();
		}
		casasMarcadas.clear();
	}

	@Override
	public void observerNotify(CasaSelecionadaObserved observed) {
		Vetor2D_int casaSelecionada = observed.obterCasaSelecionada();
		
		for( Marcador ator : casasMarcadas ) {
			ator.definirMarcado(false);
			
			if( ator.casaReferente.x == casaSelecionada.x && ator.casaReferente.y == casaSelecionada.y ) {
				ator.definirMarcado(true);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if( ControladoraDoJogo.getInstance().obterEstadoDaJogada() != ControladoraDoJogo.EstadoDaJogada.AGUARDANDO_MOVIMENTO ) {
			return;
		}	
		
		Vetor2D_int casaDestino = tradutorJogadores.tradutorTabuleiro.cenaTabuleiro.ultimaCasaApontada;
		
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
		this.tradutorJogadores.reposicionarJogador( jogadorDaVez , casaDestino );
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
