package game.scenes.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

import java.util.List;

import core.BoardPoint;
import core.Ship;

import game.GameConstants;

/**
 * Objeto de desenho da cena de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class GameDrawer extends BoardDrawer {

    private Image uiHits;
    private Image uiShots;
    private Image waterType1;
    private Image waterType2;
    private Image waterShot;
    private Image shipHit;
    
	public GameDrawer(JPanel panel)
	{
    	super(panel);
    	
        this.uiHits = this.getImage(GameConstants.UIHits);
        this.uiShots = this.getImage(GameConstants.UIShots);
        this.waterType1 = this.getImage(GameConstants.WaterType1);
        this.waterType2 = this.getImage(GameConstants.WaterType2);
        this.waterShot = this.getImage(GameConstants.WaterShot);
        this.shipHit = this.getImage(GameConstants.ShipHit);
	}
	
	/**
	 * Desenha os dados de um jogador.
	 *
	 * @param g         	Objeto Graphics no qual o tabuleiro deverá ser desenhado.
	 * @param playerName	Nome do jogador.
	 * @param numShots		Número de disparos.
	 * @param numDestroyed	Número de embarcações destruídas.
	 * @param p				Ponto inicial do desenho.
	 * @param isTurn		Indica se o tabuleiro deve ser criado para um turno.
	 */
	public void drawPlayerData(Graphics2D g
		, String playerName
		, int numShots
		, int numDestroyed
		, Point p
		, boolean isTurn)
	{
        Font small = new Font("Verdana", Font.BOLD, 14);
        g.setColor(Color.BLACK);
        g.setFont(small);        

		g.drawImage(this.uiShots, p.x, p.y + 15, panel);
		g.drawImage(this.uiHits, p.x + 60, p.y + 15, panel);
        
        g.drawString(String.valueOf(numShots), p.x + 20, p.y + 28);
        g.drawString(String.valueOf(numDestroyed), p.x + 80, p.y + 28);
        
		if (isTurn) g.setColor(Color.RED);
        g.drawString(playerName, p.x, p.y);
	}
	
    /**
     * Desenha um tabuleiro em um objeto Graphics.
     *
     * @param g         		Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param board     		Tabuleiro o qual deverá ser desenhado.
     * @param p        			Ponto inicial do tabuleiro (canto superior esquerdo).
     * @param ships 			Embarcações a serem exibidas (null caso não devam ser exibidas).
     * @param showOnlyDestroyed	Somente exibe embarcações destruídas. 
	 * @param highlight			Ponto a ser realçado no tabuleiro.
     */
    public void drawBoard(Graphics2D g
        , BoardPoint[][] board
        , Point p
        , List<Ship> ships
        , boolean showOnlyDestroyed
        , Point highlight)
    {
    	this.drawBoardInfo(g, p);
    	
        boolean useWater1;
        int x = p.x
            , y = p.y;
        
        for (int i = 0; i < board.length; i++)
        {
        	g.setColor(Color.BLACK);
            x = p.x;
            
            useWater1 = (i % 2 == 0);

            for (int j = 0; j < board[0].length; j++)
            {
                BoardPoint point = board[i][j];
                
                Image img;

                //Verifica qual imagem utilizar em relação à água e ataque.
                if (point.isAttacked())
                {
                    if (point.isShip()) img = this.shipHit;
                    else img = this.waterShot;
                }
                else
                {
	                if (useWater1) img = this.waterType1;
	                else img = this.waterType2;
                }

                //Desenha a imagem na posição atual do ponto.
                g.drawImage(img, x, y, panel);

                x += GameConstants.BoardPointSize;

                //Inverte o tipo de imagem da água.
                if (useWater1) useWater1 = false;
                else useWater1 = true;
                
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
        
        //Desenha as embarcações, caso haja alguma a ser desenhada.
        if (ships != null) this.drawShips(g, p, ships, showOnlyDestroyed);      
        
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
     * Desenha as embarcações do tabuleiro.
     * 
     * @param g         		Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param p        		 	Ponto inicial do tabuleiro (canto superior esquerdo).
     * @param ships 			Embarcações a serem exibidas (null caso não devam ser exibidas).
     * @param showOnlyDestroyed	Somente exibe embarcações destruídas. 
     */
    private void drawShips(Graphics2D g
    	, Point p
    	, List<Ship> ships
    	, boolean showOnlyDestroyed)
    {
    	for (Ship s : ships)
        {
    		if (showOnlyDestroyed && !s.isDestroyed()) continue;
    		this.drawShip(g, p, s);   
        }
    }
}
