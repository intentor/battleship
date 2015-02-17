package game.scenes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import core.*;

import game.GameConstants;
import game.scenes.drawers.SelectShipsDrawer;
import game.scenes.events.*;
import game.scenes.listeners.SelectShipsSceneListener;

/**
 * Cena para seleção de embarcações.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class SelectShipsScene extends BaseScene<SelectShipsDrawer, SelectShipsSceneListener> {

    private final Point panelPoint = new Point(370, 70);
    
    /**
     * Indica que o painel já foi desenhado.
     * É utilizado para assegurar que os eventos do mouse 
     * somente sejam executados após o desenho do painel. 
     */
    private boolean panelDrawed;
    
	private List<Ship> playerShips;
	private List<Ship> cpuShips;
	private BoardPoint[][] playerBoard;
    private BoardPoint[][] cpuBoard;
    private Point pointToHighlight;
    
    private BoardMouseListener mouseListener; 
    private BoardMouseMoveListener mouseMotionListener;   
    
    /**
     * Embarcação selecionada para posicionamento no tabuleiro.
     */
    private Ship shipSelected;
	
	public SelectShipsScene(JPanel panel)
	{   
		super(panel);
		
		this.panelDrawed = false;
		
		this.playerShips = BoardFactory.getShips(GameConstants.ShipQuantity);
    	this.cpuShips = BoardFactory.getShips(GameConstants.ShipQuantity);
    	
    	this.playerBoard = BoardFactory.CreateEmptyBoard(GameConstants.BoardX
    		, GameConstants.BoardY);
    	this.cpuBoard = BoardFactory.GenerateRandomBoard(GameConstants.BoardX
    		, GameConstants.BoardY
    		, this.cpuShips);
    	
    	this.pointToHighlight = null;
    	
		this.drawer = new SelectShipsDrawer(panel);
        
        this.mouseListener = new BoardMouseListener(); 
        this.mouseMotionListener = new BoardMouseMoveListener();
		this.panel.addMouseListener(this.mouseListener);
		this.panel.addMouseMotionListener(this.mouseMotionListener);
		
		this.setBackgroundMusic(GameConstants.AudioSelectShipsScene);
	}
	
	@Override
	public void Draw(Graphics2D g) {		
		Point p = new Point(15, 30);
		this.panel.setBackground(Color.WHITE);
		this.drawer.drawInfo(g, p);
		this.drawer.drawBoard(g
			, this.playerBoard
			, this.panelPoint
			, this.playerShips
			, this.pointToHighlight);
		this.drawer.drawShipsInSelection(g, p, this.playerShips);
		
		//Verifica se todas as embarcações já foram colocadas no tabuleiro.
		boolean hasDeployed = true;
		for (Ship s : this.playerShips)
		{
			if (!s.isDeployed())
			{
				hasDeployed = false;
				break;
			}
		}
		
		if (hasDeployed) this.fireShipsDeployedEvent(new ShipsDeployedEvent(this));
		
		if (!this.panelDrawed) this.panelDrawed = true;
	}

    @Override
    public void Dispose()
    {
    	super.Dispose();
    	
		this.panel.removeMouseListener(this.mouseListener);
		this.panel.removeMouseMotionListener(this.mouseMotionListener);
    }
    
	public List<Ship> getPlayerShips() {
		return playerShips;
	}

	public List<Ship> getCpuShips() {
		return cpuShips;
	}
	
    public BoardPoint[][] getPlayerBoard() {
		return playerBoard;
	}

	public BoardPoint[][] getCpuBoard() {
		return cpuBoard;
	}  
	
	/**
	 * Dispara o evento de término da seleção das embarcações.
	 * @param e	Objeto representando o evento disparado. 
	 */
    private void fireShipsDeployedEvent(ShipsDeployedEvent e)
    {
        for (SelectShipsSceneListener l : this.listeners) l.shipsDeployedReceived(e);
    }
    
    /**
     * Realiza resolução de valor de um ponto, assegurando
     * que este esteja nos limites do tabuleiro.
     * 
     * @param Ponto a ser normalizado.
     * @return Ponto normalizado.
     */
    private Point resolvePoint(Point ptn)
    {
    	//Obtém os pontos X e Y do usuário.
		Point p = new Point(((ptn.x - this.panelPoint.x) / GameConstants.BoardPointSize)
			, ((ptn.y - this.panelPoint.y) / GameConstants.BoardPointSize));
		
		//Normalização dos valores do ponto.
		if (p.x < 0) p.x = 0;
		if (p.x > (GameConstants.BoardX - 1)) p.x = (GameConstants.BoardX - 1);
		if (p.y < 0) p.y = 0;
		if (p.y > (GameConstants.BoardY - 1)) p.y = (GameConstants.BoardY - 1);
		
		return p;
    }
    
	/**
     * Adaptador para captura de cliques do mouse.
     */
    class BoardMouseListener extends MouseAdapter 
    {
    	@Override
        public void mousePressed(MouseEvent e) 
    	{
    		if (!panelDrawed) return;
    		
    		if (shipSelected != null && e.getButton() == MouseEvent.BUTTON3)
    		{
    			Axis a = shipSelected.getShipAxis();    			
    			if (a == Axis.X) a = Axis.Y;
    			else a = Axis.X;
    			shipSelected.setShipAxis(a);
    		}
    		else
    		{
	    		if (shipSelected == null)
	    		{
		    		//Verifica se o pressionamento do mouse se deu sobre alguma das embarcações.
		    		for (Ship s : playerShips)
		    		{
		    			if (s.getBounds().contains(e.getPoint())) {
		    				shipSelected = s;
		    				break;
		    			}	    			
		    		}
	    		}
	    		else 
	    		{
	    			//Verifica se a embarcação está em um ponto do tabuleiro.
	        		Point p = e.getPoint();
	        		Rectangle board = new Rectangle(panelPoint.x
	        			, panelPoint.y
	        			, GameConstants.BoardX * GameConstants.BoardPointSize
	        			, GameConstants.BoardY * GameConstants.BoardPointSize);
	        		
	        		if (board.contains(p))
	        		{
	        			Point resolvedPoint = resolvePoint(p);
	        			
	        			if (BoardFactory.canFit(playerBoard
	        				, resolvedPoint
	        				, shipSelected.getShipAxis()
	        				, shipSelected.size())) {
		        			shipSelected.setStartPoint(resolvedPoint);
	        				BoardFactory.fitShip(playerBoard, shipSelected);
		        			shipSelected.setDeployed(true);

			    			shipSelected.setMovePoint(null);
			    			shipSelected = null;
	        			}
	        		}	    			
	    		}
    		}
    		
			panel.repaint();
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
    		if (!panelDrawed) return;
    		
    		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    		pointToHighlight = null;
    		
    		//Verifica se o movimento do mouse se deu sobre alguma das embarcações.
    		for (Ship s : playerShips)
    		{
    			if (s.getBounds().contains(e.getPoint())) 
    			{
        			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        			break;
    			}
    		}
			
			//Verifica se há alguma embarcação em movimento.
			if (shipSelected != null)
			{
				Point p = e.getPoint();
				
				if ((p.x >= panelPoint.x && p.x <= panelPoint.x + (GameConstants.BoardPointSize * GameConstants.BoardX)) &&
		            (p.y >= panelPoint.y && p.y <= panelPoint.y + (GameConstants.BoardPointSize * GameConstants.BoardY)))
				{
					pointToHighlight = resolvePoint(p);
				}
				
    			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    			
				shipSelected.setMovePoint(p);
				panel.repaint();
			}
    		
			panel.repaint();
    	}

		@Override
		public void mouseDragged(MouseEvent e) 
		{
		}
    }
}
