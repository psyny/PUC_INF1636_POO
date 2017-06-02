package interfaceGrafica;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import animacao.*;
import estruturas.*;
import jogo.GeradorDeTabuleiros;
import jogo.*;

public class QuadroJogo extends JLayeredPane {
	
	
	public QuadroJogo() {
		// Carregando arquivos pertinentes ao jogo
		Tabuleiro tabuleiro = GeradorDeTabuleiros.carregarDoArquivo();	
		Vetor2D_int tamanhoVirtualDoTabuleiro = new Vetor2D_int( tabuleiro.colunas * 64 , tabuleiro.linhas * 64 );
		Vetor2D_int deslocamentoVertical = new Vetor2D_int( 0 , tamanhoVirtualDoTabuleiro.y );
		
		// Configurações principais
		JanelaPrincipal.definirLayoutManager( null );
		
		setBackground( new Color(0,255,255) );
		setOpaque( true );
		setLayout( null );
        setBounds(0, 0, 1024 , 768 );
        
        // Cena Principal - No nosso caso, única cena
        
		Scene mainScene = new Scene( 0 , 0 , 3000 , 3000 );	
		
		// Camera
		
		Camera gameCamera = new Camera( mainScene , 0 , 0 ); 
        gameCamera.setBounds(0, 0, 1000 , 700 );
        gameCamera.setTarget( 0 , 0 );
        
        gameCamera.setIsFixedOnTarget( false );
        
        add( gameCamera );
        setLayer( gameCamera , 10 );
        
        // Animacao de Teste - Diretor por Sprites
        
		AnimatedSprite testAnim = new AnimatedSprite( "testExplosion.txt" );
		testAnim.setLocation( 0, 100 );   
		testAnim.insertInto( mainScene );
		testAnim.playAnimation( 5 , LoopType.REPEAT );
		
		// Ator de Teste - Ator que pode ser composto por Varios Sprites
		
		Actor testActor = new Actor( 200 , 200 );
		testActor.setLocation( 200, 100 );   
		testActor.addAnimatedSprite( "testExplosion.txt" , new Vetor2D_int(0,0) , 0 );
		
		mainScene.addActor( testActor , 10 );

		// Ator de Teste - Testando o Tile
		
		Actor testActor2 = new Actor( 200 , 200 );
		testActor2.setLocation( 100, 100 );
		testActor2.setVirtualPosition( 100 , 0 );
		testActor2.addTileSprite( "houseTiles.txt" , new Vetor2D_int(0,0) , 0 );

		mainScene.addActor( testActor2 , 5 );	
		
		Actor testActor3 = new Actor( 200 , 200 );
		testActor3.setVirtualPosition( 164 , 0 );
		testActor3.addTileSprite( "houseTiles.txt" , new Vetor2D_int(0,0) , 0 );

		mainScene.addActor( testActor3 , 5 );
		
	}
}
