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
		pilhaArmas.add(new Carta(CartaEnum.DEATH_NOTE));
		pilhaArmas.add(new Carta(CartaEnum.DIAMANTE));
		pilhaArmas.add(new Carta(CartaEnum.FEDORA));
		pilhaArmas.add(new Carta(CartaEnum.BATRANGUE));
		pilhaArmas.add(new Carta(CartaEnum.CACHIMBO));
		pilhaArmas.add(new Carta(CartaEnum.REVOLVER));
		
		pilhaComodos.add(new Carta(CartaEnum.BIBLIOTECA));
		pilhaComodos.add(new Carta(CartaEnum.COZINHA));
		pilhaComodos.add(new Carta(CartaEnum.ENTRADA));
		pilhaComodos.add(new Carta(CartaEnum.ESCRITORIO));
		pilhaComodos.add(new Carta(CartaEnum.JARDIM_INVERNO));
		pilhaComodos.add(new Carta(CartaEnum.SALA_DE_ESTAR));
		pilhaComodos.add(new Carta(CartaEnum.SALA_DE_JANTAR));
		pilhaComodos.add(new Carta(CartaEnum.SALA_DE_MUSICA));
		pilhaComodos.add(new Carta(CartaEnum.SALA_DE_JOGOS));
		
		pilhaSuspeitos.add(new Carta(CartaEnum.L));
		pilhaSuspeitos.add(new Carta(CartaEnum.SHERLOCK));
		pilhaSuspeitos.add(new Carta(CartaEnum.CARMEN));
		pilhaSuspeitos.add(new Carta(CartaEnum.PANTERA));
		pilhaSuspeitos.add(new Carta(CartaEnum.EDMORT));
		pilhaSuspeitos.add(new Carta(CartaEnum.BATMAN));
		
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
	
	public Carta distribuirCarta()
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
