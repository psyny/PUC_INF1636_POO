package jogo;

import java.util.ArrayList;

import jogo.Jogador.Nota;

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
				
				String crime, jogadorDaVez;
				
				line = reader.readLine();
				
				while(!line.equals("Crime"))
				{
					String nome, posicao, mao, blocoDeNotas, emJogo;
					
					nome = reader.readLine();
					posicao = reader.readLine();
					mao = reader.readLine();
					blocoDeNotas = reader.readLine();
					emJogo = reader.readLine();
					
					//TODO - inserir registros
					
					line = reader.readLine();
				}
				
				crime = reader.readLine();
				line = reader.readLine();
				jogadorDaVez = reader.readLine();
				
				reader.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
}
