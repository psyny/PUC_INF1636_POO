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

public class CenaFeedback extends Scene {
	
	class mouseListener_fechar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			limparCena();
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MENU_PRINCIPAL);
			TradutorMenus.getInstance().registrarCartaEscolhida();
		}
	}
	
	protected ArrayList<AtorCarta> cartasNaCena = new ArrayList<AtorCarta>();
	protected Vetor2D_int proximaPosicao = new Vetor2D_int(70, 100);
	protected AtorBotaoMenuJogo marcadorJogador = null;
	
	protected Actor fechar;
	protected Vetor2D_int confirmaPos = new Vetor2D_int( 0 , 20 );
	protected int numeroDeSelecionadas = 1;
	
	public CenaFeedback()
	{
		super(50, 50, 900, 600);
		
		setBackground(Color.WHITE);
		setOpaque(false);
		
		fechar = new AtorBotaoMenuJogo( "botao_fechar.txt" );
		fechar.setLocation( confirmaPos.x , confirmaPos.y );  
		fechar.addMouseListener( new mouseListener_fechar() );
		this.addActor( fechar , 10 );	
		
		this.limparCena();
	}
	
	public CenaFeedback(int x, int y, int w, int h)
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
	
	public void desenharPersonagem(AtorBotaoMenuJogo atorPersonagem)
	{
		atorPersonagem.setLocation(0, 0);
		this.addActor(atorPersonagem, 20);
		marcadorJogador = atorPersonagem;
	}
	
	public void limparCena()
	{
		for (AtorCarta carta : cartasNaCena) {
			carta.setToDestroy();
		}
		if(marcadorJogador != null)
			marcadorJogador.setToDestroy();
		
		cartasNaCena.clear();
		proximaPosicao = new Vetor2D_int(70, 130);
		confirmaPos = new Vetor2D_int( 0 , 20 );
		fechar.setVisible(false);
	}
	
	public void ajustarConfirmaVisible(boolean estado)
	{
		fechar.setVisible(estado);
	}
	
	private void ajustarProximaPosicao()
	{
		proximaPosicao.x += 128;
		if(proximaPosicao.x >= 900)
		{
			proximaPosicao.x = 70;
			proximaPosicao.y += 190;
		}
		
		if( confirmaPos.x < proximaPosicao.x ) {
			confirmaPos.x = proximaPosicao.x - 80;
			fechar.setLocation( confirmaPos.x , confirmaPos.y );
		}
	}
}
