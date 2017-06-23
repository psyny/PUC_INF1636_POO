package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo.EstadoDaJogada;
import mediadores.MediadorFluxoDeJogo;

@SuppressWarnings("serial")
public class AtorBotaoMenuPreJogo extends Actor {
	public enum Tipo {
		INDEFINIDO,
		NOVO_JOGO,
		CONTINUAR_JOGO
	}
	
	private class mouseListener_movimento extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent arg0)  {
			if( mouseOverChange == true ) {
				botao.playAnimation(2, true);
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
	private AtorBotaoMenuPreJogo.Tipo tipo = AtorBotaoMenuPreJogo.Tipo.NOVO_JOGO;

	public AtorBotaoMenuPreJogo( AtorBotaoMenuPreJogo.Tipo tipo ) {
		super(100,100);
		
		this.tipo = tipo;
		mouseOverChange = true;
		
		String botaoFile;
		switch( tipo ) {
			case NOVO_JOGO:
				botaoFile = "botao_novojogo.txt";
				break;
				
			case CONTINUAR_JOGO:
				botaoFile = "botao_continuar.txt";
				break;
				
			case INDEFINIDO:
			default:
				mouseOverChange = false;
				botaoFile = "faltando.txt";
				break;
		}
		
		adicionarDoArquivo(botaoFile);
	}
	
	public AtorBotaoMenuPreJogo( String botaoFile ) {
		super(100, 100);
		adicionarDoArquivo(botaoFile);
	}
	
	private void adicionarDoArquivo( String botaoFile ) {
		this.addMouseListener( new mouseListener_movimento() );
		this.botao = addAnimatedSprite( botaoFile , new Vetor2D_int(0,0) , 0 );	
		
		autoSize();
		posicaoAncora = Actor.PosicaoAncora.CENTRO;
	}
	
	public AtorBotaoMenuPreJogo.Tipo obterTipo() {
		return this.tipo;
	}

}
