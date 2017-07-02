package jogo;

import java.util.ArrayList;
import java.util.Random;

public class Baralho {
	
	public static ArrayList<Carta> pilhaArmas = new ArrayList<Carta>();
	public static ArrayList<Carta> pilhaComodos = new ArrayList<Carta>();
	public static ArrayList<Carta> pilhaSuspeitos = new ArrayList<Carta>();
	
	public ArrayList<Carta> baralho = new ArrayList<Carta>();
	
	public Baralho()
	{
		pilhaArmas.add(new Carta(CartaType.DEATH_NOTE));
		pilhaArmas.add(new Carta(CartaType.DIAMANTE));
		pilhaArmas.add(new Carta(CartaType.FEDORA));
		pilhaArmas.add(new Carta(CartaType.BATRANGUE));
		pilhaArmas.add(new Carta(CartaType.CACHIMBO));
		pilhaArmas.add(new Carta(CartaType.REVOLVER));
		
		pilhaComodos.add(new Carta(CartaType.BIBLIOTECA));
		pilhaComodos.add(new Carta(CartaType.COZINHA));
		pilhaComodos.add(new Carta(CartaType.ENTRADA));
		pilhaComodos.add(new Carta(CartaType.ESCRITORIO));
		pilhaComodos.add(new Carta(CartaType.JARDIM_INVERNO));
		pilhaComodos.add(new Carta(CartaType.SALA_DE_ESTAR));
		pilhaComodos.add(new Carta(CartaType.SALA_DE_JANTAR));
		pilhaComodos.add(new Carta(CartaType.SALA_DE_MUSICA));
		pilhaComodos.add(new Carta(CartaType.SALA_DE_JOGOS));
		
		pilhaSuspeitos.add(new Carta(CartaType.L));
		pilhaSuspeitos.add(new Carta(CartaType.SHERLOCK));
		pilhaSuspeitos.add(new Carta(CartaType.CARMEN));
		pilhaSuspeitos.add(new Carta(CartaType.PANTERA));
		pilhaSuspeitos.add(new Carta(CartaType.EDMORT));
		pilhaSuspeitos.add(new Carta(CartaType.BATMAN));
		
		baralho.addAll(pilhaArmas);
		baralho.addAll(pilhaComodos);
		baralho.addAll(pilhaSuspeitos);
	}
	
	public ArrayList<Carta> gerarCrime()
	{
		Random random = new Random();
		ArrayList<Carta> crime = new ArrayList<Carta>();
		
		int armaEscolhida = random.nextInt(pilhaArmas.size());
		int comodoEscolhido = random.nextInt(pilhaArmas.size());
		int suspeitoEscolhido = random.nextInt(pilhaArmas.size());
		
		crime.add(pilhaArmas.get(armaEscolhida));
		baralho.remove(pilhaArmas.get(armaEscolhida));
		
		crime.add(pilhaComodos.get(comodoEscolhido));
		baralho.remove(pilhaComodos.get(comodoEscolhido));

		crime.add(pilhaSuspeitos.get(suspeitoEscolhido));
		baralho.remove(pilhaSuspeitos.get(suspeitoEscolhido));
		
		return crime;
	}
	
	public ArrayList<Carta> distribuirMao(int n_cartas)
	{
		Random random = new Random();
		ArrayList<Carta> mao = new ArrayList<Carta>();
		
		for (int i = 0; i < n_cartas; i++) {
			int cartaEscolhida = random.nextInt(baralho.size());
			mao.add(baralho.get(cartaEscolhida));
			baralho.remove(cartaEscolhida);
		}
		
		return mao;
	}
	
	public Carta obterCarta(CartaType nome)
	{
		for (Carta carta : baralho) {
			if(carta.tipo == nome)
				return carta;
		}
		return null;
	}
	
	public Carta comprarCarta()
	{
		Random random = new Random();
		Carta carta;
		
		int cartaEscolhida = random.nextInt(baralho.size());
		carta = baralho.get(cartaEscolhida);
		baralho.remove(cartaEscolhida);
		
		return carta;
	}
	
	public static ArrayList<Carta> todasCartas()
	{
		ArrayList<Carta> baralho = new ArrayList<Carta>();
		
		baralho.addAll(pilhaArmas);
		baralho.addAll(pilhaComodos);
		baralho.addAll(pilhaSuspeitos);
		
		return baralho;
	}
}
