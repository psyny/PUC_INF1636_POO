package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animacao.Actor;
import animacao.AnimatedSprite;
import estruturas.Vetor2D_int;
import jogo.Carta;

public class AtorCarta extends Actor {
	private class mouseListener_seletor extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent arg0)  {
			seletor.playAnimation(2);
		}
		
		@Override
		public void mouseExited(MouseEvent arg0)  {
			seletor.playAnimation(1);
		}
	}
	
	public enum TipoMarcador {
		VAZIO,
		SUSPEITO,
		INOCENTE,
		NEUTRO
	}

	private AnimatedSprite 	carta = null;	
	private AnimatedSprite 	marcador = null;
	private AnimatedSprite 	seletor = null;
	protected boolean 	selecionado = false;
	
	public AtorCarta(Carta carta) {
		super(120, 180);
		inicializarAtoresInternos();
	}

	public AtorCarta(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		inicializarAtoresInternos();
	}
	
	private void inicializarAtoresInternos() {
		this.addMouseListener( new mouseListener_seletor() );
		marcador 	= addAnimatedSprite( "carta_marcadores.txt", new Vetor2D_int(0, 0), 2);
		seletor 	= addAnimatedSprite( "carta_seletores.txt", new Vetor2D_int(0, 0), 2);
	}
	
	public void definirCarta(String arquivo)
	{
		carta = addAnimatedSprite(arquivo, new Vetor2D_int(0, 0), 1);
		carta.playAnimation(2);
	}
	
	public boolean getSelecionado()
	{
		return selecionado;
	}
	
	public void definirSelecionado(boolean flag)
	{
		selecionado = flag;

		if(carta == null)
			return;
		
		if(selecionado)
			carta.playAnimation(3);
		else
			carta.playAnimation(2);
	}
	
	public void definirMarcador( AtorCarta.TipoMarcador tipoMarcador ) {
		switch( tipoMarcador ) {
			case VAZIO:
				marcador.playAnimation(1);
				break;
				
			case SUSPEITO:
				marcador.playAnimation(2);
				break;
				
			case INOCENTE:
				marcador.playAnimation(3);
				break;
				
			case NEUTRO:
				marcador.playAnimation(1);
				break;
		}
	}

}
