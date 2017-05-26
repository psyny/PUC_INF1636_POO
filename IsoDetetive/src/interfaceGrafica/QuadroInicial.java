package interfaceGrafica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Listener que carrega proxima janela de novo jogo
class actList_novoJogo implements ActionListener
{
	public actList_novoJogo()
	{
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
}

class actList_carregarJogo implements ActionListener
{
	public actList_carregarJogo()
	{
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
}


public class QuadroInicial extends JPanel {
	public QuadroInicial() {
		setBackground( new Color(255,255,255) );
		setLayout( null );
		
		JButton btn_novoJogo = new JButton("Novo Jogo");
		btn_novoJogo.setBounds( 0 , 0, 100, 100);
		add(btn_novoJogo);
		
		JButton btn_carregarJogo = new JButton("Continuar");
		btn_carregarJogo.setBounds( 100 , 0, 100, 100);
		add(btn_carregarJogo);
		
	}	
}
