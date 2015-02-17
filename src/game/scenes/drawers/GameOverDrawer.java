package game.scenes.drawers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import game.GameConstants;

/**
 * Objeto de desenho da cena de término de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class GameOverDrawer extends BaseDrawer {

    private Image playerWin;
    private Image playerLost;
    
	public GameOverDrawer(JPanel panel)
	{    	
		super(panel);
		
        this.playerWin = this.getImage(GameConstants.PlayerWin);
        this.playerLost = this.getImage(GameConstants.PlayerLost);
	}
	
	/**
	 * Desenha a cena de término de jogo.
	 * 
	 * @param isPlayerWinner	Indicação se o jogador foi ou não vencedor.
	 */
	public void drawEndGame(Graphics2D g
		, boolean isPlayerWinner)
	{
		Image img;
		
        g.setFont(new Font("Verdana", Font.BOLD, 40)); 
        
        if (isPlayerWinner)
        {
        	img = this.playerWin;
        	this.panel.setBackground(Color.WHITE);
            g.setColor(Color.BLACK);
        	g.drawString("Jogador venceu!", 160, 360);
        }
        else
        {
        	img = this.playerLost;
        	this.panel.setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
        	g.drawString("CPU Venceu!", 205, 360);
        }

        g.setFont(new Font("Verdana", Font.PLAIN, 12));
    	g.drawString("Clique na janela para continuar", 250, 390);
        
		g.drawImage(img, 120, 0, this.panel);        	
	}
}
