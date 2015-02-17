package game.scenes;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import core.BoardPoint;
import core.Ship;
import core.events.BoardPointHitEvent;
import core.events.PlayerListener;
import core.events.ShipDestroyedEvent;
import core.players.CPUPlayer;
import core.players.HumanPlayer;
import core.players.IPlayer;

import game.GameConstants;
import game.scenes.drawers.GameDrawer;
import game.scenes.events.*;
import game.scenes.listeners.GameSceneListener;

/**
 * Cena de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class GameScene extends BaseScene<GameDrawer, GameSceneListener> implements PlayerListener {

    private final Point playerInfoPosition = new Point(20, 20);
    private final Point cpuInfoPosition = new Point(360, 20);
    private final Point playerPosition = new Point(40, 100);
    private final Point cpuPosition = new Point(380, 100);
    
	private BoardPoint[][] playerBoard;
    private BoardPoint[][] cpuBoard;
    private IPlayer cpu;
	private IPlayer player;
	private String playerName;
    private boolean playerTurn;
    private Point pointToHighlight;  
    
    private BoardMouseListener mouseListener; 
    private BoardMouseMoveListener mouseMotionListener; 
    
	public GameScene(JPanel panel
		, BoardPoint[][] playerBoard
		, List<Ship> playerShips
	    , BoardPoint[][] cpuBoard
	    , List<Ship> cpuShips
		, String playerName)
	{   
		super(panel);
		
		this.panel = panel;
		this.playerBoard = playerBoard;
        this.cpuBoard = cpuBoard;
		this.playerName = playerName;
		this.pointToHighlight = null;
		this.drawer = new GameDrawer(panel);
		
        this.cpu = new CPUPlayer(cpuShips, this.playerBoard, playerShips);
        this.player = new HumanPlayer(playerShips, this.cpuBoard, cpuShips);
        this.playerTurn = true;
        
        this.mouseListener = new BoardMouseListener(); 
        this.mouseMotionListener = new BoardMouseMoveListener();
		this.panel.addMouseListener(this.mouseListener);
		this.panel.addMouseMotionListener(this.mouseMotionListener);
		
	    this.cpu.addShipDestroyedListener(this);
	    this.player.addShipDestroyedListener(this);
    
	    this.setBackgroundMusic(GameConstants.AudioGameScene);
	}
    
    @Override
    public void shipDestroyedReceived(ShipDestroyedEvent e)
    {
        this.play(GameConstants.AudioDestroyed);
    }
    
    @Override
    public void boardPointHitReceived(BoardPointHitEvent e)
    {
    	if (e.isShip()) this.play(GameConstants.AudioHit);
    	else this.play(GameConstants.AudioWater);
    }
    
    /**
     * Realiza resolução de valor de um ponto, assegurando
     * que este esteja nos limites do tabuleiro.
     * 
     * @param x Posição no eixo X.
     * @param y Posição no eixo Y.
     * @return Ponto normalizado.
     */
    private Point resolvePoint(int x, int y)
    {
    	//Obtém os pontos X e Y do usuário.
		Point p = new Point(
				  ((x - this.cpuPosition.x) / GameConstants.BoardPointSize)
				, ((y - this.cpuPosition.y) / GameConstants.BoardPointSize));
		
		//Normalização dos valores do ponto.
		if (p.x < 0) p.x = 0;
		if (p.x > (GameConstants.BoardX - 1)) p.x = (GameConstants.BoardX - 1);
		if (p.y < 0) p.y = 0;
		if (p.y > (GameConstants.BoardY - 1)) p.y = (GameConstants.BoardY - 1);
		
		return p;
    }
    
    @Override
    public void Draw(Graphics2D g)
    {
		this.panel.setBackground(Color.WHITE);
		
    	this.drawer.drawPlayerData(g
        	, this.playerName
        	, this.player.getNumShots()
        	, this.cpu.getNumDestroyed()
        	, this.playerInfoPosition
        	, this.playerTurn);
        
        this.drawer.drawPlayerData(g
        	, "CPU"
        	, this.cpu.getNumShots()
        	, this.player.getNumDestroyed()
        	, this.cpuInfoPosition
        	, !this.playerTurn);
        
        this.drawer.drawBoard(g, this.playerBoard, this.playerPosition, this.player.getShips(), false, null);
        this.drawer.drawBoard(g, this.cpuBoard, this.cpuPosition, this.cpu.getShips(), true, this.pointToHighlight);
    } 
	
	/**
	 * Dispara o evento de término do jogo.
	 * @param e	Objeto representando o evento disparado. 
	 */
    private void fireGameOverEvent(GameOverEvent e)
    {
        for (GameSceneListener l : this.listeners) l.gameOverReceived(e);
    }
    
    @Override
    public void Dispose()
    {
    	super.Dispose();
    	
		this.panel.removeMouseListener(this.mouseListener);
		this.panel.removeMouseMotionListener(this.mouseMotionListener);
    	this.cpu.removeShipDestroyedListener(this);
    	this.player.removeShipDestroyedListener(this);
    }

    /**
     * Adaptador para captura de cliques do mouse.
     */
    class BoardMouseListener extends MouseAdapter 
    {
    	@Override
        public void mousePressed(MouseEvent e) {

        	int x = e.getX()
        		, y = e.getY();
        	
        	if (playerTurn && 
        		(x >= 360 && x <= 660) &&
        		(y >= 80 && y <= 380))
        	{
        		playerTurn = false;
        		
        		Point p = resolvePoint(x, y);
        		
        		//Verifica se o ponto já foi atacado.
        		if (cpuBoard[p.y][p.x].isAttacked()) {
        			playerTurn = true;
        			return;
        		}
        		
        		player.setAttackPoint(p);
        		player.doTurn();
                panel.repaint();   
                
                if (player.getNumDestroyed() == GameConstants.ShipQuantity)
                	fireGameOverEvent(new GameOverEvent(this, true));
                else
                {
	        		cpu.doTurn();
	        		panel.repaint();
	                
	                if (cpu.getNumDestroyed() == GameConstants.ShipQuantity)
	                	fireGameOverEvent(new GameOverEvent(this, false));
	                else 
	                	playerTurn = true;
                }
        	}
        }
    }
    
    /**
     * Adaptador para captura de movimentos do mouse.
     */
    class BoardMouseMoveListener implements MouseMotionListener
    {
    	@Override
    	public void mouseMoved(MouseEvent e)
    	{
    		int x = e.getX()
    			, y = e.getY();
    		
    		if ((x >= cpuPosition.x && x <= cpuPosition.x + (GameConstants.BoardPointSize * GameConstants.BoardX)) &&
		        (y >= cpuPosition.y && y <= cpuPosition.y + (GameConstants.BoardPointSize * GameConstants.BoardY)))
    		{
        		pointToHighlight = resolvePoint(x, y);
    			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    		}
    		else {
        		pointToHighlight = null;
        		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    		}
    		
    		panel.repaint();
    	}

		@Override
		public void mouseDragged(MouseEvent e) {		
		}
    }
}
