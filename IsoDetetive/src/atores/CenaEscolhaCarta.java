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

public class CenaEscolhaCarta extends Scene {
	
	class mouseListener_fechar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			if(TradutorMenus.getInstance().obeterNumeroCartasSelecionadas() == numeroDeSelecionadas)
			{
				limparCena();
				MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MENU_PRINCIPAL);
				TradutorMenus.getInstance().registrarCartaEscolhida();
			}
		}
	}
	
	protected ArrayList<AtorCarta> cartasNaCena = new ArrayList<AtorCarta>();
	protected Vetor2D_int proximaPosicao = new Vetor2D_int(70, 100);
	
	protected Actor confirma;
	protected Vetor2D_int fecharPos = new Vetor2D_int( 0 , 20 );
	protected int numeroDeSelecionadas = 1;
	
	public CenaEscolhaCarta()
	{
		super(50, 50, 900, 600);
		
		setBackground(Color.WHITE);
		setOpaque(false);
		
		confirma = new AtorBotaoMenuJogo( "botao_confirmar.txt" );
		confirma.setLocation( fecharPos.x , fecharPos.y );  
		confirma.addMouseListener( new mouseListener_fechar() );
		this.addActor( confirma , 10 );	
		
		this.limparCena();
	}
	
	public CenaEscolhaCarta(int x, int y, int w, int h)
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
	
	public void ajustarConfirmaVisible(boolean estado)
	{
		confirma.setVisible(estado);
	}
	
	private void ajustarProximaPosicao()
	{
		proximaPosicao.x += 128;
		if(proximaPosicao.x >= 900)
		{
			proximaPosicao.x = 70;
			proximaPosicao.y += 190;
		}
		
		if( fecharPos.x < proximaPosicao.x ) {
			fecharPos.x = proximaPosicao.x - 80;
			confirma.setLocation( fecharPos.x , fecharPos.y );
		}
	}
}
