package atores;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import animacao.Actor;
import animacao.Scene;
import atores.CameraMenu.Modos;
import estruturas.Vetor2D_int;
import jogo.ControladoraDoJogo;
import jogo.ControladoraDoJogo.EstadoDaJogada;
import mediadores.MediadorFluxoDeJogo;
import mediadores.TradutorMenus;

class mouseListener_movimento extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent arg0)  {
		if(MediadorFluxoDeJogo.getInstance().obterEstadoDoJogo() == EstadoDaJogada.INICIO) {
			MediadorFluxoDeJogo.getInstance().rolarDados();
		}
		else if(MediadorFluxoDeJogo.getInstance().obterEstadoDoJogo() == EstadoDaJogada.CONFIRMANDO_MOVIMENTO) {
			MediadorFluxoDeJogo.getInstance().confirmarMovimento();
		}
	}
}

class mouseListener_acusar extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent arg0)  {
		
	}
}

class mouseListener_verCartas extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent arg0)  {
		MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.MAO);
		TradutorMenus.getInstance().desenharCartasNaMao(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaMao);
	}
}

class mouseListener_verBlocoNotas extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent arg0)  {
		MediadorFluxoDeJogo.getInstance().cameraMenu.definirModo(Modos.NOTAS);
		TradutorMenus.getInstance().desenharBlocoDeNotas(MediadorFluxoDeJogo.getInstance().cameraMenu.cenaBlocoNotas);
	}
}

class mouseListener_passar extends MouseAdapter {
	@Override
	public void mouseClicked(MouseEvent arg0)  {
		MediadorFluxoDeJogo.getInstance().iniciarJogadaDaVez();
	}
}

public class CenaMenuPrincipal extends Scene {
	
	public CenaMenuPrincipal()
	{
		super(0, 0, 800, 100);
		
		Actor novoBotao;
		
		novoBotao = new AtorBotoes( "botao_movimento.txt" );
		novoBotao.setLocation( 50 , 15 );  
		novoBotao.addMouseListener( new mouseListener_movimento() );
		this.addActor( novoBotao , 10 );
		
		novoBotao = new AtorBotoes( "botao_acusar.txt" );
		novoBotao.setLocation( 150 , 15 );  
		novoBotao.addMouseListener( new mouseListener_acusar() );
		this.addActor( novoBotao , 10 );
		
		novoBotao = new AtorBotoes( "botao_mao.txt" );
		novoBotao.setLocation( 250 , 15 );  
		novoBotao.addMouseListener( new mouseListener_verCartas() );
		this.addActor( novoBotao , 10 );		
		
		novoBotao = new AtorBotoes( "botao_notas.txt" );
		novoBotao.setLocation( 350 , 15 );  
		novoBotao.addMouseListener( new mouseListener_verBlocoNotas() );
		this.addActor( novoBotao , 10 );	
		
		novoBotao = new AtorBotoes( "botao_passar.txt" );
		novoBotao.setLocation( 450 , 15 );  
		novoBotao.addMouseListener( new mouseListener_passar() );
		this.addActor( novoBotao , 10 );	
	}
	
}
