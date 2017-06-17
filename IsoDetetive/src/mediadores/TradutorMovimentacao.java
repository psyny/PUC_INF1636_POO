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

public class TradutorMovimentacao {
	private CenaAtores 			cenaAtores;
	private TradutorTabuleiro	tradutorTabuleiro;

	private ArrayList<Actor>	casasMarcadas = new ArrayList<Actor>();
	
	public TradutorMovimentacao( CenaAtores cenaAtores , TradutorTabuleiro tradutorTabuleiro ) {
		this.cenaAtores = cenaAtores;
		this.tradutorTabuleiro = tradutorTabuleiro;
	}
	
	public void marcarCasas() {
		for( Casa casa : ControladoraDoJogo.getInstance().obterMovimentacaoPossivel() ) {
			Marcador marcador = new Marcador();
			
			casasMarcadas.add( marcador );	
			this.cenaAtores.addActor( marcador , 1 );

			Vetor2D_double posicao = tradutorTabuleiro.obterCentroDaCasa( casa.position.x , casa.position.y );
			marcador.setVirtualPosition( posicao.x , posicao.y , 0 );
		}
	}
	
	public void desmarcarCasas() {
		for( Actor ator : casasMarcadas ) {
			ator.setToDestroy();
		}
	}

} 
