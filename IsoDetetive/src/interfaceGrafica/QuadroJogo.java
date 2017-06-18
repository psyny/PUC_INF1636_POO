package interfaceGrafica;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import animacao.*;
import atores.*;
import estruturas.*;
import jogo.*;
import mediadores.TradutorMovimentacao;
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
	
	// Lister TEMPORARIO, so para testes de destruição de atores
	private class actList_dado implements ActionListener , AnimationEndObserver {
		private Dado dado1 = null;
		private Dado dado2 = null;
		private Scene cena = null;
		private TradutorTabuleiro tradutorTabuleiro;
		private TradutorMovimentacao tradutorMovimentacao;
		
		
		public actList_dado( Scene cena , TradutorTabuleiro tradutorTabuleiro ,  TradutorMovimentacao tradutorMovimentacao ) {
			this.cena = cena;
			this.tradutorTabuleiro = tradutorTabuleiro;
			this.tradutorMovimentacao = tradutorMovimentacao;
		}

		@Override
		public void actionPerformed(ActionEvent e)  {
			if( dado1 == null ) {
				this.criarDados();
			} else {
				this.deletarDados();
			}
		}
		
		private void criarDados() {
			dado1 = new Dado();;
			cena.addActor( dado1 , 10 );
			
			dado2 = new Dado();;
			cena.addActor( dado2 , 10 );
			
			dado1.animationEndRegister(this);
			
			int valorAleatorio1 = (int)(Math.random()*6 + 1);
			int valorAleatorio2 = (int)(Math.random()*6 + 1);

			tradutorMovimentacao.desmarcarCasas();	
			ControladoraDoJogo.getInstance().iniciarProximaJogada();
			Jogador jogadorDaVez = ControladoraDoJogo.getInstance().obterJogadorDaVez();
			
			Casa casa = jogadorDaVez.obterPosicao();
			Vetor2D_double centro1 = tradutorTabuleiro.obterCentroDaCasa(casa);
			Vetor2D_double centro2 = tradutorTabuleiro.obterCentroDaCasa(casa);
			
			centro1.x += 100;
			centro2.x -= 100;
			
			dado1.Lancar( centro1 , valorAleatorio1 );
			dado2.Lancar( centro2 , valorAleatorio2 );
			
			ControladoraDoJogo.getInstance().rolarDadoParaMovimentacao( valorAleatorio1 + valorAleatorio2 );
		}
		
		private void deletarDados() {
			//this.dado.setToDestroy();
			tradutorMovimentacao.desmarcarCasas();	
			this.dado1.setToDestroy( 0 );
			this.dado2.setToDestroy( 0 );
			this.dado1 = null;
			this.dado2 = null;
		}

		@Override
		public void animationEndNotify(AnimationEndObserved observed) {
			tradutorMovimentacao.marcarCasas();		
		}
	}
	
	// Lister TEMPORARIO, so para testes de marcadores
	private class actList_marcador implements ActionListener {
		private TradutorMovimentacao tradutorMovimentacao;
		private boolean marcado = false;
		
		
		public actList_marcador( TradutorMovimentacao tradutorMovimentacao ) {
			this.tradutorMovimentacao = tradutorMovimentacao;
		}

		@Override
		public void actionPerformed(ActionEvent e)  {
			if( marcado == false ) {
				this.gerarMarcadores();
				marcado = true;
			} else {
				this.deletarMarcadores();
				marcado = false;
			}
		}
		
		private void gerarMarcadores() {
			ControladoraDoJogo.getInstance().iniciarProximaJogada();
			ControladoraDoJogo.getInstance().rolarDadoParaMovimentacao(3);
			
			tradutorMovimentacao.marcarCasas();
		}
		
		private void deletarMarcadores() {
			tradutorMovimentacao.desmarcarCasas();
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
        
        // Mediadores
        TradutorJogadores tradutorJogadores = new TradutorJogadores( cenaAtores , tradutorTabuleiro  );
        tradutorJogadores.adicionarJogadores();
        
        TradutorMovimentacao tradutorMovimentacao = new TradutorMovimentacao( tradutorJogadores );
        
        
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
        bt_dado.addActionListener( new actList_dado(cenaAtores , tradutorTabuleiro , tradutorMovimentacao ) );
        controlsPane.add( bt_dado );
        
        JButton bt_marcadores = new JButton("Marcadores");
        bt_marcadores.setBounds(100, 0, 100, 30);
        bt_marcadores.addActionListener( new actList_marcador(tradutorMovimentacao) );
        controlsPane.add( bt_marcadores );        
        
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
		testActor.addAnimatedSprite( "tileSelector.txt" , new Vetor2D_int(0,0) , 0 );
		testActor.getAnimatedSprite().playAnimation(1);
		cenaTeste.addActor( testActor , 10 );
		
		
		
		
	}
}
