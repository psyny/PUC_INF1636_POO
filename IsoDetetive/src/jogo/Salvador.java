package jogo;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import atores.AtorJogador;
import estruturas.Vetor2D_double;
import interfaceGrafica.JanelaPrincipal;
import interfaceGrafica.QuadroJogo;
import jogo.Jogador;
import jogo.Jogador.Nota;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorJogadores;
import mediadores.TradutorJogadores.AtoresDoJogador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Salvador {
	// Singleton com LazyHolder para tratar threads
		private static class LazyHolder {
			static Salvador instance = new Salvador();
			
			private static void resetSingleton() {
				LazyHolder.instance = new Salvador();
			}
		}
		
		public static Salvador getInstance() {
			return LazyHolder.instance;
		}
		
		public static void resetSingleton() {
			LazyHolder.resetSingleton();
		}	
		
		// ----------------------------
		protected File file = null;
		
		public Salvador()
		{

		}
		
		public void configurarPath(String path)
		{
			file = new File(path);
		}
		
		public void configurarFile(File file)
		{
			this.file = file;
		}
		
		public void salvarPartida(ArrayList<Jogador> listaDeJogadores, ArrayList<Carta> crime, Jogador jogadorDaVez)
		{
			try
			{
				FileWriter fileWriter  = new FileWriter(file, false); 
				
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
					if(jogador.emJogo)
						fileWriter.write("1");
					else
						fileWriter.write("0");
					fileWriter.write("\n");
					if(listaDeJogadores.indexOf(jogador) != listaDeJogadores.size() - 1)
						fileWriter.write("Jogador\n");
				}
				
				fileWriter.write("Crime\n");
				for (Carta carta : crime) {
					fileWriter.write(carta.tipo.toString());
					fileWriter.write(",");
				}
				
				fileWriter.write("\n");
				fileWriter.write("JogadorDaVez\n");
				fileWriter.write(jogadorDaVez.personagem.nome);
				
				fileWriter.close();
			}	
			catch (Exception e) {
				System.out.println(e);
			}
		}
		
		public void carregarPartida()
		{
			try {
				//File reader
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				
				String line = reader.readLine();;
				String crime[], jogadorDaVez;
				
				//Criando um baralho
				Baralho baralho = new Baralho();
				
		        JanelaPrincipal.getInstance().carregarQuadro( new QuadroJogo(false) );
		        
		        MediadorFluxoDeJogo.getInstance().tradutorJogadores.inicializarAtoresDosJogadores();
				
				int indice = 0;
				while(!line.equals("Crime"))
				{
					String nome, posicao[], mao[], blocoDeNotas[], emJogo;
					
					nome = reader.readLine();
					posicao = reader.readLine().split(",");
					mao = reader.readLine().split(",");
					blocoDeNotas = reader.readLine().split(",");
					emJogo = reader.readLine();
					
					MediadorFluxoDeJogo.getInstance().adicionarNovoJogador( obterPersonagem(nome) );
					Jogador jogador = ControladoraDoJogo.getInstance().listaDeJogadores.get(indice);

					AtoresDoJogador atores = MediadorFluxoDeJogo.getInstance().tradutorJogadores.new AtoresDoJogador();
					atores.jogador = jogador;
					
					atores.atorJogador = new AtorJogador( jogador.obterPersonagem().obterEnum() );
					MediadorFluxoDeJogo.getInstance().cenaAtores.addActor( atores.atorJogador , 10 );

					Casa casaInicial = new Casa(Integer.parseInt(posicao[0]), Integer.parseInt(posicao[1]));
					jogador.definirPosicao( casaInicial );
					
					Vetor2D_double posicaoVirtual = MediadorFluxoDeJogo.getInstance().tradutorTabuleiro.obterCentroDaCasa( casaInicial.position.x , casaInicial.position.y );
					atores.atorJogador.setVirtualPosition( posicaoVirtual.x , posicaoVirtual.y );
					
					MediadorFluxoDeJogo.getInstance().tradutorJogadores.obterAtoresDosJogadores().add(atores);
					
					for (String string : mao) {
						jogador.mao.add(baralho.obterCarta(obterCarta(string)));
					}
					
					for (String string : blocoDeNotas) {
						Nota nota = jogador.new Nota(baralho.obterCarta( obterCarta( string.split("-")[0] ) ) , string.split("-")[1].equals("true") ? true : false );
						jogador.blocoDeNotas.add(nota);
					}
					
					jogador.emJogo = (emJogo.equals("1")) ? true : false;
					
					line = reader.readLine();
					indice++;
				}

				crime = reader.readLine().split(",");
				ControladoraDoJogo.getInstance().crime = new ArrayList<Carta>();
				for (String string : crime) {
					ControladoraDoJogo.getInstance().crime.add(baralho.obterCarta( obterCarta(string) ));
				}
				
				line = reader.readLine();

				jogadorDaVez = reader.readLine();
				for (Jogador jogador : ControladoraDoJogo.getInstance().listaDeJogadores) {
					if(jogador.personagem.personagem.equals(obterPersonagem(jogadorDaVez)))
					{
						int jogadorAnterior = ControladoraDoJogo.getInstance().listaDeJogadores.indexOf(jogador) - 1;
						if(jogadorAnterior == -1)
							jogadorAnterior = ControladoraDoJogo.getInstance().listaDeJogadores.size() - 1;
						ControladoraDoJogo.getInstance().jogadorDaVez = ControladoraDoJogo.getInstance().listaDeJogadores.get(jogadorAnterior);
						break;
					}
				}
				
				MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();

				reader.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private PersonagemEnum obterPersonagem(String nome)
		{
			switch (nome) 
			{
			
				case "L":
					return PersonagemEnum.L;
				case "Sherlock Holmes":
					return PersonagemEnum.SHERLOCK;
				case "Carmen San Diego":
					return PersonagemEnum.CARMEN;
				case "Pantera Cor-de-Rosa":
					return PersonagemEnum.PANTERA;
				case "Ed Mort":
					return PersonagemEnum.EDMORT;
				case "Batman":
					return PersonagemEnum.BATMAN;
	
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
}
