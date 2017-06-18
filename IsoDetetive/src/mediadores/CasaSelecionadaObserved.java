package mediadores;

import estruturas.Vetor2D_int;

public interface CasaSelecionadaObserved {
	public void casaSelecionadaObservedRegister(CasaSelecionadaObserver observer);
	public void casaSelecionadaObservedUnRegister(CasaSelecionadaObserver observer);
	public Vetor2D_int obterCasaSelecionada();
}
