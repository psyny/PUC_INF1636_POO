package jogo_Nucleo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import jogo_Nucleo.Jogador.Nota;
import jogo_TiposEnumerados.*;

public class EstadoDoJogo {
	public static class TipoNovoJogador
	{
		public PersonagemType personagemEnum;
		public boolean emJogo;
		public boolean inteligenciaArtificial = false;
	}
	
	// Dados de controle
	protected Tabuleiro 		tabuleiro = null;
	protected Baralho 			baralho;	
	protected boolean			opcoesDeJogadaCalculadas = false;
	
	// Dados a serem armazenados e caregados
	protected String 			tabuleiroArquivoOrigem = "";
	protected ArrayList<Jogador> listaDeJogadores;
	protected Jogador 			jogadorDaVez = null;
	protected int				valorDoDado = 0;
	protected ArrayList<Carta> 	crime;
	
	protected ArrayList<AcoesPossiveisType>	acoesPossiveis = new ArrayList<AcoesPossiveisType>();
	
	
	// ------------------------------------------------------------------------
	
	public EstadoDoJogo() {
		listaDeJogadores = new ArrayList<Jogador>();
	}
	
	public void gerarEstadoInicial( ArrayList<TipoNovoJogador> listaDePersonagens , String arquivoTabuleiro ) {
		// Importante carregar o tabuleiro primeiro para obter dados do posicionamento dos personagens
		this.carregarTabuleiro( arquivoTabuleiro );
		
		// Cria e posiciona os jogadores 
		listaDeJogadores = new ArrayList<Jogador>();
		for( PersonagemType personagemEnum : PersonagemType.values() ) {
			TipoNovoJogador novoJogador = null;
			
			for (TipoNovoJogador tipoNovoJogador : listaDePersonagens) {
				if(tipoNovoJogador.personagemEnum == personagemEnum)
				{
					novoJogador = tipoNovoJogador;
					break;
				}
			}
			
			Jogador jogador = new Jogador( personagemEnum, novoJogador.inteligenciaArtificial );
			jogador.emJogo = novoJogador.emJogo;
			
			jogador.definirPosicao( PersonagemLista.getInstance().obterPersonagem(personagemEnum).obterCasaInicial() );
			
			this.listaDeJogadores.add( jogador );	
		}	
		
		// Configuracoes iniciais de uma jogada				
		jogadorDaVez 	= this.obterProximoJogador();
		valorDoDado 	= 0;

		// Configura baralho e cartas
		baralho = new Baralho();
		crime = baralho.gerarCrime();		
		
		if( ControladoraDoJogo.getInstance().debug_mode == true ) {
			System.out.println("DEBUG: Crime: ");
			for (Carta carta : crime) {
				System.out.println(carta.tipo);
			}
		}
		
		// Distribui as cartas para os jogadores
		while( baralho.baralho.size() > 0) {
			for (Jogador jogador : listaDeJogadores) {
				if( jogador.emJogo )
					jogador.adicionarCartaAMao( baralho.comprarCarta() );
				if( baralho.baralho.size() == 0)
					break;
			}
		}
		
		// Variaveis de estado
		opcoesDeJogadaCalculadas = false;
	}
	
	private void carregarTabuleiro( String arquivoTabuleiro ) {

		if( arquivoTabuleiro == null || arquivoTabuleiro.length() < 4 ) {
			arquivoTabuleiro = "tabuleiro_oficial.txt";
		}
		
		this.tabuleiroArquivoOrigem = arquivoTabuleiro;
		this.tabuleiro = GeradorDeTabuleiros.carregarDoArquivo( arquivoTabuleiro );
	}
	
	
	public Jogador obterProximoJogador() {
		return this.obterProximoJogador( true , jogadorDaVez );
	}

	public Jogador obterProximoJogador( boolean emJogo , Jogador jogadorReferente ) {
		// Descobrir quem é o proximo jogador
		if( jogadorReferente == null ) {
			// Nao existe, entao e o primeiro jogador ativo da lista
			for( Jogador jogador : listaDeJogadores ) {
				if( jogador.emJogo == emJogo ) {
					return jogador;
				}
			}
			
			return null;
		} else {
			// Descobrir proximo jogador
			int idx = listaDeJogadores.indexOf( jogadorReferente ); 
			int idxCandidato;
			
			if( idx == listaDeJogadores.size() - 1 ) {
				idxCandidato = 0;
			} else {
				idxCandidato = idx + 1;
			}
			
			while( ( listaDeJogadores.get(idxCandidato)).emJogo == false) {
				idxCandidato++;
				if(idxCandidato == listaDeJogadores.size())
					idxCandidato = 0;
			}
			
			return listaDeJogadores.get(idxCandidato);
		}
	}
	
	
	public ArrayList<Jogador> obterProximosJogadores( ) {
		return this.obterProximosJogadores( jogadorDaVez );
	}
	
