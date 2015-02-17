package game.scenes.drawers;

import game.GameConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import core.Util;

/**
 * Objeto de desenho da cena de menu.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class MenuDrawer extends BaseDrawer {

	/**
	 * Tamanho dos pontos no tabuleiro do menu.
	 */
	public final int BoardPointSize = 40;
	
	/**
	 * Número máximo de pontos no tabuleiro do menu.
	 */
	public final int MaxPointsOnMenuBoard = 7;
	
	private final String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
    private Image waterType1;
    private Image waterType2;
    private Image waterShot;
    private Image shipHit;
    private Image creditsIntentor;
    private Image creditsFATEC;
    private Image creditsTools;    
	
	public MenuDrawer(JPanel panel)
	{    	
		super(panel);

	    this.waterType1 = this.getImage(GameConstants.WaterType1Big);
	    this.waterType2 = this.getImage(GameConstants.WaterType2Big);
	    this.waterShot = this.getImage(GameConstants.WaterShotBig);
	    this.shipHit = this.getImage(GameConstants.ShipHitBig);
	    this.creditsIntentor = this.getImage(GameConstants.Intentor);
	    this.creditsFATEC = this.getImage(GameConstants.FATEC);
	    this.creditsTools = this.getImage(GameConstants.Tools);
	}
	
	/**
	 * Cria label de início de jogo.
	 * 
	 * @return JLabel criado.
	 */
	public JLabel createLabelStartGame()
	{
		JLabel label = new JLabel("Jogar ");
		label.setName("lblStartGame");
		label.setLocation(new Point(-1, 10));
		
		this.setLabelData(label);
		
		return label;
	}
	
	/**
	 * Cria label de créditos do jogo.
	 * 
	 * @return JLabel criado.
	 */
	public JLabel createLabelCredits()
	{
		JLabel label = new JLabel("Créditos ");
		label.setName("lblCredits");
		label.setLocation(new Point(-1, 60));
		
		this.setLabelData(label);
		
		return label;
	}
	
	/**
	 * Cria label de término de jogo.
	 * 
	 * @return JLabel criado.
	 */
	public JLabel createLabelEndGame()
	{
		JLabel label = new JLabel("Sair ");
		label.setName("lblEndGame");
		label.setLocation(new Point(-1, 110));
		
		this.setLabelData(label);
		
		return label;
	}
	
	/**
	 * Define características de um label no
	 * qual o ponteiro do mouse não está sobre.
	 * 
	 * @param label	JLabel a ter as características aplicadas.
	 */
	public void setLabelMouseOut(JLabel label)
	{
		label.setBackground(Color.LIGHT_GRAY);
		label.setForeground(Color.DARK_GRAY);
		label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		label.setSize(120, 40);
	}
	
	/**
	 * Define características de um label no
	 * qual o ponteiro do mouse está sobre.
	 * 
	 * @param label	JLabel a ter as características aplicadas.
	 */
	public void setLabelMouseOver(JLabel label)
	{
		label.setBackground(Color.DARK_GRAY);
		label.setForeground(Color.WHITE);
		label.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		label.setSize(130, 40);
	}
	
	/**
	 * Define configurações de um label.
	 * 
	 * @param label	JLabel a ter suas configurações definidas.
	 */
	private void setLabelData(JLabel label)
	{
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setVerticalAlignment(SwingConstants.CENTER);
		
		this.setLabelMouseOut(label);
	}
	
	/**
	 * Realiza desenho de informações do menu.
	 * 
	 * @param g	 		Objeto Graphics no qual o menu deverá ser desenhado.
	 * @param points 	Pontos a serem realçados.
	 */
	public void drawMenuInfo(Graphics2D g)
	{
        g.setFont(new Font("Verdana", Font.BOLD, 55)); 
        g.setColor(Color.BLACK);
        g.drawString("BATALHA NAVAL", 150, 160);
        
        //Desenha os créditos.
        g.drawImage(this.creditsIntentor, 10, 293, panel);
        g.drawImage(this.creditsTools, 85, 298, panel);
        g.drawImage(this.creditsFATEC, 85, 345, panel);
		
		g.setFont(new Font("Verdana", Font.PLAIN, 9)); 
        g.setColor(Color.DARK_GRAY);
        g.drawString("Versão " + GameConstants.GameVersion, 570, 15);
	}
	
	/**
	 * Realiza desenho do menu.
	 * 
	 * @param g	 		Objeto Graphics no qual o menu deverá ser desenhado.
	 * @param points 	Pontos a serem realçados.
	 */
	public void drawBackground(Graphics2D g
		, List<Point> points)
	{		
		this.panel.setBackground(Color.WHITE);
		Point p = new Point (410, 210); 
		this.drawBoardInfo(g, p);

		boolean useWater1;
        int x = p.x
            , y = p.y;
        
        for (int i = 0; i < MaxPointsOnMenuBoard; i++)
        {
        	g.setColor(Color.BLACK);
            x = p.x;

            useWater1 = (i % 2 == 0);
            
            for (int j = 0; j < MaxPointsOnMenuBoard; j++)
            {                
                Image img = null;
                
                //Verifica se é para desenhar ponto colorido.
                for (Point hp : points)
                {
                	if (hp.equals(new Point(i, j)))
                	{
                		if (Util.GetRandomNumber(2) == 0) img = this.waterShot;
                		else img = this.shipHit;
                	}
                }
                
                if (img == null)
                {
	            	if (useWater1) img = this.waterType1;
	                else img = this.waterType2;
                }
            	
            	if (useWater1) useWater1 = false;
                else useWater1 = true;

                //Desenha a imagem na posição atual do ponto.
                g.drawImage(img, x, y, panel);

                x += BoardPointSize;
                
                //Desenha linha no eixo Y.
                g.setColor(Color.WHITE);
                g.drawLine(p.x + (j * BoardPointSize)
                	, p.y
                	, p.x + (j * BoardPointSize)
                	, p.y + (MaxPointsOnMenuBoard * BoardPointSize));
            }

            y += BoardPointSize;
            
            //Desenha linha no eixo X.
            g.drawLine(p.x
                	, p.y + (i * BoardPointSize)
                	, p.x + (MaxPointsOnMenuBoard * BoardPointSize)
                	, p.y + (i * BoardPointSize));
        }    
        
        //Desenha borda no tabuleiro.         
        g.setColor(Color.GRAY);
        g.drawRect(p.x
        	, p.y
        	, MaxPointsOnMenuBoard * BoardPointSize
        	, MaxPointsOnMenuBoard * BoardPointSize); 
        
        points.clear();
	}
	
	/**
	 * Realiza desenho de informações do tabuleiro do menu.
	 * 
	 * @param g 			Objeto Graphics no qual o menu deverá ser desenhado.
	 * @param startPoint	Ponto de início 
	 */
	private void drawBoardInfo(Graphics2D g
		, Point startPoint)
	{
		Point p = (Point)startPoint.clone();
		p.x -= BoardPointSize - 10;
		p.y -= BoardPointSize - 10;
		
		Font big = new Font("Verdana", Font.BOLD, 18);
        g.setColor(Color.BLACK);
        g.setFont(big);
        
		for (int i = 0; i <= MaxPointsOnMenuBoard; i++)
        {
			if (i == 0)
			{
				for (int j = 0; j < MaxPointsOnMenuBoard; j++)
				{
			        g.drawString(this.letters[j], p.x + ((j + 1) * BoardPointSize) + 4, p.y + 18);
				}
			}
			else
			{
				g.drawString(String.valueOf(i), p.x + 4, p.y + (i * BoardPointSize) + 18);				
			}
        }	
	}
}
