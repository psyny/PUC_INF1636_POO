package jogo;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import interfaceGrafica.JanelaPrincipal;
import interfaceGrafica.QuadroJogo;
import jogo.Jogador;
import jogo.Jogador.Nota;
import mediadores.MediadorFluxoDeJogo;

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
				
				System.out.println("FOI");
			}	
			catch (Exception e) {
				System.out.println(e);
			}
		}
		
		public void carregarPartida()
		{
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader reader = new BufferedReader(fileReader);
				
				String line;
				
				String crime[], jogadorDaVez;
				
				line = reader.readLine();
				
				Baralho baralho = new Baralho();
				
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
					jogador.posicao = new Casa(Integer.parseInt(posicao[0]), Integer.parseInt(posicao[1]));
					
					for (String string : mao) {
						jogador.mao.add(baralho.obterCarta(obterCarta(string)));
					}
					
					for (String string : blocoDeNotas) {
						Nota nota = jogador.new Nota(baralho.obterCarta( obterCarta( string.split("-")[0] ) ) , string.split("-")[1].equals("true") ? true : false );
						jogador.blocoDeNotas.add(nota);
					}
					
					jogador.emJogo = (emJogo == "1") ? true : false;
					
					line = reader.readLine();
					indice++;
				}

				
				crime = reader.readLine().split(",");
				for (String string : crime) {
					
				}
				
				line = reader.readLine();
				
				jogadorDaVez = reader.readLine();
				
				reader.close();
				
				JanelaPrincipal.getInstance().carregarQuadro( new QuadroJogo() );
				
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
					return PersonagemEnum.BATMAN;
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
		
		private CartaEnum obterCarta(String nome)
		{
			switch (nome) 
			{
			
				case "SALA_DE_JOGOS":
					return CartaEnum.SALA_DE_JOGOS;
				case "BATRANGUE":
					return CartaEnum.BATRANGUE;
				case "COZINHA":
					return CartaEnum.COZINHA;
				case "SALA_DE_ESTAR":
					return CartaEnum.SALA_DE_ESTAR;
				case "DIAMANTE":
					return CartaEnum.DIAMANTE;
				case "PANTERA":
					return CartaEnum.PANTERA;
				case "SHERLOCK":
					return CartaEnum.SHERLOCK;
				case "CARMEN":
					return CartaEnum.CARMEN;
				case "FEDORA":
					return CartaEnum.FEDORA;
				case "SALA_DE_JANTAR":
					return CartaEnum.SALA_DE_JANTAR;
				case "BIBLIOTECA":
					return CartaEnum.BIBLIOTECA;
				case "REVOLVER":
					return CartaEnum.REVOLVER;
				case "BATMAN":
					return CartaEnum.BATMAN;
				case "L":
					return CartaEnum.L;
				case "ENTRADA":
					return CartaEnum.ENTRADA;
				case "SALA_DE_MUSICA":
					return CartaEnum.SALA_DE_MUSICA;
				case "ESCRITORIO":
					return CartaEnum.ESCRITORIO;
				case "CACHIMBO":
					return CartaEnum.CACHIMBO;
				case "DEATH_NOTE":
					return CartaEnum.DEATH_NOTE;
				case "JARDIM_INVERNO":
					return CartaEnum.JARDIM_INVERNO;
				case "EDMORT":
					return CartaEnum.EDMORT;
	
				default:
					return null;
			}
		}
}
