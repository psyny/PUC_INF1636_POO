package mediadores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import atores.AtorCarta;
import atores.CenaBlocoNotas;
import atores.CenaMao;
import atores.CenaPalpite;
import jogo.Baralho;
import jogo.Carta;
import jogo.ControladoraDoJogo;
import jogo.Jogador;
import jogo.Jogador.Nota;

public class TradutorMenus {
	
	// Singleton com LazyHolder para tratar threads
		private static class LazyHolder {
			static TradutorMenus instance = new TradutorMenus();
			
			private static void resetSingleton() {
				LazyHolder.instance = new TradutorMenus();
			}
		}
		
		public static TradutorMenus getInstance() {
			return LazyHolder.instance;
		}
		
		public static void resetSingleton() {
			LazyHolder.resetSingleton();
		}	
		
	// ----------------------------------------
		
	class mouseListener_cartaMulti extends MouseAdapter {
		protected CartaAtor carta = null;
		
		public mouseListener_cartaMulti(CartaAtor carta) {
			this.carta = carta;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			carta.atorCarta.definirSelecionado(!(carta.atorCarta.getSelecionado()));
		}
	}
	
	class mouseListener_cartaSingle extends MouseAdapter {
		protected CartaAtor carta = null;
		
		public mouseListener_cartaSingle(CartaAtor carta) {
			this.carta = carta;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			carta.atorCarta.definirSelecionado(!(carta.atorCarta.getSelecionado()));
			desmarcarCartaTipo(carta.carta);
		}
	}
	
	public class CartaAtor {
		public AtorCarta atorCarta;
		public Carta carta;
		
		public CartaAtor(Carta carta, AtorCarta atorCarta)
		{
			this.carta = carta;
			this.atorCarta = atorCarta;
		}
	}
	
	public ArrayList<CartaAtor> cartasAtor = new ArrayList<CartaAtor>();
		
	public TradutorMenus()
	{
		
	}
	
	public void desenharCartasNaMao(CenaMao cena)
	{
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
		ArrayList<Carta> cartas = jogadorDaVez.obterMao();
		cartasAtor.clear();

		for (Carta carta : cartas) {
			CartaAtor cartaAtor = new CartaAtor(carta, new AtorCarta(carta));
			cartasAtor.add(cartaAtor);
			String arquivo = obteArquivoCarta(carta);
			cartaAtor.atorCarta.definirCarta(arquivo);
			cartaAtor.atorCarta.definirSelecionado(true);
			cena.desenharCarta(cartaAtor.atorCarta);
		}
	}
	
	public void desenharBlocoDeNotas(CenaBlocoNotas cena)
	{
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
	
		ArrayList<Carta> baralho = Baralho.todasCartas();
		cartasAtor.clear();
		
		for (Carta carta : baralho) {
			CartaAtor cartaAtor = new CartaAtor(carta, new AtorCarta(carta));
			cartasAtor.add(cartaAtor);
			String arquivo = obteArquivoCarta(carta);
			cartaAtor.atorCarta.definirCarta(arquivo);
			if(jogadorDaVez.temNota(carta))
				cartaAtor.atorCarta.definirSelecionado(true);	
			if(!jogadorDaVez.temNotaCerta(carta))
				cartaAtor.atorCarta.addMouseListener(new mouseListener_cartaMulti(cartaAtor));
			cena.desenharCarta(cartaAtor.atorCarta);
		}
	}
	
	public void desenharPalpite(CenaPalpite cena)
	{
		ArrayList<Carta> pilhaArmas = Baralho.pilhaArmas;
		ArrayList<Carta> pilhaSuspeitos = Baralho.pilhaSuspeitos;
		
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		cartasAtor.clear();
		
		for (Carta carta : pilhaSuspeitos) {
			CartaAtor cartaAtor = new CartaAtor(carta, new AtorCarta(carta));
			cartasAtor.add(cartaAtor);
			String arquivo = obteArquivoCarta(carta);
			cartaAtor.atorCarta.definirCarta(arquivo);
			cartaAtor.atorCarta.addMouseListener(new mouseListener_cartaSingle(cartaAtor));
			cena.desenharCarta(cartaAtor.atorCarta);
		}
		
		for (Carta carta : pilhaArmas) {
			CartaAtor cartaAtor = new CartaAtor(carta, new AtorCarta(carta));
			cartasAtor.add(cartaAtor);
			String arquivo = obteArquivoCarta(carta);
			cartaAtor.atorCarta.definirCarta(arquivo);
			cartaAtor.atorCarta.addMouseListener(new mouseListener_cartaSingle(cartaAtor));
			cena.desenharCarta(cartaAtor.atorCarta);
		}
		
		Carta carta = jogadorDaVez.obterCartaPosicao();
		CartaAtor cartaAtor = new CartaAtor(carta, new AtorCarta(carta));
		cartasAtor.add(cartaAtor);
		String arquivo = obteArquivoCarta(carta);
		cartaAtor.atorCarta.definirCarta(arquivo);
		cartaAtor.atorCarta.definirSelecionado(true);
		cena.desenharCarta(cartaAtor.atorCarta);
	}
	
	public void atualizarBlocoDeNotas()
	{
		Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
		for (CartaAtor carta : cartasAtor) {
			if(carta.atorCarta.getSelecionado() && !jogadorDaVez.temNota(carta.carta))
				jogadorDaVez.adicionarBlocoDeNotas(carta.carta);
			
			if(!carta.atorCarta.getSelecionado() && jogadorDaVez.temNota(carta.carta))
				jogadorDaVez.removerBlocoDeNotas(jogadorDaVez.obterNota(carta.carta));
		}
		System.out.println();
	}
	
	public void desmarcarCartaTipo(Carta carta)
	{
		for (CartaAtor cartaAtor : cartasAtor) {
			if(mesmoTipoCarta(cartaAtor.carta, carta) && !cartaAtor.carta.equals(carta))
			{
				cartaAtor.atorCarta.definirSelecionado(false);
			}
		}
	}
	
	private boolean mesmoTipoCarta(Carta c1, Carta c2)
	{
		if(c1.isArma() && c2.isArma())
			return true;
		if(c1.isComodo() && c2.isComodo())
			return true;
		if(c1.isSuspeito() && c2.isSuspeito())
			return true;
		
		return false;
	}
	
	public void gerarPalpite()
	{
		ArrayList<Carta> palpite = new ArrayList<Carta>();
		
		for (CartaAtor cartaAtor : cartasAtor) {
			if(cartaAtor.atorCarta.getSelecionado())
				palpite.add(cartaAtor.carta);
		}
		
		ControladoraDoJogo.getInstance().validarPalpite(palpite);
	}
	
	public String obteArquivoCarta(Carta carta)
	{
		switch (carta.tipo) {

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
}
