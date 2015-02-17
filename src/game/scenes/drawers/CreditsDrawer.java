package game.scenes.drawers;

import game.GameConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Objeto de desenho da cena de cr�ditos.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public class CreditsDrawer extends BaseDrawer {

	private Image credits;
	
	public CreditsDrawer(JPanel panel) {
		super(panel);
		
		panel.setBackground(Color.BLACK);
		this.credits = this.getImage(GameConstants.Credits);
	}
	
	/**
	 * Cria o label de sa�da.
	 */
	public JLabel createExitLabel()
	{
		JLabel label = new JLabel("<- Voltar ");
		label.setName("lblExitScene");
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setVerticalAlignment(SwingConstants.CENTER);
		
		label.setOpaque(false);
		label.setForeground(Color.LIGHT_GRAY);
		label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		label.setSize(135, 30);
		label.setLocation(new Point(-1, 360));
		
		return label;
	}	
	
	/**
	 * Realiza desenho dos cr�ditos.
	 * 
	 * @param g	Objeto Graphics para desenho.
	 */
	public void drawCredits(Graphics2D g)
	{
		g.drawImage(this.credits, 497, 0, this.panel);
		
		g.setColor(Color.LIGHT_GRAY);		 
	    g.setFont(new Font("Verdana", Font.PLAIN, 36)); 
	    g.rotate(-1.57);
	    g.drawString("C R � D I T O S", -395, 490);
	    g.rotate(1.57);
	    
	    g.rotate(-0.1);		
	    this.drawGameMember(g, "dire��o, programa��o e �udio", new String[] { "ANDR� MARTINS" }, new Point(30, 45));
		this.drawGameMember(g, "arte", new String[] { "GABRIEL ROQUE" }, new Point(200, 100));
		this.drawGameMember(g, "documenta��o", new String[] { "ANDERSON SZALAI" }, new Point(30, 155));
		this.drawGameMember(g, "documenta��o e testes", new String[] { "LEANDRO MONGE" }, new Point(195, 210));
		this.drawGameMember(g, "m�sicas", new String[] { 
				"Chrono Cross - Dragon Rider"
				, "Front Mission 3 - Plain"
				, "FFVII - Requiem"
				, "FFVII - Victory Fanfare" 
				, "FF Tatics - Germinas Peak"
				, "FF Tatics - Run Past Through" } , new Point(80, 265));
	}
	
	/**
	 * Desenha informa��es de cr�ditos.
	 * 
	 * @param g			Objeto Graphics para desenho.
	 * @param title		T�tulo do cr�dito.
	 * @param members	Membros do cr�dito.
	 * @param p			Ponto no qual dever� ser posta a informa��o.
	 */
	private void drawGameMember(Graphics2D g
		, String title
		, String[] members
		, Point p)
	{
		 g.setColor(Color.WHITE);		 
	     g.setFont(new Font("Verdana", Font.ITALIC, 12)); 
	     g.drawString(title, p.x, p.y);	     

	     int y = 20;
	     for (String member : members)
	     {
		     g.setFont(new Font("Verdana", Font.BOLD, 18)); 
		     g.drawString(member, p.x + 35, p.y + y);
		     y += 20;
	     }
	}
}
