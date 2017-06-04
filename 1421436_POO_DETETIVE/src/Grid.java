import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grid extends JPanel{
	
	public Dimension size;
	public Point position = new Point(0, 0);
	
	public JanelaTabuleiro frame;
	
	public int tile_size = 23;
	
	public ArrayList<Jogador> listaDeJogadores = new ArrayList<Jogador>();
	public Jogador jogadorDaVez;
	
	class MouseController implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			Point gridPos = position_to_grid(e.getPoint());
			if(canGo(jogadorDaVez.posicao, gridPos))
			{
				jogadorDaVez.mover(gridPos);
				if(listaDeJogadores.indexOf(jogadorDaVez) + 1 < listaDeJogadores.size())
					jogadorDaVez = listaDeJogadores.get(listaDeJogadores.indexOf(jogadorDaVez) + 1);
				else
					jogadorDaVez =  listaDeJogadores.get(0);
				frame.valor_Dado = 0;
				repaint();
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mousePressed(MouseEvent e) 
		{
			
		}
	
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			
		}
	}
	
	class Jogador
	{
		public Point posicao;
		public Dimension size;
		public String nome;
		public Color cor;
		
		public Jogador(Point pos, String nome, Color cor)
		{
			this.nome = nome;
			this.posicao = pos;
			this.size = new Dimension(20, 20);
			this.cor = cor;
			listaDeJogadores.add(this);
		}
		
		public void mover(Point novaPos)
		{
			this.posicao = novaPos;
		}
	}
	
	public Grid(Dimension size, JanelaTabuleiro frame)
	{
		this.size = size;
		this.frame = frame;
		
		Jogador batman = new Jogador(new Point(10, 5), "Batman", Color.BLACK);
		Jogador pantera_cor_de_rosa = new Jogador(new Point(3, 6), "Pantera cor de Rosa", Color.PINK);
		Jogador Sherlock = new Jogador(new Point(20, 3), "Sherlock Homes", Color.ORANGE);
		Jogador EdMort = new Jogador(new Point(5, 20), "Ed Mort", Color.WHITE);
		
		jogadorDaVez = batman;
		
		setPos();
	}
	
	public void setPos()
	{
		this.setLayout(null);
		this.setSize(size);
		this.setPreferredSize(size);
		this.setLocation(position);
		this.setOpaque(false);
		
		addMouseListener(new MouseController());
	}

	public void paintComponent(Graphics g)
	{
		for (Jogador jogador : listaDeJogadores) {
			drawJogador(g, jogador);
		}
	}
	
	public void drawJogador(Graphics g, Jogador jogador)
	{
		Graphics2D g2d=(Graphics2D) g;
		
		int posX = jogador.posicao.x * tile_size;
		int posY = jogador.posicao.y * tile_size;
		int larg = jogador.size.width;
		int alt = jogador.size.height;
		
		Rectangle2D rectangle=new Rectangle2D.Double(posX, posY, larg, alt);
		g2d.setColor(jogador.cor);
		g2d.fill(rectangle);
	}
	
	public boolean canGo(Point origem, Point destino)
	{
		int moves = frame.valor_Dado;
		if(manhDist(origem, destino) <= moves)
			return true;
		return false;
	}
	
	public Point position_to_grid(Point pos)
	{
		return new Point(pos.x / tile_size, pos.y / tile_size);
	}
	
	private int manhDist(Point p1, Point p2)
	{
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
	}

}
