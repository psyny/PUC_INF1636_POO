package mediadores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import animacao.Actor;
import animacao.Scene;
import atores.AtorBotaoMenuJogo;
import atores.AtorCarta;
import atores.AtorCarta.TipoMarcador;
import atores.CameraMenu.Modos;
import atores.CenaAcusacao;
import atores.CenaBlocoNotas;
import atores.CenaEscolhaCarta;
import atores.CenaFeedback;
import atores.CenaMao;
import atores.CenaPalpite;
import interfaceGrafica.JanelaPrincipal;
import jogo.Baralho;
import jogo.Carta;
import jogo.CartaType;
import jogo.ControladoraDoJogo;
import jogo.EstadoDoJogo;
import jogo.Jogador;
import jogo.PersonagemEnum;
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
	
	class MouseListener_cartaSelecaoUnicaTipada extends MouseAdapter {
		protected ReferenciaCartaAtor carta = null;
		protected int numeroDeCartasNecessario = 0;
		protected Actor confirma;
		
		public MouseListener_cartaSelecaoUnicaTipada(ReferenciaCartaAtor carta, Actor confirma) {
			this.carta = carta;
			this.confirma = confirma;
		}
		
		public MouseListener_cartaSelecaoUnicaTipada(ReferenciaCartaAtor carta, Actor confirma, int numeroDeCartasNecessario) {
			this.carta = carta;
			this.confirma = confirma;
			this.numeroDeCartasNecessario = numeroDeCartasNecessario;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			carta.atorCarta.definirSelecionado(!(carta.atorCarta.getSelecionado()));
			desmarcarOutrasCartaTipo(carta.carta);
			
			if(obeterNumeroCartasSelecionadas() >= numeroDeCartasNecessario)
				confirma.setVisible(true);
			else
				confirma.setVisible(false);
		}
	}
	
	class MouseListener_cartaSelecaoUnicaNaoTipada extends MouseAdapter {
		protected ReferenciaCartaAtor carta = null;
		protected int numeroDeCartasNecessario = 0;
		protected Actor confirma;
		
		public MouseListener_cartaSelecaoUnicaNaoTipada(ReferenciaCartaAtor carta, Actor confirma) {
			this.carta = carta;
			this.confirma = confirma;
		}
		
		public MouseListener_cartaSelecaoUnicaNaoTipada(ReferenciaCartaAtor carta, Actor confirma, int numeroDeCartasNecessario) {
			this.carta = carta;
			this.confirma = confirma;
			this.numeroDeCartasNecessario = numeroDeCartasNecessario;
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			carta.atorCarta.definirSelecionado(!(carta.atorCarta.getSelecionado()));
			desmarcarOutrasCarta(carta.carta);
			
			if(obeterNumeroCartasSelecionadas() >= numeroDeCartasNecessario)
				confirma.setVisible(true);
			else
				confirma.setVisible(false);
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
				
				// Checa se a carta é uma certeza ( já revelada por algum jogador ou na mão do jogador atual )
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
		
	// Menu: Escolha Carta	
		public void desenharEscolhaCarta(CenaEscolhaCarta cena)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
			ArrayList<Carta> cartas = ControladoraDoJogo.getInstance().obterJogadorReacao().temCartasNaMao(ControladoraDoJogo.getInstance().obterPalpiteReacao());
			referenciasCartasAtor.clear();
			
			for (Carta carta : cartas) {
				ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor( carta , new AtorCarta() );
				referenciasCartasAtor.add( referenciaCartaAtor );
				referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
				referenciaCartaAtor.atorCarta.definirMarcador( TipoMarcador.VAZIO );
				
				// Adiciona o mouseListener para multipla seleção
				referenciaCartaAtor.atorCarta.addMouseListener( new MouseListener_cartaSelecaoUnicaNaoTipada(referenciaCartaAtor, cena.getConfirma(), 1) );	
				
				// Desenhar marcacao do tipo de carta ( feedback para o jogador )
				definirMarcadorCarta( referenciaCartaAtor , jogadorDaVez );
				
				cena.desenharCarta( referenciaCartaAtor.atorCarta );
			}
			
			PersonagemEnum personagemEnum = ControladoraDoJogo.getInstance().obterJogadorReacao().obterPersonagem().obterEnum();
			AtorBotaoMenuJogo.Tipo tipoBotao = TradutorJogadores.converterPersonagemEnumTipoBotao(personagemEnum);
			AtorBotaoMenuJogo atorBotao = new AtorBotaoMenuJogo( tipoBotao );
			cena.desenharPersonagem( atorBotao );
		}
		
	// Menu: Feedback	
		public void desenharFeedback(CenaFeedback cena, Carta carta)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
			referenciasCartasAtor.clear();

			ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor( carta , new AtorCarta() );
			referenciasCartasAtor.add( referenciaCartaAtor );
			referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
			referenciaCartaAtor.atorCarta.definirSelecionado( true );
			
			// Desenhar marcacao do tipo de carta ( feedback para o jogador )
			definirMarcadorCarta( referenciaCartaAtor , jogadorDaVez );
			
			cena.desenharCarta( referenciaCartaAtor.atorCarta );
			
			PersonagemEnum personagemEnum = ControladoraDoJogo.getInstance().obterJogadorReacao().obterPersonagem().obterEnum();
			AtorBotaoMenuJogo.Tipo tipoBotao = TradutorJogadores.converterPersonagemEnumTipoBotao(personagemEnum);
			AtorBotaoMenuJogo atorBotao = new AtorBotaoMenuJogo( tipoBotao );
			cena.desenharPersonagem( atorBotao );
		}

		public void registrarCartaEscolhida()
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			Carta carta = null;
			
			for (ReferenciaCartaAtor referenciaCartaAtor : referenciasCartasAtor) {
				if(referenciaCartaAtor.atorCarta.getSelecionado())
				{
					jogadorDaVez.adicionarBlocoDeNotas(referenciaCartaAtor.carta, true);
					carta = referenciaCartaAtor.carta;
					break;
				}
			}
			
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.FEEDBACK);
			desenharFeedback(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaFeedback , carta);
		}
	
	// Menu: Acusação	
		public void desenharAcusacao(CenaAcusacao cena)
		{
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
		
			ArrayList<Carta> baralho = Baralho.todasCartas();
			referenciasCartasAtor.clear();
			
			for (Carta carta : baralho) {
				ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor( carta , new AtorCarta() );
				referenciasCartasAtor.add( referenciaCartaAtor );
				referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
				
				// Adiciona o mouseListener para multipla seleção
				referenciaCartaAtor.atorCarta.addMouseListener( new MouseListener_cartaSelecaoUnicaTipada(referenciaCartaAtor, cena.getConfirma(), 3) );	
				
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
			
			ControladoraDoJogo.getInstance().validarAcusacao(acusacao);
			MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
		}
		
		private void desenharMenuPalpite_auxiliar_desenharCartaNaCena( Carta carta , CenaPalpite cena , boolean addListener ) {
			ReferenciaCartaAtor referenciaCartaAtor = new ReferenciaCartaAtor(carta, new AtorCarta());
			referenciasCartasAtor.add(referenciaCartaAtor);
			referenciaCartaAtor.atorCarta.definirCarta( carta.tipo );
			
			if( addListener == true ) {
				referenciaCartaAtor.atorCarta.addMouseListener(new MouseListener_cartaSelecaoUnicaTipada(referenciaCartaAtor, cena.getConfirma(), 3));
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
			MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.ESCOLHA_CARTA);
			desenharEscolhaCarta(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaEscolhaCarta);
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
		
		public void desmarcarOutrasCartaTipo(Carta carta)
		{
			for (ReferenciaCartaAtor cartaAtor : referenciasCartasAtor) {
				if(mesmoTipoCarta(cartaAtor.carta, carta) && !cartaAtor.carta.equals(carta))
				{
					cartaAtor.atorCarta.definirSelecionado(false);
				}
			}
		}
		
		public void desmarcarOutrasCarta(Carta carta)
		{
			for (ReferenciaCartaAtor cartaAtor : referenciasCartasAtor) {
				if(!cartaAtor.carta.equals(carta))
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
		
		//retorna quantas cartas da cena estão selecionadas
		public int obeterNumeroCartasSelecionadas()
		{
			int numeroCartasSelecionadas = 0;
			for (ReferenciaCartaAtor referenciaCartaAtor : referenciasCartasAtor) {
				if(referenciaCartaAtor.atorCarta.getSelecionado())
					numeroCartasSelecionadas++;
			}
			return numeroCartasSelecionadas;
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
		
		
	// Salvar Jogo
		public void salvarJogo() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog( JanelaPrincipal.getInstance() );
			
			File file = fileChooser.getSelectedFile();
			
			ControladoraDoJogo.getInstance().salvarPartidaEmArquivo( file );
		}
		
	// Carregar Jogo
		public void carregarJogo() {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog( JanelaPrincipal.getInstance() );
			
			// Cria o novo estado do jogo
			EstadoDoJogo estadoDoJogo = new EstadoDoJogo();
			estadoDoJogo.carregarPartidaDoArquivo( fileChooser.getSelectedFile() );
			
			ControladoraDoJogo.getInstance().definirEstadoDoJogo(estadoDoJogo);
		}		
}
