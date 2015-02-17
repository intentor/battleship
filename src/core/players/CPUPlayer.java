package core.players;

import java.awt.Point;
import java.util.*;

import core.Axis;
import core.BoardPoint;
import core.PointInAxis;
import core.Ship;
import core.Util;
import core.events.*;

/**
 * Representa o computador como jogador.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public final class CPUPlayer implements IPlayer {

    /**
     * Tabuleiro do oponente.
     */
    private BoardPoint oponentBoard[][];
    
    /**
     * Embarca��es do oponente.
     */
    private List<Ship> oponentShips;
    
    /**
     * Embarca��es do jogador.
     */
    private List<Ship> myShips;

    /**
     * N�mero de embarca��es destru�das.
     */
    private int numDestroyed;

    /**
     * N�mero de tiros disparados.
     */
    private int numShots;

    /**
     * Quantidade de embarca��es a serem destru�das.
     */
    private int quantity;

    /**
     * Tamanho do eixo X do tabuleiro.
     */
    private int sizeX;

    /**
     *  Tamanho do eixo Y do tabuleiro.
     */
    private int sizeY;

    /**
     * Pilha representando os �ltimos ataques a embarca��es realizados.
     */
    private Stack<PointInAxis> lastAttacks = new Stack<PointInAxis>();

    /**
     * Lista de listeners de eventos.
     */
    private List<PlayerListener> listeners = new LinkedList<PlayerListener>();

    /**
     * Instancia a classe, definindo o n�mero de barcos
     * destru�dos e tiros disparados como 0.
     *
     * @param oponentBoard  Tabuleiro do oponente.
     * @param quantity      Quantidade de embarca��es a serem destru�das.
     */
    public CPUPlayer(List<Ship> myShips, BoardPoint oponentBoard[][], List<Ship> oponentShips) {
    	this.myShips = myShips;
        this.oponentBoard = oponentBoard;
        this.oponentShips = oponentShips;
        this.numDestroyed = 0;
        this.numShots = 0;
        this.quantity = this.oponentShips.size();

        this.sizeX = oponentBoard[0].length;
        this.sizeY = oponentBoard.length;
    }

    public void doTurn() {
        Point p; //Ponto escolhido.

        /* Primeiramente, verifica se h� uma �ltima posi��o a ser considerada.
         * Havendo uma �ltima posi��o, realiza an�lises a partir dela.
         */
        if (!this.lastAttacks.empty()) {
            //Obt�m os pontos ao redor do ponto atual.
            List<Point> points = null;

            /* Indica se, ap�s a an�lise de um eixo, o outro precisa
             * ser analisado por conta de a an�lise do primeiro n�o ter
             * obtido pontos pass�veis de ataque. */
            boolean hasCheckedAxis = false;

            int i = (this.lastAttacks.size() - 1);

            do {
                PointInAxis aux = this.lastAttacks.get(i);
                points = this.getSurroundedPoints(aux);
                
                /* Caso n�o tenha havido troca anterior de eixo para
                 * o ponto atual e se a quantidade de pontos obtidos
                 * for 0, havendo um eixo anteriormente analisado,
                 * troca o eixo do ataque. */
                if (!hasCheckedAxis && points.size() == 0 && aux.axis != null)
                {
                    hasCheckedAxis = true;
                    if (aux.axis == Axis.X) aux.axis = Axis.Y;
                    else aux.axis = Axis.X;
                }
                else {
                    //Reseta a troca de eixos e volta uma posi��o na pilha.
                    hasCheckedAxis = false;
                    i--;
                }
            } while (i >= 0 && points.size() == 0);

            //Caso n�o haja pontos ao redor, gera um ponto aleat�rio.
            if (points.size() == 0) {
                p = this.getRandomPoint();
                
            } else {
                //Seleciona aleatoriamente um dos pontos obtidos.
                int chosenPoint = Util.GetRandomNumber(points.size());
                p = points.get(chosenPoint);
            }
        } else {
            p = this.getRandomPoint();
        }

        this.numShots++;
        this.setAttackPoint(p);

        //Analisa o ponto.
        if (this.oponentBoard[p.y][p.x].isShip()) {
            /* Verifica se a embarca��o foi completamente destru�da.
             * Se lastPosition != null, significa que mais de um
             * ataque a uma embarca��o j� se deu.
             * ([Todas as embarca��es tem mais de uma posi��o).
             */
            boolean destroyed = (this.oponentBoard[p.y][p.x].isDestroyed());

            if (destroyed)
            {
                this.fireShipDestroyedEvent(
                    new ShipDestroyedEvent(this, this.oponentBoard[p.y][p.x].getShip()));

                //Verifica se o jogo acabou.
                if (++this.numDestroyed == this.quantity) return;

                //Remove pontos j� destru�dos da pilha de �ltimos ataques.
                for (int i = 0; i < this.lastAttacks.size(); i++)
                {
                    PointInAxis aux = this.lastAttacks.get(i);
                    if (this.oponentBoard[aux.y][aux.x].isDestroyed())
                        this.lastAttacks.remove(i--);
                }
            }
            else
            {
            	this.fireBoardPointHitEvent(new BoardPointHitEvent(this, true));
            	
                //Empilha o ataque realizado.
                PointInAxis attackedShip = new PointInAxis(p);
                this.lastAttacks.push(attackedShip);
            }
        }
        else
        	this.fireBoardPointHitEvent(new BoardPointHitEvent(this, false));        

    	this.fireBoardPointHitEvent(new BoardPointHitEvent(this, true));
    }
    
    public void setAttackPoint(Point p)
    {
        this.oponentBoard[p.y][p.x].setAttacked();
    }

    public int getNumDestroyed()
    {
        return this.numDestroyed;
    }
    
    public List<Ship> getShips()
    {
    	return this.myShips;
    }

    public int getNumShots()
    {
        return this.numShots;
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

    /**
     * Obt�m pontos dispon�veis para ataque ao redor de um
     * determinado ponto atacado que contenha uma embarca��o.
     *
     * @param p Ponto que cont�m uma embarca��o.
     * @return  Lista de pontos pass�veis de ataque.
     * @see     Core.Point
     */
    private List<Point> getSurroundedPoints(PointInAxis p) {
        /* Primeiramente, verifica se o ponto atual j� foi destru�do.
         * Sendo destru�do, retorna uma lista vazia. */
        if (this.oponentBoard[p.y][p.x].isDestroyed()) return new LinkedList<Point>();

        boolean analyseXAxis = (p.axis == null || p.axis == Axis.X)
            , analyseYAxis = (p.axis == null || p.axis == Axis.Y);
        List<Point> points = new LinkedList<Point>();

        /* Primeiramente verifica se j� h� algum navio atingido em algum eixo
         * para efeito de anula��o da an�lise do outro eixo.
         */

        //Eixo X.
        if (analyseXAxis &&
            ( (p.x - 1 >= 0 && this.oponentBoard[p.y][p.x - 1].isShipAttackedNotDestroyed()) ||
              (p.x + 1 < this.sizeX && this.oponentBoard[p.y][p.x + 1].isShipAttackedNotDestroyed()) ))
        {
            analyseYAxis = false;
            p.axis = Axis.X;
        }

        //Eixo Y.
        if (analyseYAxis &&
            ( (p.y - 1 >= 0 && this.oponentBoard[p.y - 1][p.x].isShipAttackedNotDestroyed()) ||
              (p.y + 1 < this.sizeY && this.oponentBoard[p.y + 1][p.x].isShipAttackedNotDestroyed()) ))
        {
            analyseXAxis = false;
            p.axis = Axis.Y;
        }

        //Analisa o eixo X.
        if (analyseXAxis)
        {
            //Analisa pontos � esquerda.
            int x = p.x - 1;
            while (x >= 0 && this.oponentBoard[p.y][x].isShipAttackedNotDestroyed()) {
                x -= 1;
            }
            if (x >= 0 && !this.oponentBoard[p.y][x].isAttacked()) {
                points.add(new Point(x, p.y));
            }

            //Analisa pontos � direita.
            x = p.x + 1;
            while (x < this.sizeX && this.oponentBoard[p.y][x].isShipAttackedNotDestroyed()) {
                x += 1;
            }
            if (x < this.sizeX && !this.oponentBoard[p.y][x].isAttacked()) {
                points.add(new Point(x, p.y));
            }
        }

        //Analisa o eixo Y.
        if (analyseYAxis)
        {
            //Analisa pontos acima.
            int y = p.y - 1;
            while (y >= 0 && this.oponentBoard[y][p.x].isShipAttackedNotDestroyed()) {
                y -= 1;
            }
            if (y >= 0 && !this.oponentBoard[y][p.x].isAttacked()) {
                points.add(new Point(p.x, y));
            }

            //Analisa pontos abaixo.
            y = p.y + 1;
            while (y < this.sizeY && this.oponentBoard[y][p.x].isShipAttackedNotDestroyed()) {
                y += 1;
            }
            if (y < this.sizeY && !this.oponentBoard[y][p.x].isAttacked()) {
                points.add(new Point(p.x, y));
            }
        }

        return points;
    }

    /**
     * Obt�m um ponto aleat�rio ainda n�o atacado.
     *
     * @return Objeto Point representando o ponto.
     * @see Core.Point
     */
    private Point getRandomPoint() {
        Point p;

        /* Realiza gera��o de ponto enquanto este n�o tiver sido atacado.
         * Um ponto � gerado enquanto este for atacado e ao redor do ponto
         * n�o houver pelo menos um dos eixos com a quantidade de casas
         * n�o atacadas igual ao tamanho da menor embarca��o ainda n�o 
         * destru�da (contando com o ponto atual).
         */ 
        do {
            p = Util.GenerateRandomPosition(this.sizeX, this.sizeY);
        } while (this.oponentBoard[p.y][p.x].isAttacked() ||
        		!this.checkSurroundedSize(p, this.getSizeOfSmallShip()));

        return p;
    }
    
    /**
     * Verifica se ao redor de um ponto h� pelo menos a quantidade
     * size -1 de espa�os dispon�veis.
     * 
     * @param p		Ponto a ser analisado.
     * @param size	Tamanho dispon�vel necess�rio.
     * @return		Boolean indicando se a h� a quantidade dispon�vel.
     */
    //TODO: transformar em PRIVATE ap�s t�rmino de DEBUG.
    //TODO: melhorar procedimento de an�lise dos pontos.
    public boolean checkSurroundedSize(Point p, int size)
    {
    	boolean hasSize = true;    	
    	
    	//Verifica pontos � esquerda.
    	int x = p.x - 1;
    	if (x < 0) hasSize = false;
    	else {
	    	for (int i = 0; x >= 0 && i < (size - 1); i++, x -= 1) {
	    		if (this.oponentBoard[p.y][x].isAttacked())
	    		{
	    			hasSize = false;
	    			break;
	    		}
	        }
    	}
    
    	if (hasSize) return true;
    	hasSize = true;
    
        //Analisa pontos � direita.
        x = p.x + 1; 
    	if (x >= this.sizeX) hasSize = false;
    	else {
			for (int i = 0; x < this.sizeX && i < (size - 1); i++, x += 1) {
	    		if (this.oponentBoard[p.y][x].isAttacked())
	    		{
	    			hasSize = false;
	    			break;
	    		}
	        }
    	}
    
    	if (hasSize) return true;
    	hasSize = true;  
        
        //Analisa pontos acima.
        int y = p.y - 1;
    	if (y < 0) hasSize = false;
    	else {
	    	for (int i = 0; y >= 0 && i < (size - 1); i++, y -= 1) {
	    		if (this.oponentBoard[y][p.x].isAttacked())
	    		{
	    			hasSize = false;
	    			break;
	    		}
	        }
    	}
    	
    	if (hasSize) return true;
    	hasSize = true;
    	
        //Analisa pontos abaixo.
        y = p.y + 1;
    	if (y >= this.sizeY) hasSize = false;
    	else {
	    	for (int i = 0; y < this.sizeY && i < (size - 1); i++, y += 1) {
	    		if (this.oponentBoard[y][p.x].isAttacked())
	    		{
	    			hasSize = false;
	    			break;
	    		}
	        }
    	}
    	
    	return hasSize;
    }
    
    /**
     * Obt�m o tamanho da menor embarca��o ainda
     * n�o destru�da.
     * 
     * @return Tamanho da embarca��o.
     */
    private int getSizeOfSmallShip()
    {
    	int size = 5; //Inicia com o tamanho da maior embarca��o.
    	
    	for (Ship ship : this.oponentShips)
    		if (!ship.isDestroyed() && ship.size() < size) size = ship.size();
    	
    	return size;
    }
}