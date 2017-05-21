import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class JanelaInicial extends JFrame{
	
	public final int LARG_DEFAULT = 800;
	public final int ALT_DEFAULT = 600;
	
	private JButton novoJogo;
	private JButton carregarJogo;
	
	private JPanel panel = new JPanel();
	
	public JFileChooser fileChooser = new JFileChooser();
	
	class novoJogoController implements ActionListener
	{
		JFrame frame;
		
		public novoJogoController(JFrame frame)
		{
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Main.janelaIntermediaria = new JanelaIntermediaria("Escolha os Jogadores");
			Main.janelaIntermediaria.setVisible(true);
			frame.setVisible(false);
		}
	}
	
	class carregarJogoController implements ActionListener
	{
		JFileChooser fileChooser;
		JFrame frame;
		
		public carregarJogoController(JFrame frame, JFileChooser fileChooser)
		{
			this.fileChooser = fileChooser;
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			fileChooser.showOpenDialog(frame);
		}
	}
	
	public JanelaInicial(String s)
	{
		super(s);
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		
		panel.setLayout(null);
		
		novoJogo = new JButton("Começar Um Novo Jogo");
		carregarJogo = new JButton("Carregar Um Jogo Ja Jogado");
		
		novoJogo.setBounds(0, 0, LARG_DEFAULT/2, ALT_DEFAULT);
		carregarJogo.setBounds(LARG_DEFAULT/2, 0, LARG_DEFAULT/2, ALT_DEFAULT);
		
		novoJogo.addActionListener(new novoJogoController(this));
		carregarJogo.addActionListener(new carregarJogoController(this, fileChooser));

		panel.add(novoJogo);
		panel.add(carregarJogo);
		
		getContentPane().add(panel);

		panel.setBackground(Color.WHITE);
		
		setLocation(new Point(x,y));
		getContentPane().setPreferredSize(new Dimension(LARG_DEFAULT,ALT_DEFAULT));
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
