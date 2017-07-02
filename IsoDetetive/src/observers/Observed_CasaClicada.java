package observers;

import estruturas.Vetor2D_int;

public interface Observed_CasaClicada {
	public void register_casaClicada( Observer_CasaClicada observer );
	public void unRegister_casaClicada( Observer_CasaClicada observer );
	public Vetor2D_int obterCasaClicada();
}
