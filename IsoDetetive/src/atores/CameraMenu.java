package atores;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import animacao.*;

public class CameraMenu extends Camera {
	public enum Modos {
		MENU_PRINCIPAL,
		MAO,
		PALPITE,
		ACUSACAO,
		NOTAS,
		ESCOLHA_CARTA,
		FEEDBACK
	}
	
	public CenaMenuPrincipal 	menuPrincipal;
	public CenaMao 				cenaMao;
	public CenaBlocoNotas 		cenaBlocoNotas;
	public CenaPalpite 			cenaPalpite;
	public CenaAcusacao 		cenaAcusacao;
	public CenaReacaoAoPalpite 	cenaEscolhaCarta;
	public CenaFeedbackDoPalpite 		cenaFeedback;
    
	public CameraMenu( Component viewPort , int x , int y ) {
		super( viewPort , x , y );

		setOpaque( false );
		getViewport().setOpaque( false );
		setTarget( 0 , 0 );
		setIsFixedOnTarget( false );
		setBorder(BorderFactory.createEmptyBorder());
		
		super.hideScrolls();
		
		definirModo( Modos.MENU_PRINCIPAL );
	}
	
	private void esconderTudo( ) {
		if( menuPrincipal != null ) menuPrincipal.setVisible( false );
		if( cenaMao != null ) cenaMao.setVisible( false );
		if( cenaBlocoNotas != null ) cenaBlocoNotas.setVisible( false );
		if( cenaPalpite != null ) cenaPalpite.setVisible( false );
		if( cenaAcusacao != null ) cenaAcusacao.setVisible( false );
		if( cenaEscolhaCarta != null ) cenaEscolhaCarta.setVisible( false );
		if( cenaFeedback != null ) cenaFeedback.setVisible( false );
	}
	
	public void definirModo( CameraMenu.Modos modo ) {
		esconderTudo();

		switch( modo ) {
			case MENU_PRINCIPAL:
				setBounds(0, 0 , 900 , 100 );
				if( menuPrincipal != null ) menuPrincipal.setVisible(true);		
				break;
				
			case MAO:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaMao != null ) cenaMao.setVisible(true);		
				break;
				
			case PALPITE:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaPalpite != null ) cenaPalpite.setVisible(true);		
				break;
				
			case NOTAS:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaBlocoNotas != null ) cenaBlocoNotas.setVisible(true);		
				break;
				
			case ACUSACAO:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaAcusacao != null ) cenaAcusacao.setVisible(true);		
				break;
				
			case ESCOLHA_CARTA:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaEscolhaCarta != null ) cenaEscolhaCarta.setVisible(true);		
				break;
				
			case FEEDBACK:
				setBounds(0, 0 , 1000 , 700 );
				if( cenaFeedback != null ) cenaFeedback.setVisible(true);		
				break;
		
			default:
				setBounds(0, 0 , 1000 , 700 );
				break;
		}

		this.revalidate();
	}
	
	
}
