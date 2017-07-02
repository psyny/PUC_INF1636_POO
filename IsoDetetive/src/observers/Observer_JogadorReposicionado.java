package observers;

import estruturas.Vetor2D_int;
import jogo.PersonagemEnum;

public interface Observer_JogadorReposicionado {
	public void ObserverNotify_JogadorReposicionado( PersonagemEnum personagem , Vetor2D_int novaPosicao );
}
