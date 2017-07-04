import java.util.ArrayList;

import animacao.*;
import estruturas.*;
import interfaceGrafica.*;
import jogo_Nucleo.*;

public class APP {
	public static void main(String[] args) {
		Isometria.inicializar( 45 , 60 );
		//Isometria.inicializar( 0 , 0 );

		JanelaPrincipal janelaPrincipal = JanelaPrincipal.getInstance();
	}

}
