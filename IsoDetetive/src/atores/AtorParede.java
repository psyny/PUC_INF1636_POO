package atores;

import java.util.Random;

import animacao.*;
import estruturas.Vetor2D_int;
import jogo.Tabuleiro;

public class AtorParede extends Actor {
	protected TileSetSprite parede;
	protected int	tileSet = 1;
	protected int	tile = 1;
	protected Vetor2D_int offset = new Vetor2D_int( 0 , 0 );
	
	public Tabuleiro.DIRECOES direcao;
	public int paredeFlag;
	
	protected AnimatedSprite decoracao = null;

	public AtorParede( Tabuleiro.DIRECOES direcao , int paredeFlag ) {
		super( 128 , 256 );
		this.direcao = direcao;
		this.paredeFlag = paredeFlag;

		// Definindo a direcao...
		switch( direcao ) {
			case NORTE:
				tileSet = 1;
				break;
			
			case OESTE:
				tileSet = 2;
				break;
				
			case SUL:
				tileSet = 1;
				offset.x = -37;
				offset.y = +18;
				break;
			
			case LESTE:
				tileSet = 2;
				offset.x = +37;
				offset.y = +19;
				break;
		}
		
		// Definindo os cortes...
		switch( paredeFlag ) {
			case 1:
				tile = 1;
				break;
				
			case 2:
				tile = 2;
				break;	
				
			case 3:
				tile = 3;
				break;
				
			case 4:
				tile = 4;
				break;
		}
		
		this.parede = this.addTileSprite( "wallTiles.txt", offset , 0 );
		
		adicionarDecoracao( 25 );
		
		// Desenhando o sprite em si
		ehPelaMetade( false );
	}
	
	public void ehPelaMetade( boolean flag ) {
		if( flag == true ) {
			parede.setTile( tileSet + 10 , tile );
			
			if( decoracao != null ) {
				decoracao.setVisible( false );
			}
		}
		else {
			parede.setTile( tileSet + 0 , tile );
			
			if( decoracao != null ) {
				decoracao.setVisible( true );
			}
		}
	}
	
	public void adicionarDecoracao( int chance ) {
		Random random = new Random();
		int valor;
		
		// Decide se realmente vai adiconar ou nao
		valor = random.nextInt( 100 ); 
		if( valor > chance ) {
			return;
		}
		
		
		// Decide qual decoracao adicionar
		String arquivo = "";
		
		valor = random.nextInt( 100 );
		if( valor < 40 ) {
		// Luminaria	
			arquivo = "decoWallCandle1.txt";
		}
		else if( valor < 50 ) {
		// Cortina
			valor = random.nextInt( 4 ) + 1;
			arquivo = "decoWallCurtain" + Integer.toString( valor ) + ".txt";
		}
		else if( valor < 100 ) {
		// Quadro
			valor = random.nextInt( 6 ) + 1;
			arquivo = "decoWallPainting" + Integer.toString( valor ) + ".txt";
		}		
		
		this.decoracao = addAnimatedSprite( arquivo , offset , 1);
		this.decoracao.playAnimation( tileSet );
	}
	
}
