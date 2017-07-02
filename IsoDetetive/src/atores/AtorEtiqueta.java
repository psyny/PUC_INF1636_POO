package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animacao.*;
import estruturas.Vetor2D_int;
import mediadores.MediadorFluxoDeJogo;

@SuppressWarnings("serial")
public class AtorEtiqueta extends Actor {
	public enum Tipo {
		INDEFINIDO,
		JOGO_DOS_DETETIVES,
		SELECAO_DE_PERSONAGENS,
		ACUSAR,
		BLOCO_DE_NOTAS,
		SUA_MAO,
		FACA_SEU_PALPITE		
	}
	
	private class mouseListener_movimento extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent arg0)  {
			if( mouseOverChange == true ) {
				etiquetaSprite.playAnimation(2, true);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent arg0)  {
			if( mouseOverChange == true ) {
				etiquetaSprite.playAnimation(1, true);
			}
		}
	}
	
	private AnimatedSprite 	etiquetaSprite;
	private boolean 		mouseOverChange = false;
	private AtorEtiqueta.Tipo tipo = AtorEtiqueta.Tipo.INDEFINIDO;

	public AtorEtiqueta( AtorEtiqueta.Tipo tipo ) {
		super(100,100);
		
		this.tipo = tipo;
		
		String botaoFile;
		switch( tipo ) {
			case JOGO_DOS_DETETIVES:
				botaoFile = "label_titulo.txt";
				break;
				
			case SELECAO_DE_PERSONAGENS:
				botaoFile = "label_selecaopersonagens.txt";
				break;
				
			case ACUSAR:
				botaoFile = "label_acusar.txt";
				break;
				
			case BLOCO_DE_NOTAS:
				botaoFile = "label_bloco.txt";
				break;
				
			case SUA_MAO:
				botaoFile = "label_mao.txt";
				break;
				
			case INDEFINIDO:
			default:
				botaoFile = "faltando.txt";
				break;
		}
		
		adicionarDoArquivo(botaoFile);
	}
	
	public AtorEtiqueta( String botaoFile ) {
		super(100, 100);
		adicionarDoArquivo(botaoFile);
	}
	
	private void adicionarDoArquivo( String botaoFile ) {
		addMouseListener( new mouseListener_movimento() );
		etiquetaSprite = addAnimatedSprite( botaoFile , new Vetor2D_int(0,0) , 0 );	
		
		autoSize();
		posicaoAncora = Actor.PosicaoAncora.SUPERIOR_ESQUERDO;
	}
	
	public AtorEtiqueta.Tipo obterTipo() {
		return tipo;
	}

}
