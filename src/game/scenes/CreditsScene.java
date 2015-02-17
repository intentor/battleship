package game.scenes;

import game.GameConstants;
import game.scenes.drawers.CreditsDrawer;
import game.scenes.events.ExitSceneEvent;
import game.scenes.listeners.CreditsSceneListener;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cena de créditos.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class CreditsScene extends BaseScene<CreditsDrawer, CreditsSceneListener> {

	private JLabel lblExitScene;
	
	public CreditsScene(JPanel panel) {
		super(panel);
		
		this.drawer = new CreditsDrawer(panel);
		
		this.lblExitScene = this.drawer.createExitLabel();
		this.lblExitScene.addMouseListener(new LabelExitMouseListener());
		panel.add(this.lblExitScene);
		
		this.setBackgroundMusic(GameConstants.AudioCreditsScene);
	}

	@Override
	public void Draw(Graphics2D g) {
		this.drawer.drawCredits(g);
	}	
	
	@Override
    public void Dispose()
    {
		super.Dispose();

		this.panel.remove(this.lblExitScene);
    }
	
	/**
	 * Dispara o evento de saída da cena.
	 * 
	 * @param e	Objeto representando o evento disparado. 
	 */
	private void fireExitSceneEvent(ExitSceneEvent e)
	{
        for (CreditsSceneListener l : this.listeners) l.exitCreditsEventReceived(e);
	}
	
	/**
     * Adaptador para captura de cliques do mouse.
     */
    class LabelExitMouseListener extends MouseAdapter 
    {
    	@Override
        public void mousePressed(MouseEvent e) {
    		fireExitSceneEvent(new ExitSceneEvent(this));
        }
    }
}
