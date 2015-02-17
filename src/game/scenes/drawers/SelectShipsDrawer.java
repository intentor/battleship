package game.scenes.drawers;

import game.GameConstants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import core.*;

/**
 * Objeto de desenho da cena de seleção de embarcações.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class SelectShipsDrawer extends BoardDrawer {

    private Image waterType1;
    private Image waterType2;
    private Image allShips;
    
    public SelectShipsDrawer(JPanel panel)
	{
    	super(panel);
    	
        this.waterType1 = this.getImage(GameConstants.WaterType1);
        this.waterType2 = this.getImage(GameConstants.WaterType2);
        this.allShips = this.getImage(GameConstants.Ships);        
	}
    
	/**
	 * Desenha informações sobre as embarcações.
	 *
	 * @param g         	Objeto Graphics no qual o tabuleiro deverá ser desenhado.
	 * @param p				Ponto inicial do desenho.
	 */
	public void drawInfo(Graphics2D g
	    , Point p)
	{
		//Título.
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 20)); 
        g.drawString("POSICIONAMENTO DA FROTA", p.x + 170, p.y);
        
        g.drawImage(this.allShips, 10, 10, this.panel);

        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(p.x + 125, p.y + 345, p.x + 800, p.y + 345);
        g.drawLine(p.x + 652, p.y, p.x + 652, p.y + 400);
        
        g.setFont(new Font("Verdana", Font.PLAIN, 9)); 
        g.setColor(Color.DARK_GRAY);
        g.drawString("INSTRUÇÕES | botão esquerdo do mouse: seleção e posicionamento | botão direito do mouse: rotação", p.x + 125, p.y + 362);
	}
    
    /**
     * Desenha um tabuleiro em um objeto Graphics.
     *
     * @param g         Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param board     Tabuleiro o qual deverá ser desenhado.
     * @param p        	Ponto inicial do tabuleiro (canto superior esquerdo).
     * @param ships 	Embarcações a serem exibidas (null caso não devam ser exibidas).
	 * @param highlight	Ponto a ser realçado no tabuleiro.
     */
    public void drawBoard(Graphics2D g
        , BoardPoint[][] board
        , Point p
        , List<Ship> ships
        , Point highlight)
    {
    	this.drawBoardInfo(g, p);
    	
        boolean useWater1;
        int x = p.x
            , y = p.y;
        
        for (int i = 0; i < GameConstants.BoardY; i++)
        {
        	g.setColor(Color.BLACK);
            x = p.x;

            useWater1 = (i % 2 == 0);
            
            for (int j = 0; j < GameConstants.BoardX; j++)
            {                
                Image img;
            	if (useWater1) {
            		img = this.waterType1;
            		useWater1 = false;
            	}
                else {
                	img = this.waterType2;
                	useWater1 = true;
                }

                //Desenha a imagem na posição atual do ponto.
                g.drawImage(img, x, y, panel);

                x += GameConstants.BoardPointSize;
                
                //Desenha linha no eixo Y.
                g.setColor(Color.WHITE);
                g.drawLine(p.x + (j * GameConstants.BoardPointSize)
                	, p.y
                	, p.x + (j * GameConstants.BoardPointSize)
                	, p.y + (GameConstants.BoardY * GameConstants.BoardPointSize));
                
                //Verifica ponto para realce.
	            if (highlight != null &&
	            	highlight.x == j &&
	            	highlight.y == i)
	            {
	            	highlight = new Point(p.x + (j * GameConstants.BoardPointSize)
	            		, p.y + (i * GameConstants.BoardPointSize));
	            }
            }

            y += GameConstants.BoardPointSize;
            
            //Desenha linha no eixo X.
            g.drawLine(p.x
                	, p.y + (i * GameConstants.BoardPointSize)
                	, p.x + (GameConstants.BoardX * GameConstants.BoardPointSize)
                	, p.y + (i * GameConstants.BoardPointSize));
        }
        
        //Desenha as embarcações.
        if (ships != null) this.drawShips(g, p, ships);      
        
        //Desenha borda no tabuleiro.         
        g.setColor(Color.GRAY);
        g.drawRect(p.x
        	, p.y
        	, GameConstants.BoardX * GameConstants.BoardPointSize
        	, GameConstants.BoardY * GameConstants.BoardPointSize);
        
        //Realiza realce de ponto no tabuleiro, caso seja necessário.
        if (highlight != null) 
        {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(2.0f));
			g.drawRect(highlight.x
				, highlight.y
				, GameConstants.BoardPointSize
				, GameConstants.BoardPointSize); 
        } 
    }
    
    /**
     * Desenha as em seleção embarcações do tabuleiro.
     * 
     * @param g         		Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param p        		 	Ponto inicial das informações.
     * @param ships 			Embarcações a serem exibidas.
     */
    public void drawShipsInSelection(Graphics2D g
    	, Point p
    	, List<Ship> ships)
    {
        //Posicionamento das embarcações.
        int x = p.x + 190, y = p.y + 65;
        g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(x, y, x, y + (ships.size() * GameConstants.BoardPointSize));
        Ship shipInMove = null;
        
        for (int i = 0; i <= ships.size(); i++)
        {
        	int yLine = y + (i * GameConstants.BoardPointSize);        	
        	g.drawLine(x, yLine, x + 120, yLine);    
        	
        	if (i > 0) 
        	{
        		Ship s = ships.get(i - 1);
        		g.drawString(s.getShipType().shipName().toUpperCase(), x + 5, yLine - 5);
        		
        		//Somente posiciona a embarcação se esta ainda não tiver sido posta no tabuleiro.
        		if (!s.isDeployed())
        		{
        			if (s.getMovePoint() != null)
        			{
        				shipInMove = s;
        				continue;
        			}
        			else s.setShipAxis(Axis.Y); 
        			Point sp =  s.getStartPoint();
        			
        			if (sp == null)
        			{
		    			sp = new Point(x + 130 - (s.getShipType().size() * GameConstants.BoardPointSize)
		    				, yLine - GameConstants.BoardPointSize);
		    			s.setStartPoint(sp);
        			}
	    			
	    			AffineTransform transform = new AffineTransform();
	                transform.setToTranslation(sp.x, sp.y);
	    			
	    			this.drawShip(g, transform, s.getShipType());
        		}
        	}
        }
        
        if (shipInMove != null)
        {
        	Point sp = shipInMove.getMovePoint();
						
			AffineTransform transform = new AffineTransform();
			
			if (shipInMove.getShipAxis() == Axis.X) 
        	{
        		transform.setToTranslation(sp.x + GameConstants.BoardPointSize, sp.y);
        		transform.rotate(Math.toRadians(90));
        	}
        	else
            	transform.setToTranslation(sp.x, sp.y);
			
			this.drawShip(g, transform, shipInMove.getShipType());
		
	        g.setColor(Color.RED);
        	g.setStroke(new BasicStroke(1.5f));
			Rectangle r = shipInMove.getBounds(true);
			g.draw(r);
        	g.setStroke(new BasicStroke(1.0f));
	        g.setColor(Color.LIGHT_GRAY);
        }
    }
    
    /**
     * Desenha as embarcações do tabuleiro.
     * As embarcações somente são desenhadas se já tiverem uma posição incial.
     * 
     * @param g         		Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param p        		 	Ponto inicial do tabuleiro (canto superior esquerdo).
     * @param ships 			Embarcações a serem exibidas (null caso não devam ser exibidas).
     */
    private void drawShips(Graphics2D g
    	, Point p
    	, List<Ship> ships)
    {
    	for (Ship s : ships)
        {
        	if (!s.isDeployed()) continue;
    		this.drawShip(g, p, s);        	
        }
    }
}
