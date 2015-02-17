package game.scenes;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.sound.midi.Sequencer;
import javax.swing.JPanel;

import game.GameConstants;
import game.scenes.drawers.GameOverDrawer;
import game.scenes.events.GameOverSceneExitEvent;
import game.scenes.listeners.GameOverSceneListener;

/**
 * Cena de término de jogo.
 * 
 * @author André Martins (amartins@ymail.com).
 *
 */
public class GameOverScene extends BaseScene<GameOverDrawer, GameOverSceneListener> {

	private boolean isPlayerWinner;
    private BoardMouseListener mouseListener; 
    private Sequencer midi;
	
	public GameOverScene(JPanel panel
		, boolean isPlayerWinner)
	{
		super(panel);
		
		this.isPlayerWinner = isPlayerWinner;
        this.mouseListener = new BoardMouseListener(); 

		this.drawer = new GameOverDrawer(panel);
        
		this.panel.addMouseListener(this.mouseListener);

		if (isPlayerWinner)
        	this.midi = this.playMIDI(GameConstants.AudioPlayerWins);
		else
			this.midi = this.playMIDI(GameConstants.AudioPlayerLoses);
	}
	
	/**
	 * Dispara o evento de término do jogo.
	 * @param e	Objeto representando o evento disparado. 
	 */
    private void fireGameOverSceneExitEvent(GameOverSceneExitEvent e)
    {
        for (GameOverSceneListener l : this.listeners) l.gameOverSceneExitReceived(e);
    }

	@Override
	public void Draw(Graphics2D g) 
	{
		this.drawer.drawEndGame(g, this.isPlayerWinner);
	}
	
	@Override
	public void Dispose()
	{
		super.Dispose();
		
		this.midi.stop();
		this.panel.removeMouseListener(this.mouseListener);
	}
	
	/**
     * Adaptador para captura de cliques do mouse.
     */
    class BoardMouseListener extends MouseAdapter 
    {
    	@Override
        public void mousePressed(MouseEvent e) {
    		fireGameOverSceneExitEvent(new GameOverSceneExitEvent(this));
        }
    }
}
