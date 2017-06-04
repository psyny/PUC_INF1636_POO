package interfaceGrafica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jogo.ControladoraDoJogo;
import jogo.Jogador;
import jogo.Personagem;
import jogo.PersonagemEnum;
import jogo.PersonagemLista;

public class QuadroSelecaoDeJogadores extends JPanel {
	private JList lista_de_personagens;
	private JButton start;	
	
	// Listener que carrega proxima janela de jogo iniciado
	class actList_iniciarJogo implements ActionListener {
		public actList_iniciarJogo() {
		}

		@Override
		public void actionPerformed(ActionEvent e)  {
			// Configurar Jogadores - TEMPORARIO
			for( PersonagemEnum personagemEnum : PersonagemEnum.values() ) {
				Jogador jogador = new Jogador( personagemEnum );
				ControladoraDoJogo.getInstance().adicionarJogador( jogador );	
			}
			
			
			JanelaPrincipal.carregarQuadro( new QuadroJogo() );
		}
	}
	
	// Listener de mudanças na lista de personagens
	class listener_ListaDePesonagens implements ListSelectionListener {
		public JList list;
		public JButton button;
		
		public listener_ListaDePesonagens(JList list, JButton button) {
			this.list = list;
			this.button = button;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			List lista = list.getSelectedValuesList();
			if(lista.size() >= 3) {
				button.setEnabled(true);
			}	
			else {
				button.setEnabled(false);
			}
		}
	}
	
	// -------------------------------------------------------------------------
	
	public QuadroSelecaoDeJogadores() {
		setBackground( new Color(230,230,230) );
		setLayout( null );
		
		// ---------------
		
		JButton btn_iniciarPartida = new JButton("Iniciar Partida");
		btn_iniciarPartida.setBounds( 0 , 200, 200, 100 );
		btn_iniciarPartida.addActionListener( new actList_iniciarJogo() );
		btn_iniciarPartida.setEnabled( false );
		add(btn_iniciarPartida);
		
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem(  PersonagemEnum.L ).obterNome() );
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.SHERLOCK ).obterNome() );
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.CARMEN ).obterNome() );
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.PANTERA ).obterNome() );
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.EDMORT ).obterNome() );
		listModel.addElement( PersonagemLista.getInstance().obterPersonagem( PersonagemEnum.BATMAN ).obterNome() );

		// ---------------		
		
		lista_de_personagens = new JList(listModel);
		lista_de_personagens.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista_de_personagens.setBounds(0, 0, 200, 150);
		lista_de_personagens.addListSelectionListener( new listener_ListaDePesonagens( lista_de_personagens , btn_iniciarPartida ) );
		
		add(lista_de_personagens);
		
		
		
	}	
}
