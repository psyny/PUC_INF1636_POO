package animacao;

import animacao.*;
import estruturas.Vetor2D_double;
import estruturas.Vetor2D_int;

public class CenaIsometrica extends Scene {
	private int margem_direita;
	private int margem_esquerda;
	private int margem_superior;
	private int margem_inferior;
	
	private Vetor2D_int 	origem;
	private Vetor2D_int 	tamanhoProjecao;
	private Vetor2D_double 	tamanhoVirtual;

	
	public CenaIsometrica(int x, int y, int largura_virtual , int altura_virtual ) {
		super( x , y );
		
		// Calculo e registro do tamanho projetado
		this.definirTamanhoVirtual( largura_virtual , altura_virtual );
		this.definirMargens( 0 , 0 ,  0 ,  0 );
		
	}
	
	private void atualizarTamanho() {
		Vetor2D_double limites = Isometria.obterCaixaDelimitadora( this.tamanhoVirtual );
		this.tamanhoProjecao = new Vetor2D_int( (int)limites.x , (int)limites.y );	
		
		int largura 	= this.margem_direita + this.margem_esquerda + this.tamanhoProjecao.x;
		int altura 		= this.margem_superior + this.margem_inferior + this.tamanhoProjecao.y; 
		
		this.setBounds( this.getX() , this.getY() , largura , altura );
		
		this.atualizarOrigem();
	}
	
	private void atualizarOrigem() {
		int ox = 0;
		int oy = 0;

		Vetor2D_double deslocamentoX = Isometria.obterVetorIsometrico( new Vetor2D_double( this.tamanhoVirtual.x , 0 ) );
		Vetor2D_double deslocamentoY = Isometria.obterVetorIsometrico( new Vetor2D_double( 0 , this.tamanhoVirtual.y ) );
		
		if( deslocamentoY.x < deslocamentoX.x ) {
			ox = (int)deslocamentoY.x;
		} else {
			ox = (int)deslocamentoX.x;
		}
		
		if( ox > 0 ) {
			ox = 0;
		} else {
			ox = -ox;
		}
		
		
		
		if( deslocamentoY.y < deslocamentoX.y ) {
			oy = (int)deslocamentoY.y;
		} else {
			oy = (int)deslocamentoX.y;
		}
		
		if( oy > 0 ) {
			oy = 0;
		} else {
			oy = -oy;
		}
			
		this.origem = new Vetor2D_int( ox , oy );
	}
	
	public void definirTamanhoVirtual( double largura , double altura ) {
		this.tamanhoVirtual = new Vetor2D_double( largura , altura );
		this.atualizarTamanho();
	}
	
	public void definirMargens( int direita , int esquerda , int superior , int inferior ) {
		this.margem_direita 	= direita;
		this.margem_esquerda 	= esquerda;
		this.margem_superior 	= superior;
		this.margem_inferior 	= inferior;
		
		this.atualizarTamanho();
	}
	
	public int obterMargemDireita() {
		return this.margem_direita;
	}
	
	public int margem_esquerda() {
		return this.margem_direita;
	}

	public int obterMargemSuperior() {
		return this.margem_superior;
	}
	
	public int obterMargemInferior() {
		return this.margem_inferior;
	}
	
	public Vetor2D_int getProjection( Vetor2D_double virtualPosition ) {
		Vetor2D_double posicaoProjetada = Isometria.obterVetorIsometrico( virtualPosition );
		posicaoProjetada.x += this.margem_esquerda + this.origem.x;
		posicaoProjetada.y += this.margem_superior + this.origem.y;
		
		return new Vetor2D_int( (int)posicaoProjetada.x , (int)posicaoProjetada.y );
	}
}
