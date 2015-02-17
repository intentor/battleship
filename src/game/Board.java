package game;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.RenderingHints;

import game.scenes.*;
import game.scenes.events.*;
import game.scenes.listeners.*;

/**
 * Tabuleiro no qual ocorre o jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class Board extends JPanel 
	implements MenuSceneListener, CreditsSceneListener, SelectShipsSceneListener, GameSceneListener, GameOverSceneListener {
	    
	/**
	 * Cena atual em renderização.
	 */
	@SuppressWarnings("unchecked")
	private BaseScene currentScene;
	
    public Board()
    {        
        //Define o layout para possibilitar posicionamento de objetos.
		setLayout(null);
		
    	//Inicia com a cena do menu.
    	this.currentScene = this.createMenuScene();
        setDoubleBuffered(true);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        this.setRenderingHints(g);
        
        this.currentScene.Draw((Graphics2D)g);
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

	@Override
	public void showCreditsReceived(ExitSceneEvent e) {
		//Descarta a cena do menu e cria a cena de créditos.
    	this.currentScene.Dispose();
    	this.currentScene = this.createCreditsScene();
	}

	@Override
	public void exitCreditsEventReceived(ExitSceneEvent e) {
		//Descarta a cena de créditos e cria a cena do menu.
    	this.currentScene.Dispose();
    	this.currentScene = this.createMenuScene();
	}

	@Override
	public void startGameReceived(StartGameEvent e) {
    	//Descarta a cena de menu e cria cena de seleção de embarcações.
    	this.currentScene.Dispose();
    	this.currentScene = this.createSelectShipsScene(); 
    	
    	this.repaint();
	}
    
	@Override
    public void shipsDeployedReceived(ShipsDeployedEvent e)
    {    	
    	//Descarta a cena de seleção de embarcações e cria cena do jogo.
    	SelectShipsScene s = (SelectShipsScene)this.currentScene; 
    	s.Dispose();
    	this.currentScene = this.createGameScene(s);
    	
    	this.repaint();
    }
    
	@Override
    public void gameOverReceived(GameOverEvent e)
    {
    	//Descarta a cena de jogo e cria cena de término de jogo.
    	this.currentScene.Dispose();
    	this.currentScene = this.createGameOverScene(e.isPlayerWinner()); 
    	
    	this.repaint();
    }
    
	@Override
    public void gameOverSceneExitReceived(GameOverSceneExitEvent e)
    {
    	//Descarta a cena de jogo e cria cena de seleção de embarcações.
    	this.currentScene.Dispose();
    	this.currentScene = this.createMenuScene(); 
    	
    	this.repaint();
    }
	
	 /**
     * Cria cena de menu.
     * 
     * @return Cena criada.
     */
    private MenuScene createMenuScene()
    {
    	MenuScene m = new MenuScene(this); 
    	m.addListener(this);
    	
    	return m;   
    }
    
	 /**
     * Cria cena de crédtios.
     * 
     * @return Cena criada.
     */
    private CreditsScene createCreditsScene()
    {
    	CreditsScene c = new CreditsScene(this); 
    	c.addListener(this);
    	
    	return c;   
    }
    
    /**
     * Cria cena de seleção de embarcações.
     * 
     * @return Cena criada.
     */
    private SelectShipsScene createSelectShipsScene()
    {
    	SelectShipsScene s = new SelectShipsScene(this); 
    	s.addListener(this);
    	
    	return s;   
    }
    
    /**
     * Cria cena de jogo.
     * 
     * @param s	Cena de seleção de embarcações para obtenção de informações. 
     * @return Cena criada.
     */
    private GameScene createGameScene(SelectShipsScene s)
    { 	
    	GameScene g = new GameScene(this
			, s.getPlayerBoard()
			, s.getPlayerShips()
			, s.getCpuBoard()
			, s.getCpuShips()
			, "Some player");  
    	g.addListener(this);
    	
    	return g;
    }
    
    /**
     * Cria cena de término de jogo.
     * 
     * @return Cena criada.
     */
    private GameOverScene createGameOverScene(boolean isPlayerWinner)
    {
    	GameOverScene gos = new GameOverScene(this, isPlayerWinner); 
    	gos.addListener(this);
    	
    	return gos;   
    }
    
    /**
     * Define configurações de renderização.
     * 
     * @param g Objeto Graphics.
     */
    private void setRenderingHints(Graphics g)
    {
    	RenderingHints rh =
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_TEXT_ANTIALIASING
	    		, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_RENDERING
	    		, RenderingHints.VALUE_RENDER_QUALITY);

	    ((Graphics2D)g).setRenderingHints(rh);
    }
}