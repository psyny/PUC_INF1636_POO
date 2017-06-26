package mediadores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import atores.AtorCarta;
import atores.CenaAcusacao;
import atores.CenaBlocoNotas;
import atores.CenaMao;
import atores.CenaPalpite;
import jogo.Baralho;
import jogo.Carta;
import jogo.CartaEnum;
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
		
	class MouseListener_cartaSelecaoMultipla extends MouseAdapter {
		protected ReferenciaCartaAtor carta = null;
		
		public MouseListener_cartaSelecaoMultipla(ReferenciaCartaAtor carta) {
			this.carta = carta;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			boolean estado = carta.atorCarta.getSelecionado();
			estado = !estado;
			
			carta.atorCarta.definirSelecionado( estado );
			
			if( estado == true ) {
				carta.atorCarta.definirMarcador( AtorCarta.TipoMarcador.NEUTRO );
			}
			else {
				carta.atorCarta.definirMarcador( AtorCarta.TipoMarcador.SUSPEITO );
			}
		}
	}
	
	class MouseListener_cartaSelecaoUnica extends MouseAdapter {
		protected ReferenciaCartaAtor carta = null;
		
		public MouseListener_cartaSelecaoUnica(ReferenciaCartaAtor carta) {
			this.carta = carta;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			carta.atorCarta.definirSelecionado(!(carta.atorCarta.getSelecionado()));
			desmarcarCartaTipo(carta.carta);
		}
	}
	
	public class ReferenciaCartaAtor {
		public AtorCarta atorCarta;
		public Carta carta;
		
		public ReferenciaCartaAtor(Carta carta, AtorCarta atorCarta)
		{
			this.carta = carta;
			this.atorCarta = atorCarta;
		}
	}
	
	public ArrayList<ReferenciaCartaAtor> referenciasCartasAtor = new ArrayList<ReferenciaCartaAtor>();
		
	public TradutorMenus()
	{
		
	}
	
	// Menu: Cartas na mao
		public void desenharCartasNaMao(CenaMao cena)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			
			ArrayList<Carta> cartas = jogadorDaVez.obterMao();
			referenciasCartasAtor.clear();
	
			for (Carta carta : cartas) {
				ReferenciaCartaAtor cartaAtor = new ReferenciaCartaAtor(carta, new AtorCarta());
				referenciasCartasAtor.add(cartaAtor);
				cartaAtor.atorCarta.definirCarta(carta.tipo);
				cartaAtor.atorCarta.definirSelecionado(true);
				cena.desenharCarta(cartaAtor.atorCarta);
			}
		}
	
	// Bloco de notas
		public void desenharBlocoDeNotas(CenaBlocoNotas cena)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
			ArrayList<Carta> baralho = Baralho.todasCartas();
			referenciasCartasAtor.clear();
			
			for (Carta carta : baralho) {
				ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor( carta , new AtorCarta() );
				referenciasCartasAtor.add( referenciaCartaAtor );
				referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
				
				// Checa se o jogador tem a carta marcada em seu bloco de notas
				if( jogadorDaVez.temNota(carta) == true ) {
					referenciaCartaAtor.atorCarta.definirSelecionado(true);	
				}
				
				// Checa se a carta � uma certeza ( j� revelada por algum jogador ou na m�o do jogador atual )
				if( jogadorDaVez.temNotaCerteza(carta) == false ) {
					referenciaCartaAtor.atorCarta.addMouseListener( new MouseListener_cartaSelecaoMultipla(referenciaCartaAtor) );
				}	
				
				// Desenhar marcacao do tipo de carta ( feedback para o jogador )
				definirMarcadorCarta( referenciaCartaAtor , jogadorDaVez );
				
				cena.desenharCarta( referenciaCartaAtor.atorCarta );
			}
		}
		
		public void atualizarBlocoDeNotas()
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			
			for (ReferenciaCartaAtor carta : referenciasCartasAtor) {
				if(carta.atorCarta.getSelecionado() && !jogadorDaVez.temNota(carta.carta))
					jogadorDaVez.adicionarBlocoDeNotas(carta.carta);
				
				if(!carta.atorCarta.getSelecionado() && jogadorDaVez.temNota(carta.carta))
					jogadorDaVez.removerBlocoDeNotas(jogadorDaVez.obterNota(carta.carta));
			}
			System.out.println();
		}	
	
	// Menu: Palpite
		public void desenharMenuPalpite(CenaPalpite cena)
		{
			ArrayList<Carta> pilhaArmas = Baralho.pilhaArmas;
			ArrayList<Carta> pilhaSuspeitos = Baralho.pilhaSuspeitos;
			
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			referenciasCartasAtor.clear();
			
			for (Carta carta : pilhaSuspeitos) {
				desenharMenuPalpite_auxiliar_desenharCartaNaCena( carta , cena , true );
			}
			
			for (Carta carta : pilhaArmas) {
				desenharMenuPalpite_auxiliar_desenharCartaNaCena( carta , cena , true );
			}
			
			// Carta do comodo atual
			Carta carta = jogadorDaVez.obterCartaPosicao();
			desenharMenuPalpite_auxiliar_desenharCartaNaCena( carta , cena , false );
		}
		
	// Menu: Acusa��o	
		public void desenharAcusacao(CenaAcusacao cena)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
			ArrayList<Carta> baralho = Baralho.todasCartas();
			referenciasCartasAtor.clear();
			
			for (Carta carta : baralho) {
				ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor( carta , new AtorCarta() );
				referenciasCartasAtor.add( referenciaCartaAtor );
				referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
				
				// Adiciona o mouseListener para multipla sele��o
				referenciaCartaAtor.atorCarta.addMouseListener( new MouseListener_cartaSelecaoMultipla(referenciaCartaAtor) );	
				
				// Desenhar marcacao do tipo de carta ( feedback para o jogador )
				definirMarcadorCarta( referenciaCartaAtor , jogadorDaVez );
				
				cena.desenharCarta( referenciaCartaAtor.atorCarta );
			}
		}
		
		public void gerarAcusacao()
		{
			ArrayList<Carta> acusacao = new ArrayList<Carta>();
			
			for (ReferenciaCartaAtor cartaAtor : referenciasCartasAtor) {
				if(cartaAtor.atorCarta.getSelecionado())
					acusacao.add(cartaAtor.carta);
			}
			
			System.out.println(ControladoraDoJogo.getInstance().validarAcusacao(acusacao));
		}
		
		private void desenharMenuPalpite_auxiliar_desenharCartaNaCena( Carta carta , CenaPalpite cena , boolean addListener ) {
			ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor(carta, new AtorCarta());
			referenciasCartasAtor.add(referenciaCartaAtor);
			referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
			
			if( addListener == true ) {
				referenciaCartaAtor.atorCarta.addMouseListener(new MouseListener_cartaSelecaoUnica(referenciaCartaAtor));
			} else {
				referenciaCartaAtor.atorCarta.definirSelecionado(true);
			}
			
			cena.desenharCarta(referenciaCartaAtor.atorCarta);
			
			definirMarcadorCarta( referenciaCartaAtor , ControladoraDoJogo.getInstance().obterJogadorDaVez() );
		}
		
		public void gerarPalpite()
		{
			ArrayList<Carta> palpite = new ArrayList<Carta>();
			
			for (ReferenciaCartaAtor cartaAtor : referenciasCartasAtor) {
				if(cartaAtor.atorCarta.getSelecionado())
					palpite.add(cartaAtor.carta);
			}
			
			ControladoraDoJogo.getInstance().validarPalpite(palpite);
		}

	// Funcoes sobre as cartas
		private void definirMarcadorCarta( ReferenciaCartaAtor referenciaCartaAtor , Jogador jogadorDaVez ) {
			if( jogadorDaVez.temNota(referenciaCartaAtor.carta) == false ) {
				referenciaCartaAtor.atorCarta.definirMarcador( AtorCarta.TipoMarcador.SUSPEITO );
			}
			
			if( jogadorDaVez.temNotaCerteza(referenciaCartaAtor.carta) == true ) {
				referenciaCartaAtor.atorCarta.definirMarcador( AtorCarta.TipoMarcador.INOCENTE );
			}
		}
		
		public void desmarcarCartaTipo(Carta carta)
		{
			for (ReferenciaCartaAtor cartaAtor : referenciasCartasAtor) {
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
		
		
		public String obterArquivoCartaa(Carta carta)
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
