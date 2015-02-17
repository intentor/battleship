package game.scenes.drawers;

import game.GameConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import core.Axis;
import core.Ship;
import core.ShipType;

/**
 * Objeto de desenho de tabuleiros base, contendo características 
 * comuns a todos os objetos que realizam desenho de tabuleiros.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public abstract class BoardDrawer extends BaseDrawer {
	
	protected final String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	protected Image portaAvioes;
    protected Image encouracado;
    protected Image submarino;
    protected Image cruzador;
    protected Image destroier;
    
	public BoardDrawer(JPanel panel) {
		super(panel);
        this.portaAvioes = this.getImage(GameConstants.PortaAvioes);
        this.encouracado = this.getImage(GameConstants.Encouracado);
        this.submarino = this.getImage(GameConstants.Submarino);
        this.cruzador = this.getImage(GameConstants.Cruzador);
        this.destroier = this.getImage(GameConstants.Destroier);
	}

	/**
	 * Desenha letras e números do tabuleiro.
	 * 
	 * @param g 			Objeto Graphics no qual o tabuleiro deverá ser desenhado.
	 * @param startPoint 	Ponto inicial do tabuleiro (canto superior esquerdo).
	 */
	public void drawBoardInfo(Graphics2D g
	        , Point startPoint)
    {
		Point p = (Point)startPoint.clone();
		p.x -= GameConstants.BoardPointSize;
		p.y -= GameConstants.BoardPointSize;
		
		Font small = new Font("Verdana", Font.BOLD, 11);
        g.setColor(Color.BLACK);
        g.setFont(small);
        
		for (int i = 0; i <= GameConstants.BoardY; i++)
        {
			if (i == 0)
			{
				for (int j = 0; j < GameConstants.BoardX; j++)
				{
			        g.drawString(this.letters[j], p.x + ((j + 1) * GameConstants.BoardPointSize) + 6, p.y + 15);
				}
			}
			else
			{
				g.drawString(String.valueOf(i), p.x + (i >= 10 ? 2 : 6), p.y + (i * GameConstants.BoardPointSize) + 15);				
			}
        }
	}
	
	/**
     * Desenha uma embarcação no tabuleiro.
     * 
     * @param g		Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param p     Ponto inicial do tabuleiro (canto superior esquerdo).
     * @param s		Embarcação a ser desenhada.
     */
	protected void drawShip(Graphics2D g
    	, Point p
		, Ship s)
	{
		Point sp = s.getStartPoint();
    	AffineTransform transform = new AffineTransform();
    	
    	//Verifica se o ponto está travado no eixo X, o que causará rotação.
    	if (s.getShipAxis() == Axis.X) 
    	{
    		transform.setToTranslation(p.x + (sp.x * GameConstants.BoardPointSize) + GameConstants.BoardPointSize
    			, p.y + (sp.y * GameConstants.BoardPointSize));
    		transform.rotate(Math.toRadians(90));
    	}
    	else
        	transform.setToTranslation(p.x + (sp.x * GameConstants.BoardPointSize)
        		, p.y + (sp.y * GameConstants.BoardPointSize));  
    	
		this.drawShip(g, transform, s.getShipType());
	}
    
    /**
     * Desenha uma embarcação no tabuleiro.
     * 
     * @param g			Objeto Graphics no qual o tabuleiro deverá ser desenhado.
     * @param transform	Objeto AffineTransform contendo configurações de posicionamento.
     * @param type		Tipo da embarcação a ser desenhada.
     */
    protected void drawShip(Graphics2D g
    	, AffineTransform transform
    	, ShipType type)
    {		
    	Image ship;
    	
    	switch (type)
    	{
    		case PortaAvioes:
    			ship = this.portaAvioes;
    			break;
    			
    		case Encouracado:
    			ship = this.encouracado;
    			break;
    			
    		case Submarino:
    			ship = this.submarino;
    			break;
    			
    		case Cruzador:
    			ship = this.cruzador;
    			break;
    			
    		default:
    			ship = this.destroier;
    			break;
    	}

        g.drawImage(ship, transform, panel); 
    }
}
