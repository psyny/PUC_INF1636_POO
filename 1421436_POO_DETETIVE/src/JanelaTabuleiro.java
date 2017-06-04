import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaTabuleiro extends JFrame {
	
	public final int LARG_DEFAULT = 576;
	public final int ALT_DEFAULT = 604;
	
	private JPanel panel = new JPanel();
	
	public JButton dado;
	
	public int valor_Dado = 0;
	
	public class dadoController implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.janelaDados = new JanelaDados("Dados");
			Main.janelaDados.setVisible(true);
		}
		
	}
	
	public JanelaTabuleiro(String s)
	{
		super(s);
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		
		panel.setLayout(null);
		
		panel.setBackground(Color.WHITE);
		
		dado = new JButton("Dado");
		dado.setBounds(253, 263, 90, 130);
		dado.addActionListener(new dadoController());
		panel.add(dado);
		
		Grid grid_jogadores = new Grid(new Dimension(LARG_DEFAULT, ALT_DEFAULT), this);
		panel.add(grid_jogadores);
		
		ImageIcon i = new ImageIcon("Tabuleiro-Clue-C.jpg");
		JLabel l=new JLabel();
		l.setIcon(i);
		l.setBackground(Color.RED);
		l.setOpaque(true);
		l.setBounds(0, 0, LARG_DEFAULT, ALT_DEFAULT);
		panel.add(l);
		
		getContentPane().add(panel);
		
		setLocation(new Point(x,y));
		getContentPane().setPreferredSize(new Dimension(LARG_DEFAULT,ALT_DEFAULT));
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
