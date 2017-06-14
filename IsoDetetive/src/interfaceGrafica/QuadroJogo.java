package interfaceGrafica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import animacao.*;
import atores.*;
import estruturas.*;
import jogo.*;
import mediadores.TradutorJogadores;
import mediadores.TradutorTabuleiro;




public class QuadroJogo extends JLayeredPane {
	private class painelDeControles extends JPanel {
		public painelDeControles() {
			this.setBackground( new Color(255,0,0));
			this.setOpaque( true );
			this.setLayout( null );
		}
	}
	
	// Listener que carrega proxima janela de jogo iniciado
	private class actList_dado implements ActionListener {
		private Actor dado = null;
		private Scene cena = null;
		
		
		public actList_dado( Scene cena ) {
			this.cena = cena;
		}

		@Override
		public void actionPerformed(ActionEvent e)  {
			if( dado == null ) {
				this.criarDado();
			} else {
				this.deletarDado();
			}
		}
		
		private void criarDado() {
			dado = new Actor( 200 , 200 );
			dado.setLocation( 500, 500 );   
			dado.addAnimatedSprite( "dice.txt" , new Vetor2D_int(0,0) , 0 );
			dado.getAnimatedSprite().playAnimation(100);
			cena.addActor( dado , 10 );
		}
		
		private void deletarDado() {
			//this.dado.setToDestroy();
			this.dado.getAnimatedSprite().playAnimation(10, LoopType.END_STOP );
			this.dado.setToDestroy( null );
			this.dado = null;
		}
	}
	
	public QuadroJogo() {
		// Carregando arquivos pertinentes ao jogo
		Tabuleiro tabuleiro = GeradorDeTabuleiros.carregarDoArquivo();	
		tabuleiro.scanGameInfo();
				
		// Configurações principais
		JanelaPrincipal.getInstance().definirLayoutManager( null );
		
		setBackground( new Color(0,255,255) );
		setOpaque( true );
		setLayout( null );
        setBounds(0, 0, 1024 , 768 );
        
        // Cena Principal - Cena composta:
        CenaComposta cenaPrincipal = new CenaComposta(0,0);
        
        // Cena do tabueiro ( piso ):
        CenaTabuleiro cenaTabuleiro = new CenaTabuleiro( 0 , 0 , 1 , 1 );
		cenaTabuleiro.definirMargens( 100 , 100 , 100, 100);
		
        TradutorTabuleiro tradutorTabuleiro = new TradutorTabuleiro( tabuleiro , cenaTabuleiro , 63 , 63 );
        tradutorTabuleiro.popularTabuleiroGrafico();
        
        cenaPrincipal.adicionarCena( cenaTabuleiro , 0);
        
        // Cena dos atores ( jogadores, dado, paredes, etc ):
        CenaAtores cenaAtores = new CenaAtores( 0 , 0 , (int)cenaTabuleiro.obterTamanhoVirtual().x , (int)cenaTabuleiro.obterTamanhoVirtual().y );
        cenaAtores.definirMargens( 100 , 100 , 100, 100);

        cenaPrincipal.adicionarCena( cenaAtores , 1);
        
        // Mediador
        TradutorJogadores tradutorJogadores = new TradutorJogadores( cenaAtores , tradutorTabuleiro  );
        tradutorJogadores.adicionarJogadores();
        
        // Cena de Testes     
		Scene cenaTeste = new CenaIsometrica( 0 , 0 , 300 , 300 );	
		cenaPrincipal.adicionarCena( cenaTeste , 1 );
        
		// Camera
		Camera gameCamera = new Camera( cenaPrincipal , 0 , 0 ); 
        gameCamera.setBounds(0, 0, 1000 , 700 );
        gameCamera.setTarget( 0 , 0 );
        
        gameCamera.setIsFixedOnTarget( false );
        
        add( gameCamera );
        setLayer( gameCamera , 10 );
        
        // Layer dos controles de jogo
        JPanel controlsPane = new painelDeControles();
        controlsPane.setBounds(0, 0, 500, 50);
        
        JButton bt_dado = new JButton("Dado");
        bt_dado.setBounds(0, 0, 100, 30);
        bt_dado.addActionListener( new actList_dado(cenaAtores) );
        controlsPane.add( bt_dado );
        
        add( controlsPane );
        setLayer( controlsPane , 20 );
        
       
        
        
        
        // Animacao de Teste - Diretor por Sprites
        
		AnimatedSprite testAnim = new AnimatedSprite( "testExplosion.txt" );
		testAnim.setLocation( 0, 100 );   
		testAnim.insertInto( cenaTeste );
		testAnim.playAnimation( 5 , LoopType.REPEAT );
		
		// Ator de Teste - Ator que pode ser composto por Varios Sprites
		
		Actor testActor = new Actor( 200 , 200 );
		testActor.setLocation( 200, 100 );   
		testActor.addAnimatedSprite( "dice.txt" , new Vetor2D_int(0,0) , 0 );
		testActor.getAnimatedSprite().playAnimation(100);
		cenaTeste.addActor( testActor , 10 );
		
		
		
		
	}
}
