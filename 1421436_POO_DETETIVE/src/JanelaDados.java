import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class JanelaDados extends JFrame
{
	public final int LARG_DEFAULT = 210;
	public final int ALT_DEFAULT = 160;
	
	private JButton andar;
	
	private JPanel panel = new JPanel();
	
	public Dado d1, d2;
	
	class andarController implements ActionListener
	{
		JFrame frame;
		
		public andarController(JFrame frame)
		{
			this.frame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Main.janelaTabuleiro.valor_Dado = d1.getValor() + d2.getValor();
			frame.setVisible(false);
		}
	}

	public JanelaDados(String s)
	{
		super(s);
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		
		panel.setLayout(null);
		
		d1 = new Dado();
		d2 = new Dado();
		
		ImageIcon dado1 = new ImageIcon(dado_ImagemValor(d1.getValor()));
		JLabel dado1_lable =new JLabel();
		dado1_lable.setIcon(dado1);
		dado1_lable.setBackground(Color.WHITE);
		dado1_lable.setOpaque(true);
		dado1_lable.setBounds(5, 5, 100, 110);
		panel.add(dado1_lable);
		
		ImageIcon dado2 = new ImageIcon(dado_ImagemValor(d2.getValor()));
		JLabel dado2_lable =new JLabel();
		dado2_lable.setIcon(dado2);
		dado2_lable.setBackground(Color.WHITE);
		dado2_lable.setOpaque(true);
		dado2_lable.setBounds(110, 5, 100, 110);
		panel.add(dado2_lable);
		
		andar = new JButton("Andar");
		
		andar.setBounds(60, 120, 80, 30);
		
		andar.addActionListener(new andarController(this));
		
		panel.add(andar);
		
		getContentPane().add(panel);

		panel.setBackground(Color.WHITE);
		
		setLocation(new Point(x,y));
		getContentPane().setPreferredSize(new Dimension(LARG_DEFAULT,ALT_DEFAULT));
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public String dado_ImagemValor(int valor)
	{
		return "dado" + valor + ".jpg";
	}
}