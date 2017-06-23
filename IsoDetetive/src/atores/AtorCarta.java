package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animacao.Actor;
import animacao.AnimatedSprite;
import estruturas.Vetor2D_int;
import jogo.Carta;
import jogo.CartaEnum;

public class AtorCarta extends Actor {
	private class mouseListener_mouseOver extends MouseAdapter {
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
	protected boolean 	flagSelecionado = false;
	
	private CartaEnum	tipo;
	
	public AtorCarta() {
		super(120, 180);
		inicializarAtoresInternos();
	}

	public AtorCarta(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		inicializarAtoresInternos();
	}
	
	private void inicializarAtoresInternos() {
		this.addMouseListener( new mouseListener_mouseOver() );
		marcador 	= addAnimatedSprite( "carta_marcadores.txt", new Vetor2D_int(0, 0), 2);
		seletor 	= addAnimatedSprite( "carta_seletores.txt", new Vetor2D_int(0, 0), 2);
	}
	
	public void definirCarta(String arquivo)
	{
		carta = addAnimatedSprite(arquivo, new Vetor2D_int(0, 0), 1);
		carta.playAnimation(2);
	}
	
	public void definirCarta( CartaEnum tipo )
	{
		this.tipo = tipo;
		String arquivo = this.obterArquivoCarta(tipo);		
		this.definirCarta(arquivo);
	}
	
	public String obterArquivoCarta( CartaEnum tipo ) {
		switch (tipo) {
		
		case BATMAN:
			return "carta_batman.txt";
		case BATRANGUE:
			return "carta_boomerang.txt";
		case BIBLIOTECA:
			return "carta_biblioteca.txt";
		case CACHIMBO:
			return "carta_cachimbo.txt";
		case CARMEN:
			return "carta_carmen.txt";
		case COZINHA:
			return "carta_cozinha.txt";
		case DEATH_NOTE:
			return "carta_deathnote.txt";
		case DIAMANTE:
			return "carta_diamante.txt";
		case EDMORT:
			return "carta_edmort.txt";
		case ENTRADA:
			return "carta_entrada.txt";
		case ESCRITORIO:
			return "carta_escritorio.txt";
		case FEDORA:
			return "carta_fedora.txt";
		case JARDIM_INVERNO:
			return "carta_jardiminverno.txt";
		case L:
			return "carta_l.txt";
		case PANTERA:
			return "carta_panther.txt";
		case REVOLVER:
			return "carta_revolver.txt";
		case SALA_DE_ESTAR:
			return "carta_salaestar.txt";
		case SALA_DE_JANTAR:
			return "carta_salajantar.txt";
		case SALA_DE_JOGOS:
			return "carta_salajogos.txt";
		case SALA_DE_MUSICA:
			return "carta_salamusica.txt";
		case SHERLOCK:
			return "carta_sherlock.txt";
			
		default:
			return "carta_dummy.txt";
	}
	}
	
	public boolean getSelecionado()
	{
		return flagSelecionado;
	}
	
	public void definirSelecionado(boolean flag)
	{
		flagSelecionado = flag;

		if(carta == null)
			return;
		
		if(flagSelecionado)
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
	
	public CartaEnum obterTipo() {
		return this.tipo;
	}
	

}
