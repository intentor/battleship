package core;

import game.GameConstants;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Representa uma embarcação.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class Ship {
    
    private Point startPoint;
    private Point movePoint;
    private Axis shipAxis;
    private ShipType shipType;
    private int hitPoints;
    private boolean deployed;

	/**
     * Cria uma nova embarcação.
     *
     * @param startPoint    Ponto inicial da embarcação.
     */
	public Ship(ShipType shipType) {
        this.startPoint = null;
        this.movePoint = null;
        this.shipAxis = null;
        this.shipType = shipType;
        this.hitPoints = 0;
        this.deployed = false;
    } 

	/**
     * Cria uma nova embarcação.
     *
     * @param startPoint    Ponto inicial da embarcação.
     * @param shipAxis      Eixo da embarcação.
     * @param shipType      Tipo da embarcação.
     */
    public Ship(Point startPoint, Axis shipAxis, ShipType shipType) {
        this.startPoint = startPoint;
        this.shipAxis = shipAxis;
        this.shipType = shipType;
        this.hitPoints = 0;
    }

    /**
     * @return the startPoint
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * @param startPoint the startPoint to set
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
    
    /**
     * @return the movePoint
     */
    public Point getMovePoint() {
		return movePoint;
	}

    /**
     * @param movePoint the movePoint to set
     */
	public void setMovePoint(Point movePoint) {
		this.movePoint = movePoint;
	}

    /**
     * @return the shipAxis
     */
    public Axis getShipAxis() {
        return shipAxis;
    }

    /**
     * @param shipAxis the shipAxis to set
     */
    public void setShipAxis(Axis shipAxis) {
        this.shipAxis = shipAxis;
    }

    /**
     * @return the shipType
     */
    public ShipType getShipType() {
        return shipType;
    }

    /**
     * @param shipType the shipType to set
     */
    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    /**
     * @return Tamanho da embarcação.
     */
    public int size() {
        return this.shipType.size();
    }

    /**
     * @return the hitPoints
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Indica que a embarcação foi atacada.
     */
    public void attack() {
        this.hitPoints++;
    }

    /**
     * Indica se a embarcação foi destruída.
     */
    public boolean isDestroyed()
    {
        return (this.hitPoints == this.size());
    }
    
    /**
     * Indica se a embarcação já foi colocada no tabuleiro.
     */
    public boolean isDeployed() {
		return deployed;
	}
    
    /**
     * Indica se a embarcação já foi colocada no tabuleiro.
     */
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}
	
	/**
	 * Obtém retângulo representativo da embarcação utilizando
	 * seu ponto inicial para análise de posicionamento.
	 * 
	 * @return Retângulo representando a embarcação.
	 */
	public Rectangle getBounds()
	{
		return this.getBounds(false);
	}
	
	/**
	 * Obtém retângulo representativo da embarcação.
	 * 
	 * @param useMovePoint Indica se se deve utilizar o ponto de movimentação como início do retângulo.
	 * @return Retângulo representando a embarcação.
	 */
	public Rectangle getBounds(boolean useMovePoint)
	{
		Point p = (useMovePoint ? this.movePoint : this.startPoint);
		Rectangle r;
		
		//Cria o retângulo de acordo com o eixo.
		if (this.shipAxis == Axis.X)	
			r = new Rectangle(p.x, p.y, GameConstants.BoardPointSize, this.getShipType().size() * GameConstants.BoardPointSize);
		else
			//x, y, width, height
			r = new Rectangle(p.x, p.y, this.getShipType().size() * GameConstants.BoardPointSize, GameConstants.BoardPointSize);		
		
		return r; 	
	}
}
