import Ferramentas.*;
import Isometrico.*;

public class APP {

	public static void main(String[] args) {
		Ferramentas.inicializar( 45 , 60 );
		
		Vetor2D vet = Ferramentas.obterVetorTransformado( new Vetor2D(1,0) );		
		System.out.println(vet.x + "\t|\t" + vet.y);
		
		
	}

}
