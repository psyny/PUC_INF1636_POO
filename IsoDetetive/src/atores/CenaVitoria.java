package atores;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import animacao.Actor;
import animacao.Scene;
import atores.CameraMenu.Modos;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo;
import jogo.Jogador;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;

public class CenaVitoria extends Scene {
	
	class mouseListener_confirma extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			limparCena();
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MENU_PRINCIPAL);
			TradutorMenus.getInstance().gerarAcusacao();
		}
	}
	
	class mouseListener_fechar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			limparCena();
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MENU_PRINCIPAL);
		}
	}
	
	protected ArrayList<AtorCarta> cartasNaCena = new ArrayList<AtorCarta>();
	protected Vetor2D_int proximaPosicao = new Vetor2D_int(70, 130);
	protected int numeroDeSelecionadas = 3;
	
	public CenaVitoria()
	{
		super(50, 50, 900, 600);
		
		setBackground(Color.WHITE);
		setOpaque(true);
		

        AtorEtiqueta titulo = new AtorEtiqueta( AtorEtiqueta.Tipo.ACUSAR );
        addActor( titulo , 1 );
        titulo.setLocation(0, -30);			
        
		
		this.limparCena();
	}
	
	public CenaVitoria(int x, int y, int w, int h)
	{
		super(x, y, w, h);
	}
	
	public void desenharCarta(AtorCarta carta)
	{
		carta.setLocation(proximaPosicao.x, proximaPosicao.y);
		this.addActor(carta, 20);
		cartasNaCena.add(carta);
		
		ajustarProximaPosicao();
	}
	

	public void limparCena()
	{
		for (AtorCarta carta : cartasNaCena) {
			carta.setToDestroy();
		}
		
		cartasNaCena.clear();
		proximaPosicao = new Vetor2D_int(70, 130);
	}
	
	
	private void ajustarProximaPosicao()
	{
		proximaPosicao.x += 128;
		if(proximaPosicao.x >= 900)
		{
			proximaPosicao.x = 70;
			proximaPosicao.y += 190;
		}
	}
}
