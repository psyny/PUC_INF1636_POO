import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JanelaTabuleiro extends JFrame {
	
	public final int LARG_DEFAULT = 800;
	public final int ALT_DEFAULT = 600;
	
	private JPanel panel = new JPanel();
	
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
		
		ImageIcon i = new ImageIcon("Tabuleiro-Original.jpg");
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
