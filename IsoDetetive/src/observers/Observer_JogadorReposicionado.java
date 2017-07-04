package observers;

import estruturas.Vetor2D_int;
import jogo_TiposEnumerados.PersonagemType;

public interface Observer_JogadorReposicionado {
	public void ObserverNotify_JogadorReposicionado( PersonagemType personagem , Vetor2D_int novaPosicao );
}