	public ArrayList<Jogador> obterProximosJogadores( Jogador jogadorReferente ) {
		ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
		
		Jogador inicial = jogadorReferente;
		Jogador atual = this.obterProximoJogador( false , jogadorReferente );
		while( atual != inicial ) {
			jogadores.add( atual );
			atual = this.obterProximoJogador( false , atual );
		}
		
		return jogadores;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void salvarEstadoEmArquivo( File file )
	{
		try
		{
			FileWriter fileWriter  = new FileWriter(file, false); 
			
			// Tabuleiro
			fileWriter.write("Tabuleiro\n");
			fileWriter.write( this.tabuleiroArquivoOrigem );
			fileWriter.write("\n");
			
			// Dados dos jogadores
			fileWriter.write("Jogador\n");
			for (Jogador jogador : listaDeJogadores) {
				fileWriter.write(jogador.personagem.nome);
				fileWriter.write("\n");
				fileWriter.write(jogador.posicao.position.x + "," + jogador.posicao.position.y);
				fileWriter.write("\n");
				for (Carta carta : jogador.mao) {
					fileWriter.write(carta.tipo.toString());
					fileWriter.write(",");
				}
				fileWriter.write("\n");
				for(Nota nota : jogador.blocoDeNotas)
				{
					fileWriter.write(nota.carta.tipo.toString() + '-' + nota.certeza);
					fileWriter.write(",");
				}
				fileWriter.write("\n");
				if(jogador.moveuSeForcadamente)
					fileWriter.write("1");
				else
					fileWriter.write("0");
				fileWriter.write("\n");
				if(jogador.emJogo)
					fileWriter.write("1");
				else
					fileWriter.write("0");
				fileWriter.write("\n");
				if(listaDeJogadores.indexOf(jogador) != listaDeJogadores.size() - 1)
					fileWriter.write("Jogador\n");
			}
			
			// Dados do Crime
			fileWriter.write("Crime\n");
			for (Carta carta : crime) {
				fileWriter.write(carta.tipo.toString());
				fileWriter.write(",");
			}
			
			// Estado da Partida
			fileWriter.write("\n");
			fileWriter.write("JogadorDaVez\n");
			fileWriter.write( Integer.toString( listaDeJogadores.indexOf( jogadorDaVez ) ) );
			
			
			// Acoes disponiveis
			fileWriter.write("\n\n");
			fileWriter.write("AcoesDisponiveis\n");
			for( AcoesPossiveisType acao : this.acoesPossiveis ) {
				fileWriter.write( this.converterAcaoParaString( acao ) );
				fileWriter.write("\n");
			}
			fileWriter.write("<FIM>");
			
			fileWriter.close();
		}	
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
	public void carregarPartidaDoArquivo( File file )
	{
		
		try {
			//File reader
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = reader.readLine();
			String crimeString[];
			
			// Criando um baralho
			Baralho baralho = new Baralho();		
			
			// Le Tabuleiro
			String arquivoTabuleiro = reader.readLine();
			this.carregarTabuleiro( arquivoTabuleiro );

	        // Carregando Personagens
			int indice = 0;
			line = reader.readLine();
			while(!line.equals("Crime"))
			{
				String nome, posicao[], mao[], blocoDeNotas[], moveuSeForcadamente, emJogo;
				
				nome = reader.readLine();
				posicao = reader.readLine().split(",");
				mao = reader.readLine().split(",");
				blocoDeNotas = reader.readLine().split(",");
				moveuSeForcadamente = reader.readLine();
				emJogo = reader.readLine();
				
				// Novos personagens
				Jogador jogador = new Jogador( obterPersonagem(nome), false );

				// Posicao
				int x = Integer.parseInt(posicao[0]);
				int y = Integer.parseInt(posicao[1]);
				Casa casaAtual = this.tabuleiro.getCell(x, y);
				jogador.definirPosicao( casaAtual );
				
				// Em jogo?
				jogador.emJogo = (emJogo.equals("1")) ? true : false;
				
				//moveu se forçadamente
				jogador.moveuSeForcadamente = (moveuSeForcadamente.equals("1")) ? true : false;
				
				// Carregando as cartas que o jogador tem ( mao )
				for (String string : mao) {
					if(!string.equals(""))
					{
						jogador.mao.add(baralho.obterCarta(obterCarta(string)));
					}
				}
				
				// Carregando as notas feitas pelo jogador
				for (String string : blocoDeNotas) {
					if(!string.equals(""))
					{
						Nota nota = jogador.new Nota(baralho.obterCarta( obterCarta( string.split("-")[0] ) ) , string.split("-")[1].equals("true") ? true : false );
						jogador.blocoDeNotas.add(nota);
					}
				}
				
				// Adiciona novo jogador a lista de jogadores
				this.listaDeJogadores.add( jogador );
	
				// Loopando
				line = reader.readLine();
				indice++;
			}

			// Carregando dados sobre o crime
			crimeString = reader.readLine().split(",");
			this.crime = new ArrayList<Carta>();
			for (String string : crimeString) {
				this.crime.add(baralho.obterCarta( obterCarta(string) ));
			}
	
			// Jogador da Vez
			line = reader.readLine();
			int jogadorDaVezIndice = Integer.parseInt( reader.readLine() );
			jogadorDaVez = listaDeJogadores.get( jogadorDaVezIndice );
			
			//Etapa da jogada
			this.acoesPossiveis = new ArrayList<AcoesPossiveisType>();
			this.opcoesDeJogadaCalculadas = true;
			line = reader.readLine();
			line = reader.readLine();
			line = reader.readLine();
			while( line.compareTo( "<FIM>") != 0  ) {
				System.out.println(line);
				this.acoesPossiveis.add( converterStringParaAcao(line) );
				line = reader.readLine();
			}
			
			// Outros dados
			valorDoDado 	= 0;
			
			reader.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}	
	
	private PersonagemType obterPersonagem(String nome)
	{
		switch (nome) 
		{
		
			case "L":
				return PersonagemType.L;
			case "Sherlock Holmes":
				return PersonagemType.SHERLOCK;
			case "Carmen San Diego":
				return PersonagemType.CARMEN;
			case "Pantera Cor-de-Rosa":
				return PersonagemType.PANTERA;
			case "Ed Mort":
				return PersonagemType.EDMORT;
			case "Batman":
				return PersonagemType.BATMAN;

			default:
				return null;
		}
	}
	
	private CartaType obterCarta(String nome)
	{
		switch (nome) 
		{
		
			case "SALA_DE_JOGOS":
				return CartaType.SALA_DE_JOGOS;
			case "BATRANGUE":
				return CartaType.BATRANGUE;
			case "COZINHA":
				return CartaType.COZINHA;
			case "SALA_DE_ESTAR":
				return CartaType.SALA_DE_ESTAR;
			case "DIAMANTE":
				return CartaType.DIAMANTE;
			case "PANTERA":
				return CartaType.PANTERA;
			case "SHERLOCK":
				return CartaType.SHERLOCK;
			case "CARMEN":
				return CartaType.CARMEN;
			case "FEDORA":
				return CartaType.FEDORA;
			case "SALA_DE_JANTAR":
				return CartaType.SALA_DE_JANTAR;
			case "BIBLIOTECA":
				return CartaType.BIBLIOTECA;
			case "REVOLVER":
				return CartaType.REVOLVER;
			case "BATMAN":
				return CartaType.BATMAN;
			case "L":
				return CartaType.L;
			case "ENTRADA":
				return CartaType.ENTRADA;
			case "SALA_DE_MUSICA":
				return CartaType.SALA_DE_MUSICA;
			case "ESCRITORIO":
				return CartaType.ESCRITORIO;
			case "CACHIMBO":
				return CartaType.CACHIMBO;
			case "DEATH_NOTE":
				return CartaType.DEATH_NOTE;
			case "JARDIM_INVERNO":
				return CartaType.JARDIM_INVERNO;
			case "EDMORT":
				return CartaType.EDMORT;

			default:
				return null;
		}
	}	
	
	private String converterAcaoParaString( AcoesPossiveisType acao ) {
		switch( acao ) {
		case ACUSAR:
			return "ACUSAR";

		case MOVER:
			return "MOVER";

		case NEGAR_PALPITE:
			return "NEGAR_PALPITE";
			
		case PALPITE:
			return "PALPITE";
			
		case PASSAR_A_VEZ:
			return "PASSAR_A_VEZ";
			
		case ROLAR_DADOS:
			return "ROLAR_DADOS";

		case SALVAR:
			return "SALVAR";
			
		case VER_MAO:
			return "VER_MAO";
			
		case VER_NOTAS:
			return "VER_NOTAS";
			
		default:
			return "";
		
		}
	}
	
	private AcoesPossiveisType converterStringParaAcao( String string ) {
		switch( string ) {
		case "ACUSAR":
			return AcoesPossiveisType.ACUSAR;

		case "MOVER":
			return AcoesPossiveisType.MOVER;

		case "NEGAR_PALPITE":
			return AcoesPossiveisType.NEGAR_PALPITE;
			
		case "PALPITE":
			return AcoesPossiveisType.PALPITE;
			
		case "PASSAR_A_VEZ":
			return AcoesPossiveisType.PASSAR_A_VEZ;
			
		case "ROLAR_DADOS":
			return AcoesPossiveisType.ROLAR_DADOS;

		case "SALVAR":
			return AcoesPossiveisType.SALVAR;
			
		case "VER_MAO":
			return AcoesPossiveisType.VER_MAO;
			
		case "VER_NOTAS":
			return AcoesPossiveisType.VER_NOTAS;
			
		default:
			return null;
		
		}
	}
	
}
