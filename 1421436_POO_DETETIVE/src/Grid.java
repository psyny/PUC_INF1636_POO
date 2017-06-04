import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grid extends JPanel{
	
	public Dimension size;
	public Point position = new Point(0, 0);
	
	public JanelaTabuleiro frame;
	
	public int tile_size = 25;
	
	public ArrayList<Jogador> listaDeJogadores = new ArrayList<Jogador>();
	public Jogador jogadorDaVez;
	
	public ArrayList<Map> mapa;
	
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
	
	class Map
	{
		public Point position;
		public String type;
		public boolean free = true;
		
		public Map(Point p, String s)
		{
			position = p;
			type = s;
		}
		
		public boolean isRoom()
		{
			if(type.equals(".") && !type.equals("#"))
				return true;
			return false;
		}
		
		public boolean isDoor()
		{
			if(type.equals("a") || type.equals("b") || type.equals("c") || type.equals("d") || type.equals("e") || type.equals("f") || type.equals("g") || type.equals("h") || type.equals("i"))
				return true;
			return false;
		}
		
		public boolean isFloor()
		{
			if(type.equals("."))
				return true;
			return false;
		}
		
		public boolean isSecretPassage()
		{
			if(type.equals("a") || type.equals("c") || type.equals("f") || type.equals("i"))
				return true;
			return false;
		}
		
		public Color getColor()
		{
			switch(type)
			{
				case ".":
					return Color.BLACK;
					
				case "#":
					return Color.WHITE;
					
				case "A":
					return Color.BLUE;
				
				case "B":
					return Color.GREEN;
					
				case "C":
					return Color.RED;
					
				case "D":
					return Color.CYAN;

				case "E":
					return Color.MAGENTA;
					
				case "F":
					return Color.ORANGE;
					
				case "G":
					return Color.PINK;
					
				case "H":
					return Color.YELLOW;
					
				case "I":
					return Color.BLUE;
					
				case "Z":
					return Color.WHITE;
					
				default:
					return Color.LIGHT_GRAY;
			}
		}
	}
	
	public ArrayList<Map> buildMap(String fileName)
	{
		mapa = new ArrayList<Map>();
		
		try{
			BufferedReader file = new BufferedReader(new FileReader(fileName));
			
			String sCurrentLine;
			int i = 0, j = 0;
		
			while ((sCurrentLine = file.readLine()) != null) 
			{
				for(String casa : sCurrentLine.split(""))
				{
					mapa.add(new Map(new Point(j, i), casa));
					j++;
				}
				j = 0;
				i++;
			}
		}
		catch(Exception e)
		{
			
		}
		
		return mapa;
	}
	
	public Grid(Dimension size, JanelaTabuleiro frame)
	{
		this.size = size;
		this.frame = frame;
		
		ArrayList<Map> mapa = buildMap("tabuleiro_oficial.txt");
		
		Jogador batman = new Jogador(new Point(10, 9), "Batman", Color.BLACK);
		Jogador pantera_cor_de_rosa = new Jogador(new Point(3, 7), "Pantera cor de Rosa", Color.PINK);
		Jogador Sherlock = new Jogador(new Point(20, 6), "Sherlock Homes", Color.ORANGE);
		Jogador EdMort = new Jogador(new Point(5, 18), "Ed Mort", Color.GREEN);
		
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
		
		for(Map map : mapa)
		{
			drawCasa(g, map);
		}
	}
	
	public void drawCasa(Graphics g, Map map)
	{
		Graphics2D g2d=(Graphics2D) g;
		
		int posX = map.position.x * tile_size;
		int posY = map.position.y * tile_size;
		int larg = tile_size;
		int alt = tile_size;
		
		Rectangle2D rectangle=new Rectangle2D.Double(posX, posY, larg - 1, alt - 1);
		g2d.setColor(map.getColor());
		g2d.draw(rectangle);
	}
	
	public void drawJogador(Graphics g, Jogador jogador)
	{
		Graphics2D g2d=(Graphics2D) g;
		
		int posX = jogador.posicao.x * tile_size;
		int posY = jogador.posicao.y * tile_size;
		int larg = jogador.size.width;
		int alt = jogador.size.height;
		
		Rectangle2D rectangle=new Rectangle2D.Double(posX + 2, posY + 2, larg, alt);
		g2d.setColor(jogador.cor);
		g2d.fill(rectangle);
	}
	
	public boolean canGo(Point origem, Point destino)
	{
		int moves = frame.valor_Dado;
		Map dest = null, orig = null;
		
		for(Map map : mapa)
		{
			if(map.position.equals(destino))
				dest = map;
			if(map.position.equals(origem))
				orig = map;
		}
		
		if(dest != null && orig != null)
		{
			if(manhDist(origem, destino) <= moves && ( dest.isFloor() || dest.isDoor() ) && dest.free)
			{
				orig.free = true;
				dest.free = false;
				return true;
			}
			
			if(passagemSecreta(origem, destino))
				return true;
		}
			
		return false;
	}
	
	public boolean passagemSecreta(Point origem, Point destino)
	{
		Map dest = null, orig = null;
		for(Map map : mapa)
		{
			if(map.position.equals(destino))
				dest = map;
			if(map.position.equals(origem))
				orig = map;
		}
		
		if(dest != null && orig != null)
		{
			if(orig.isSecretPassage() && dest.isSecretPassage())
			{
				if(dest.free)
				{
					dest.free = false;
					orig.free = true;
					return true;
				}
			}
		}
		
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
