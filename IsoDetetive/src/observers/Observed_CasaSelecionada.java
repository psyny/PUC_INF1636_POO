package observers;

import estruturas.Vetor2D_int;

public interface Observed_CasaSelecionada {
	public void register_casaSelecionadaObserved(Observer_CasaSelecionada observer);
	public void unRegister_casaSelecionadaObserved(Observer_CasaSelecionada observer);
	public Vetor2D_int obterCasaSelecionada();
}
