package atores;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import animacao.*;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;
import mediadores.*;
import observers.Observed_CasaSelecionada;
import observers.Observer_CasaSelecionada;

public class CenaTabuleiro extends CenaIsometrica implements Observed_CasaSelecionada {
	public Vetor2D_int ultimaCasaApontada = new Vetor2D_int(0,0);
	Observer_CasaSelecionada a;
	
	public ArrayList<Observer_CasaSelecionada> casaSelecionadaObserverList = new ArrayList<Observer_CasaSelecionada>();

	public CenaTabuleiro(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		this.setBackground( new Color(0,0,0) );
		this.setOpaque( true );
	}

	@Override
	public void passTime( long time ) {
		super.passTime(time);
		
		// Calcula a ultima posicao do mouse
		Point posicaoAbsolutaDoMouse = MouseInfo.getPointerInfo().getLocation();
		if( posicaoAbsolutaDoMouse != null ) {
			Point posicaoAbsolutaDoComponente = this.getLocationOnScreen();
			
			Point posicaoRelativa = new Point();
			posicaoRelativa.x = posicaoAbsolutaDoMouse.x - posicaoAbsolutaDoComponente.x;
			posicaoRelativa.y = posicaoAbsolutaDoMouse.y - posicaoAbsolutaDoComponente.y;
			
			Vetor2D_int novaCasaApontada = this.obterCasaClicada(posicaoRelativa);
			
			// Chama os listeners interessados
			if( novaCasaApontada.x == this.ultimaCasaApontada.x && novaCasaApontada.y == this.ultimaCasaApontada.y ) {
				// Posicao é a mesma
			} else {
				this.ultimaCasaApontada.x = novaCasaApontada.x;
				this.ultimaCasaApontada.y = novaCasaApontada.y;
				
				animationEndNotityObservers();
			}
		}
	}
	
	public Vetor2D_int obterCasaClicada(Point p)
	{
		Vetor2D_double vet = Isometria.obterVetorCartesiano(new Vetor2D_double(p.x - this.getIsometricOffset().x, p.y - this.getIsometricOffset().y));
		
		Vetor2D_int casa = new Vetor2D_int(0, 0);
		casa.x = (int)( 42 + vet.x) / 63; // Constantes referentes ao tamanho da imagem do tileset
		casa.y = (int)( 42 + vet.y) / 63; // Constantes referentes ao tamanho da imagem do tileset
		
		return casa;
	}
	
	// OBSERVER PATTERN Method Group
	public void register_casaSelecionadaObserved(Observer_CasaSelecionada observer) {
		this.casaSelecionadaObserverList.add(observer);
	}
	
	public void unRegister_casaSelecionadaObserved(Observer_CasaSelecionada observer) {
		this.casaSelecionadaObserverList.remove(observer);
	}
	
	private void animationEndNotityObservers() {
		for( Observer_CasaSelecionada ob : this.casaSelecionadaObserverList ) {
			ob.observerNotify_CasaSelecionada(this);
		}
	}	
	
	public Vetor2D_int obterCasaSelecionada() {
		return this.ultimaCasaApontada;
	}


}
