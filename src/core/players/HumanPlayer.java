package core.players;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import core.BoardPoint;
import core.Ship;
import core.events.*;

/**
 * Representa um humano como jogador.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public final class HumanPlayer implements IPlayer {
	
	private Point attackPoint;
    private List<Ship> myShips;
    private BoardPoint oponentBoard[][];
    private int numDestroyed;
    private int numShots;
    private List<PlayerListener> listeners = new LinkedList<PlayerListener>();
	
	public HumanPlayer(List<Ship> myShips, BoardPoint oponentBoard[][], List<Ship> oponentShips) 
	{
		this.myShips = myShips;
		this.oponentBoard = oponentBoard;
		this.numShots = 0;
		this.numDestroyed = 0;
    }
	
    public void doTurn()
    {
    	this.numShots++;
    	BoardPoint p = this.oponentBoard[this.attackPoint.y][this.attackPoint.x];
    	p.setAttacked();
    	
    	if (p.isDestroyed())
    	{
    		this.numDestroyed++;
    		this.fireShipDestroyedEvent(new ShipDestroyedEvent(this, p.getShip()));
    	}
    	else
    	{
    		this.fireBoardPointHitEvent(new BoardPointHitEvent(this, p.isShip()));
    	}
    }

    public int getNumDestroyed()
    {
    	return this.numDestroyed;
    }

    public int getNumShots()
    {
    	return this.numShots;
    }
    
    public List<Ship> getShips()
    {
    	return this.myShips;
    }
    
    public void addShipDestroyedListener(PlayerListener l)
    {
        this.listeners.add(l);
    }

    public void removeShipDestroyedListener(PlayerListener l)
    {
        this.listeners.remove(l);
    }

    private void fireShipDestroyedEvent(ShipDestroyedEvent e)
    {
        for (PlayerListener l : this.listeners) l.shipDestroyedReceived(e);
    }
    
    private void fireBoardPointHitEvent(BoardPointHitEvent e)
    {
        for (PlayerListener l : this.listeners) l.boardPointHitReceived(e);
    }
    
    public void setAttackPoint(Point p)
    {
    	this.attackPoint = p;
    }
}
