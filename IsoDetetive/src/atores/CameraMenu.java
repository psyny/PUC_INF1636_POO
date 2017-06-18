package atores;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
		setBorder(BorderFactory.createEmptyBorder());
		
		definirModo( Modos.MENU_PRINCIPAL );
	}
	
	private void esconderTudo( ) {
		for( Component comp : this.getComponents() ) {
			comp.setVisible( false );
		}
	}
	
	public void definirModo( CameraMenu.Modos modo ) {
		esconderTudo();
		
		switch( modo ) {
			case MENU_PRINCIPAL:
				setBounds(0, 0 , 1000 , 100 );
				menuPrincipal.setVisible(true);
				break;
		
			default:
				setBounds(0, 0 , 1000 , 700 );
				break;
		}
		
		this.revalidate();
	}
	
	
}
