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
import mediadores.MediadorFluxoDeJogo;
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
	

	
	public QuadroJogo() {
		// Carregando arquivos pertinentes ao jogo
		MediadorFluxoDeJogo.getInstance().carregarDadosDaControladora();
				
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
        MediadorFluxoDeJogo.getInstance().cenaTabuleiro = cenaTabuleiro;
		cenaTabuleiro.definirMargens( 100 , 100 , 100, 100);
		
        TradutorTabuleiro tradutorTabuleiro = new TradutorTabuleiro( 63 , 63 );
        MediadorFluxoDeJogo.getInstance().tradutorTabuleiro = tradutorTabuleiro;
        tradutorTabuleiro.popularTabuleiroGrafico();
        
        cenaPrincipal.adicionarCena( cenaTabuleiro , 0);
        
        // Cena dos atores ( jogadores, dado, paredes, etc ):
        CenaAtores cenaAtores = new CenaAtores( 0 , 0 , (int)cenaTabuleiro.obterTamanhoVirtual().x , (int)cenaTabuleiro.obterTamanhoVirtual().y );
        MediadorFluxoDeJogo.getInstance().cenaAtores = cenaAtores;
        cenaAtores.definirMargens( 100 , 100 , 100, 100);
        tradutorTabuleiro.compilarCelulas();
        
        
        cenaPrincipal.adicionarCena( cenaAtores , 1);
        
        // Mediadores
        TradutorJogadores tradutorJogadores = new TradutorJogadores();
        MediadorFluxoDeJogo.getInstance().tradutorJogadores = tradutorJogadores;
        tradutorJogadores.adicionarJogadores();
        
        TradutorMovimentacao tradutorMovimentacao = new TradutorMovimentacao();
        MediadorFluxoDeJogo.getInstance().tradutorMovimentacao = tradutorMovimentacao;

        
		// Camera - Jogo
		Camera gameCamera = new Camera( cenaPrincipal , 0 , 0 ); 
		MediadorFluxoDeJogo.getInstance().camera = gameCamera;
        gameCamera.setBounds(0, 0, 1020 , 735 );
        gameCamera.setTarget( 0 , 0 );
        gameCamera.setIsFixedOnTarget( false );
        
        add( gameCamera );
        setLayer( gameCamera , 10 );
        
        // -------------------------------------------------------
        // Menus do jogo
        
        CenaComposta menuCenaPrincipal = new CenaComposta(0,0);
        
        CameraMenu menuCamera = new CameraMenu( menuCenaPrincipal , 0 , 0 ); 
        MediadorFluxoDeJogo.getInstance().cameraMenu = menuCamera;
        add( menuCamera );
        setLayer( menuCamera , 20 );
        
        //Cena Menu Principal
        menuCamera.menuPrincipal = new CenaMenuPrincipal();
        tradutorMovimentacao.register_casaClicada(menuCamera.menuPrincipal);
        menuCenaPrincipal.adicionarCena( menuCamera.menuPrincipal , 1 );    
        
        //Cena Mao
        menuCamera.cenaMao = new CenaMao();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaMao , 2);
        
        //Cena BlocoDeNotas
        menuCamera.cenaBlocoNotas = new CenaBlocoNotas();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaBlocoNotas , 2);
        
        //Cena Palpite
        menuCamera.cenaPalpite = new CenaPalpite();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaPalpite , 2);
        
        //Cena Acusacao
        menuCamera.cenaAcusacao = new CenaAcusacao();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaAcusacao , 2);
        
        //Cena Escolha Carta
        menuCamera.cenaEscolhaCarta = new CenaEscolhaCarta();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaEscolhaCarta , 2);
        
        //Cena Feedback
        menuCamera.cenaFeedback = new CenaFeedback();
        menuCenaPrincipal.adicionarCena( menuCamera.cenaFeedback , 2);
        
        menuCamera.definirModo( CameraMenu.Modos.MENU_PRINCIPAL );
		
		//Inicia o primerio turno
		MediadorFluxoDeJogo.getInstance().iniciarJogo();
		MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
		
	}
	
}
