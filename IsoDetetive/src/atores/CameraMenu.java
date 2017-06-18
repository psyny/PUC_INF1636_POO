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
		ACUSACAO
	}
	
    public CenaMenuPrincipal 	menuPrincipal;
    public CenaMao 				cenaMao;
    
    
	public CameraMenu( Component viewPort , int x , int y ) {
		super( viewPort , x , y );

		setOpaque( false );
		getViewport().setOpaque( false );
		setTarget( 0 , 0 );
		setIsFixedOnTarget( false );
		//setBorder(BorderFactory.createEmptyBorder());
		
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		definirModo( Modos.MENU_PRINCIPAL );
	}
	
	private void esconderTudo( ) {
		if( menuPrincipal != null ) menuPrincipal.setVisible( false );
		if( cenaMao != null ) cenaMao.setVisible( false );
	}
	
	public void definirModo( CameraMenu.Modos modo ) {
		esconderTudo();
		
		switch( modo ) {
			case MENU_PRINCIPAL:
				setBounds(0, 0 , 900 , 100 );
				if( menuPrincipal != null ) menuPrincipal.setVisible(true);		
				break;
		
			default:
				setBounds(0, 0 , 1000 , 700 );
				break;
		}
		
		this.revalidate();
	}
	
	
}
