package jogo;

import java.util.ArrayList;

import jogo.Jogador.Nota;

public class Agente {

	private Jogador personagem;
	private ArrayList<CasaType> salasNaoVisitadas; 
	private ArrayList<Carta> crime;
	
	public Agente(Jogador personagem)
	{
		this.personagem = personagem;
		salasNaoVisitadas.add(CasaType.COZINHA);
		salasNaoVisitadas.add(CasaType.BIBLIOTECA);
		salasNaoVisitadas.add(CasaType.ENTRADA);
		salasNaoVisitadas.add(CasaType.ESCRITORIO);
		salasNaoVisitadas.add(CasaType.SL_ESTAR);
		salasNaoVisitadas.add(CasaType.SL_INVERNO);
		salasNaoVisitadas.add(CasaType.SL_JANTAR);
		salasNaoVisitadas.add(CasaType.SL_JOGOS);
		salasNaoVisitadas.add(CasaType.SL_MUSICA);
	}
	
	public Casa sugerirMovimento(ArrayList<Casa> casasPosiveis)
	{
		Casa melhorCasa = null;
		int melhorDistantcia = 100;
		
		//para cada casa possivel
		for (Casa casa : casasPosiveis) 
		{
			//tenta ir para uma sala não visitada, ou o mais proximo possivel
			if(salasNaoVisitadas.isEmpty())
			{
				if(salasNaoVisitadas.contains(casa.type))
				{
					return casa;
				}
				
				for(CasaType tipo : salasNaoVisitadas)
				{
					for(Casa candidato : ControladoraDoJogo.getInstance().tabuleiro.obterCasasAdjacentePorta(Casa.tipoSalaParaTipoPorta(tipo)))
					{
						if(casa.getManDistTo(candidato) < melhorDistantcia)
						{
							melhorDistantcia = casa.getManDistTo(candidato);
							melhorCasa = casa;
						}
					}
				}
			}
			else//e caso todas as salas ja foram visitadas, vai pra uma qualquer, ou o mais proximo possivel
			{
				if(casa.isRoom())
				{
					return casa;
				}
				
				for(Casa candidato : ControladoraDoJogo.getInstance().tabuleiro.obterCasasAdjacentePorta())
				{
					if(casa.getManDistTo(candidato) < melhorDistantcia)
					{
						melhorDistantcia = casa.getManDistTo(candidato);
						melhorCasa = casa;
					}
				}
			}
		}
		
		return melhorCasa;
	}
	
	public ArrayList<Carta> sugerirPalpite()
	{
		ArrayList<Carta> cartasDaNotas = new ArrayList<Carta>();
		ArrayList<Carta> palpite = new ArrayList<Carta>();
		
		//constroi lista auxiliar
		for(Nota nota : personagem.blocoDeNotas)
		{
			if(nota.certeza)
				cartasDaNotas.add(nota.carta);
		}
		
		//escolhe o suspeito
		for(Carta suspeito : Baralho.pilhaSuspeitos)
		{
			if(!cartasDaNotas.contains(suspeito))
			{
				palpite.add(suspeito);
				break;
			}
		}
		
		//escolhe o comodo
		for(Carta comodo : Baralho.pilhaComodos)
		{
			if(!cartasDaNotas.contains(comodo))
			{
				palpite.add(comodo);
				break;
			}
		}
		
		//escolhe a arma
		for(Carta arma : Baralho.pilhaArmas)
		{
			if(!cartasDaNotas.contains(arma))
			{
				palpite.add(arma);
				break;
			}
		}
		
		return palpite;
	}
	
	public void atualizarAgentePosPalpite(ArrayList<Carta> palpite, Carta cartaRevelada)
	{
		ArrayList<Carta> cartasDaNotas = new ArrayList<Carta>();
		ArrayList<Carta> comodos = new ArrayList<Carta>(Baralho.pilhaComodos);
		ArrayList<Carta> armas = new ArrayList<Carta>(Baralho.pilhaArmas);
		ArrayList<Carta> suspeitos = new ArrayList<Carta>(Baralho.pilhaSuspeitos);
		
		//constroi listas auxiliares
		for(Nota nota : personagem.blocoDeNotas)
		{
			if(nota.certeza)
				cartasDaNotas.add(nota.carta);

			if(nota.certeza && nota.carta.isComodo())
				comodos.remove(nota.carta);
			if(nota.certeza && nota.carta.isArma())
				armas.remove(nota.carta);
			if(nota.certeza && nota.carta.isSuspeito())
				suspeitos.remove(nota.carta);
		}
		
		//caso não tenha nem uma carta revelada, adiciona as cartas do palpite que n tem certeza no crime
		if(cartaRevelada == null)
		{
			for(Carta carta : palpite)
			{
				if(!cartasDaNotas.contains(carta))
					if(!crime.contains(carta))
						crime.add(carta);
			}
			return;
		}
		else
		{
			//caso a carta revelada seja um comodo, atualizarSalasNaoVisitadas
			if(cartaRevelada.isComodo())
				atualizarSalasNaoVisitadas(cartaRevelada);
		}
		
		//caso so tenha um comodo sem certeza, adiciona ele no crime
		if(comodos.size() == 1)
			if(!crime.contains(comodos.get(0)))
				crime.add(comodos.get(0));
		
		//caso so tenha uma arma sem certeza, adiciona ela no crime
		if(armas.size() == 1)
			if(!crime.contains(comodos.get(0)))
				crime.add(armas.get(0));
		
		//caso so tenha um suspeito sem certeza, adiciona ele no crime
		if(suspeitos.size() == 1)
			if(!crime.contains(comodos.get(0)))
				crime.add(suspeitos.get(0));
	}
	
	private void atualizarSalasNaoVisitadas(Carta comodo)
	{
		//remove a sala do comodo da lista de salasNaoVisitadas
		if(salasNaoVisitadas.contains(Carta.tipoCartaParaTipoCasa(comodo.tipo)))
		{
			salasNaoVisitadas.remove(Carta.tipoCartaParaTipoCasa(comodo.tipo));
		}
	}
	
	public ArrayList<Carta> sugerirAcusação()
	{
		//caso o crime tenha 3 cartas, retorna o crime
		if(crime.size() == 3)
			return crime;
		else//null caso contrario
			return null;
	}
}
