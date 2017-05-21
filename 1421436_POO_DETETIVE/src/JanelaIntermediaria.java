import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JanelaIntermediaria extends JFrame {
	
	public final int LARG_DEFAULT = 300;
	public final int ALT_DEFAULT = 200;
	
	private JPanel panel = new JPanel();
	
	private JList lista_de_personagens;
	private JButton start;
	
	Boolean teste = true;
	
	class lista_de_personagensController implements ListSelectionListener
	{
		public JList list;
		public JButton button;
		
		public lista_de_personagensController(JList list, JButton button)
		{
			this.list = list;
			this.button = button;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			List lista = list.getSelectedValuesList();
			if(lista.size() >= 3)
				button.setEnabled(true);
			else
				button.setEnabled(false);
		}
	}
	
	class startController implements ActionListener
	{
		JFrame frame;
		
		public startController(JFrame frame)
		{
			this.frame = frame;
		}

		
		@Override
		public void actionPerformed(ActionEvent e) {	
			Main.janelaTabuleiro = new JanelaTabuleiro("Detetive");
			Main.janelaTabuleiro.setVisible(true);
			frame.setVisible(false);
		}
	}
	
	public JanelaIntermediaria(String s)
	{
		super(s);
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-LARG_DEFAULT/2;
		int y=sa/2-ALT_DEFAULT/2;
		
		panel.setLayout(null);
		
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Batman");
		listModel.addElement("L");
		listModel.addElement("Carmen San-diego");
		listModel.addElement("Sherlock Homes");
		listModel.addElement("Ed Mort");
		listModel.addElement("Pantera Cor de Rosa");
		
		start = new JButton("Começar o Jogo");
		start.setBounds(0, 110, LARG_DEFAULT, 100);
		start.addActionListener(new startController(this));
		start.setEnabled(false);
		
		lista_de_personagens = new JList(listModel);
		lista_de_personagens.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lista_de_personagens.addListSelectionListener(new lista_de_personagensController(lista_de_personagens, start));
		lista_de_personagens.setBounds(0, 0, LARG_DEFAULT, 110);
		
		panel.add(lista_de_personagens);
		panel.add(start);

		getContentPane().add(panel);

		panel.setBackground(Color.WHITE);
		
		setLocation(new Point(x,y));
		getContentPane().setPreferredSize(new Dimension(LARG_DEFAULT,ALT_DEFAULT));
		pack();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
