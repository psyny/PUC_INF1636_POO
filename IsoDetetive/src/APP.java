import estruturas.*;
import isometrico.*;

public class APP {

	public static void main(String[] args) {
		// Ferramentas.inicializar( 45 , 60 );
		Transformacoes.inicializar( 15 , 0 );
		
		Vetor2D carVet_original = new Vetor2D(1,0);
		Vetor2D carVet_novo;
		Vetor2D isoVet;
		
		
		System.out.println(carVet_original.x + "\t|\t" + carVet_original.y);
		
		isoVet = Transformacoes.obterVetorIsometrico( carVet_original );		
		System.out.println(isoVet.x + "\t|\t" + isoVet.y);
		
		carVet_novo = Transformacoes.obterVetorCartesiano( isoVet );		
		System.out.println(carVet_novo.x + "\t|\t" + carVet_novo.y);
		
		
	}

}
