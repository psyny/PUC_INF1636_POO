package atores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import animacao.*;
import estruturas.*;
import facedes.*;
import jogo_TiposEnumerados.AcoesPossiveisType;
import observers.*;



public class CenaMenuPrincipal extends Scene implements Observer_CasaClicada {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2855788907009836081L;

	class mouseListener_dado extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.ROLAR_DADOS );
		}
	}	
	
	class mouseListener_mover extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.MOVER );
		}
	}

	class mouseListener_acusar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.ACUSAR );
		}
	}
	
	class mouseListener_salvar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.SALVAR );
		}
	}	

	class mouseListener_verCartas extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.VER_MAO );
		}
	}

	class mouseListener_verBlocoNotas extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.VER_NOTAS );
		}
	}

	class mouseListener_passar extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent arg0)  {
			Facade_Menus.getInstance().menuOpcoesJogada_realizarAcao( AcoesPossiveisType.PASSAR_A_VEZ );
		}
	}
	
	protected AtorBotaoMenuJogo personagem_l;
	protected AtorBotaoMenuJogo personagem_sherlock;
	protected AtorBotaoMenuJogo personagem_pantera;
	protected AtorBotaoMenuJogo personagem_carmen;
	protected AtorBotaoMenuJogo personagem_edmort;
	protected AtorBotaoMenuJogo personagem_batman;

	protected AtorBotaoMenuJogo botao_salvar;
	protected AtorBotaoMenuJogo botao_dado;
	protected AtorBotaoMenuJogo botao_mover;
	protected AtorBotaoMenuJogo botao_acusar;
	protected AtorBotaoMenuJogo botao_mao;
	protected AtorBotaoMenuJogo botao_notas;
	protected AtorBotaoMenuJogo botao_passar;	
	
	ArrayList<AtorBotaoMenuJogo> botoes = new ArrayList<AtorBotaoMenuJogo>();
	
	private Vetor2D_int proximaPosicao = new Vetor2D_int(0,0);
	
	public CenaMenuPrincipal()
	{
		super(0, 0, 900, 100);
		this.setVisible(false);
		
		inicializarBotoes();
		esconderBotoes();
		
		this.setVisible(true);
	}
	
	@Override
	public synchronized void addActor( Actor actor , int layer ) {
		super.addActor(actor, layer);
		if( actor instanceof AtorBotaoMenuJogo ) {
			botoes.add( (AtorBotaoMenuJogo)actor );
		}
	}

	private void inicializarBotoes() {
		// Personagens
		personagem_l = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_L );
		personagem_l.setVisible(false);
		this.addActor( personagem_l , 10 );
		
		personagem_sherlock = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_SHERLOCK );
		this.addActor( personagem_sherlock , 10 );
		
		personagem_pantera = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_PANTERA );
		this.addActor( personagem_pantera , 10 );	
		
		personagem_carmen = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_CARMEN );
		this.addActor( personagem_carmen , 10 );	
		
		personagem_edmort = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_EDMORT );
		this.addActor( personagem_edmort , 10 );	
		
		personagem_batman = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.PERSONAGEM_BATMAN );
		this.addActor( personagem_batman , 10 );	
		
		// Controle de jogo		
		botao_salvar = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_SALVAR );
		botao_salvar.addMouseListener( new mouseListener_salvar() );
		this.addActor( botao_salvar , 10 );			
		
		botao_acusar = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_ACUSAR );
		botao_acusar.addMouseListener( new mouseListener_acusar() );
		this.addActor( botao_acusar , 10 );		
		
		botao_mao = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_MAO );
		botao_mao.addMouseListener( new mouseListener_verCartas() );
		this.addActor( botao_mao , 10 );		
		
		botao_notas = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_NOTAS );
		botao_notas.addMouseListener( new mouseListener_verBlocoNotas() );
		this.addActor( botao_notas , 10 );	
		
		botao_dado = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_DADO );
		botao_dado.addMouseListener( new mouseListener_dado() );
		this.addActor( botao_dado , 10 );
		
		botao_mover = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_MOVER );
		botao_mover.addMouseListener( new mouseListener_mover() );
		this.addActor( botao_mover , 10 );
		
		botao_passar = new AtorBotaoMenuJogo( AtorBotaoMenuJogo.Tipo.BOTAO_PASSAR );
		botao_passar.addMouseListener( new mouseListener_passar() );
		this.addActor( botao_passar , 10 );				
	}
	
	public void esconderBotoes( ) {
		esconderBotoes( false );
	}
	
	public void esconderBotoes( boolean deixarJogador ) {
		if( deixarJogador == true ) {
			esconderBotoes_opcoes();
		}
		else {
			esconderBotoes_todos();
		}
		
		resetarPosicaoInicial();
		revalidarPosicaoDosBotoes();
	}

	private void esconderBotoes_todos() {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			botao.setVisible(false);
			botao.setLocation(0, 0);
		}
	}
	
	private void esconderBotoes_opcoes() {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			switch( botao.tipo ) {
				case PERSONAGEM_BATMAN:
				case PERSONAGEM_CARMEN:
				case PERSONAGEM_EDMORT:
				case PERSONAGEM_L:
				case PERSONAGEM_PANTERA:
				case PERSONAGEM_SHERLOCK:
					continue;
					
				default:
					break;
			}
			
			botao.setVisible(false);
			botao.setLocation(0, 0);
		}
	}
	
	
	
	public void ativarBotao( AtorBotaoMenuJogo.Tipo tipo ) {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.obterTipo() == tipo ) {
				botao.setVisible(true);
				break;
			}
		}
		
		revalidarPosicaoDosBotoes();
	}	
	
	public void desativarBotao( AtorBotaoMenuJogo.Tipo tipo ) {
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.obterTipo() == tipo ) {
				botao.setVisible(false);
				break;
			}
		}
		
		revalidarPosicaoDosBotoes();
	}
	
	public void revalidarPosicaoDosBotoes() {
		resetarPosicaoInicial();
		
		for( AtorBotaoMenuJogo botao : this.botoes ) {
			if( botao.isVisible() == true ) {
				botao.setLocation( proximaPosicao.x , proximaPosicao.y );
				
				proximaPosicao.x += 100;
			}
		}
	}
	
	private void resetarPosicaoInicial() {
		proximaPosicao.x = 50;
		proximaPosicao.y = 50;
	}

	@Override
	public void observerNotify_CasaClicada(Observed_CasaClicada observed) {
		if( botao_mover.isVisible() == false ) {
			botao_mover.setVisible(true);	
			revalidarPosicaoDosBotoes();
		}
	}
}
