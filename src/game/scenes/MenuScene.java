package game.scenes;

import game.GameConstants;
import game.scenes.drawers.MenuDrawer;
import game.scenes.events.ExitSceneEvent;
import game.scenes.events.StartGameEvent;
import game.scenes.listeners.MenuSceneListener;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.Util;

/**
 * Cena do menu.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class MenuScene extends BaseScene<MenuDrawer, MenuSceneListener> implements ActionListener  {

    private LabelsMouseListener mouseListener;
    private LabelsMouseMoveListener mouseMoveListener;
    private BoardMouseMoveListener mouseMotionListener; 
    
    private JLabel lblStartGame;
    private JLabel lblEndGame;
    private JLabel lblCredits;
    
    private List<Point> points;
    
    private Timer timer;
    
	public MenuScene(JPanel panel)
	{   
		super(panel);
		
		this.drawer = new MenuDrawer(panel);		

        this.mouseListener = new LabelsMouseListener(); 
        this.mouseMoveListener = new LabelsMouseMoveListener();  
        
		this.lblStartGame = this.drawer.createLabelStartGame();
		this.lblCredits = this.drawer.createLabelCredits();
		this.lblEndGame = this.drawer.createLabelEndGame();
		
		this.configureLabel(this.lblStartGame);
		this.configureLabel(this.lblCredits);
		this.configureLabel(this.lblEndGame);
        
        this.mouseMotionListener = new BoardMouseMoveListener();
		this.panel.addMouseMotionListener(this.mouseMotionListener);
		
        this.timer = new Timer(1500, this);
        this.timer.start();
        
        this.points = new LinkedList<Point>();
        
		this.setBackgroundMusic(GameConstants.AudioMenuScene);
		
		panel.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int qttPoints = Util.GetRandomNumber(7);		
		do {
			points.add(Util.GenerateRandomPosition(this.drawer.MaxPointsOnMenuBoard, this.drawer.MaxPointsOnMenuBoard));
		} while (--qttPoints >= 0);		
		
		this.panel.repaint();
	}
	
	@Override
	public void Draw(Graphics2D g) 
	{        
		this.drawer.drawMenuInfo(g);
		this.drawer.drawBackground(g, this.points);
	}
	
	@Override
    public void Dispose()
    {
		super.Dispose();

		this.panel.remove(this.lblStartGame);
		this.panel.remove(this.lblCredits);
		this.panel.remove(this.lblEndGame);
		this.panel.removeMouseMotionListener(this.mouseMotionListener);
    }
	
	/**
	 * Realiza configuração de um label.
	 * 
	 * @param label JLabel a ser configurado.
	 */
	private void configureLabel(JLabel label)
	{
		panel.add(label);		
		label.addMouseListener(this.mouseListener);
		label.addMouseMotionListener(this.mouseMoveListener);
	}
	
	/**
	 * Dispara o evento de início do jogo.
	 * 
	 * @param e	Objeto representando o evento disparado. 
	 */
    private void fireStarGameEvent(StartGameEvent e)
    {
        for (MenuSceneListener l : this.listeners) l.startGameReceived(e);
    }
	
	/**
	 * Dispara o evento de exibição de créditos do jogo.
	 * 
	 * @param e	Objeto representando o evento disparado. 
	 */
	private void fireShowCreditsEvent(ExitSceneEvent e)
	{
        for (MenuSceneListener l : this.listeners) l.showCreditsReceived(e);
	}
    
    /**
     * Adaptador para captura de movimentos do mouse no tabuleiro.
     */
    class BoardMouseMoveListener implements MouseMotionListener
    {
    	@Override
    	public void mouseMoved(MouseEvent e)
    	{
    		drawer.setLabelMouseOut(lblStartGame);
    		drawer.setLabelMouseOut(lblCredits);
    		drawer.setLabelMouseOut(lblEndGame);
    	}

		@Override
		public void mouseDragged(MouseEvent e) {		
		}
    }
    
    /**
     * Adaptador para captura de movimentos do mouse nos labels.
     */
    class LabelsMouseMoveListener implements MouseMotionListener 
    {
    	@Override
    	public void mouseMoved(MouseEvent e)
    	{
    		JLabel label = ((JLabel)e.getSource());
    		drawer.setLabelMouseOver(label);
    	}

		@Override
		public void mouseDragged(MouseEvent e) 
		{
		}
    }
    
	/**
     * Adaptador para captura de cliques do mouse.
     */
    class LabelsMouseListener extends MouseAdapter 
    {
    	@Override
        public void mousePressed(MouseEvent e) {
    		//Verifica qual o label pressionado.
    		String labelName = ((JLabel)e.getSource()).getName();
    		if (labelName == "lblStartGame")
				fireStarGameEvent(new StartGameEvent(this));
    		if (labelName == "lblCredits")
    			fireShowCreditsEvent(new ExitSceneEvent(this));
    		else if (labelName == "lblEndGame")
				System.exit(1);
        }
    }
}
