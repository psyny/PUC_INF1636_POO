package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo.EstadoDaJogada;
import mediadores.MediadorFluxoDeJogo;

@SuppressWarnings("serial")
public class AtorBotaoMenuJogo extends Actor {
	public enum Tipo {
		PERSONAGEM_L,
		PERSONAGEM_SHERLOCK,
		PERSONAGEM_PANTERA,
		PERSONAGEM_CARMEN,
		PERSONAGEM_EDMORT,
		PERSONAGEM_BATMAN,
		
		BOTAO_DADO,
		BOTAO_MOVER,
		BOTAO_ACUSAR,
		BOTAO_MAO,
		BOTAO_PASSAR,
		BOTAO_FECHAR,
		BOTAO_NOTAS,
		
		DESCONHECIDO
	}
	
	private class mouseListener_movimento extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent arg0)  {
			if( mouseOverChange == true ) {
				botao.playAnimation(3, true);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent arg0)  {
			if( mouseOverChange == true ) {
				botao.playAnimation(1, true);
			}
		}
	}
	
	private AnimatedSprite 	botao;
	private boolean 		mouseOverChange = false;
	private AtorBotaoMenuJogo.Tipo tipo = AtorBotaoMenuJogo.Tipo.DESCONHECIDO;

	public AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo tipo ) {
		super(100,100);
		
		this.tipo = tipo;
		
		String botaoFile;
		switch( tipo ) {
			case PERSONAGEM_L:
				mouseOverChange = false;
				botaoFile = "botao_l.txt";
				break;
				
			case PERSONAGEM_SHERLOCK:
				mouseOverChange = false;
				botaoFile = "botao_sherlock.txt";
				break;
				
			case PERSONAGEM_PANTERA:
				mouseOverChange = false;
				botaoFile = "botao_pantera.txt";
				break;
				
			case PERSONAGEM_CARMEN:
				mouseOverChange = false;
				botaoFile = "botao_carmen.txt";
				break;
				
			case PERSONAGEM_EDMORT:
				mouseOverChange = false;
				botaoFile = "botao_edmort.txt";
				break;
				
			case PERSONAGEM_BATMAN:
				mouseOverChange = false;
				botaoFile = "botao_batman.txt";
				break;
				
			case BOTAO_DADO:
				mouseOverChange = true;
				botaoFile = "botao_dado.txt";
				break;
				
			case BOTAO_MOVER:
				mouseOverChange = true;
				botaoFile = "botao_mover.txt";
				break;
				
			case BOTAO_ACUSAR:
				mouseOverChange = true;
				botaoFile = "botao_acusar.txt";
				break;		
				
			case BOTAO_MAO:
				mouseOverChange = true;
				botaoFile = "botao_mao.txt";
				break;
				
			case BOTAO_PASSAR:
				mouseOverChange = true;
				botaoFile = "botao_passar.txt";
				break;
				
			case BOTAO_NOTAS:
				mouseOverChange = true;
				botaoFile = "botao_blocodenotas.txt";
				break;
				
			case BOTAO_FECHAR:
				mouseOverChange = false;
				botaoFile = "botao_fechar.txt";
				break;
				
			default:
				mouseOverChange = false;
				botaoFile = "botao_fechar.txt";
				break;
		}
		
		adicionarDoArquivo(botaoFile);
	}
	
	public AtorBotaoMenuJogo( String botaoFile ) {
		super(100, 100);
		adicionarDoArquivo(botaoFile);
	}
	
	private void adicionarDoArquivo( String botaoFile ) {
		this.addMouseListener( new mouseListener_movimento() );
		this.botao = addAnimatedSprite( botaoFile , new Vetor2D_int(0,0) , 0 );	
	}
	
	public AtorBotaoMenuJogo.Tipo obterTipo() {
		return this.tipo;
	}

}
