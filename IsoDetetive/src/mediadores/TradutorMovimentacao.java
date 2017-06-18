package mediadores;

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
import jogo.Tabuleiro;

public class TradutorMovimentacao implements CasaSelecionadaObserver {
	private CenaAtores 			cenaAtores;
	private TradutorTabuleiro	tradutorTabuleiro;

	private ArrayList<Marcador>	casasMarcadas = new ArrayList<Marcador>();
	
	public TradutorMovimentacao( CenaAtores cenaAtores , TradutorTabuleiro tradutorTabuleiro ) {
		this.cenaAtores = cenaAtores;
		this.tradutorTabuleiro = tradutorTabuleiro;
		
		tradutorTabuleiro.cenaTabuleiro.casaSelecionadaObservedRegister(this);
	}
	
	public void marcarCasas() {
		for( Casa casa : ControladoraDoJogo.getInstance().obterMovimentacaoPossivel() ) {
			Marcador marcador = new Marcador();
			marcador.casaReferente.x = casa.position.x;
			marcador.casaReferente.y = casa.position.y;
			
			casasMarcadas.add( marcador );	
			this.cenaAtores.addActor( marcador , 1 );

			Vetor2D_double posicao = tradutorTabuleiro.obterCentroDaCasa( casa.position.x , casa.position.y );
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

} 
