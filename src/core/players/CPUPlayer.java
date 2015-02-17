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
 * @author André Martins (amartins@ymail.com)
 */
public final class CPUPlayer implements IPlayer {

    /**
     * Tabuleiro do oponente.
     */
    private BoardPoint oponentBoard[][];
    
    /**
     * Embarcações do oponente.
     */
    private List<Ship> oponentShips;
    
    /**
     * Embarcações do jogador.
     */
    private List<Ship> myShips;

    /**
     * Número de embarcações destruídas.
     */
    private int numDestroyed;

    /**
     * Número de tiros disparados.
     */
    private int numShots;

    /**
     * Quantidade de embarcações a serem destruídas.
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
     * Pilha representando os últimos ataques a embarcações realizados.
     */
    private Stack<PointInAxis> lastAttacks = new Stack<PointInAxis>();

    /**
     * Lista de listeners de eventos.
     */
    private List<PlayerListener> listeners = new LinkedList<PlayerListener>();

    /**
     * Instancia a classe, definindo o número de barcos
     * destruídos e tiros disparados como 0.
     *
     * @param oponentBoard  Tabuleiro do oponente.
     * @param quantity      Quantidade de embarcações a serem destruídas.
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

        /* Primeiramente, verifica se há uma última posição a ser considerada.
         * Havendo uma última posição, realiza análises a partir dela.
         */
        if (!this.lastAttacks.empty()) {
            //Obtém os pontos ao redor do ponto atual.
            List<Point> points = null;

            /* Indica se, após a análise de um eixo, o outro precisa
             * ser analisado por conta de a análise do primeiro não ter
             * obtido pontos passíveis de ataque. */
            boolean hasCheckedAxis = false;

            int i = (this.lastAttacks.size() - 1);

            do {
                PointInAxis aux = this.lastAttacks.get(i);
                points = this.getSurroundedPoints(aux);
                
                /* Caso não tenha havido troca anterior de eixo para
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
                    //Reseta a troca de eixos e volta uma posição na pilha.
                    hasCheckedAxis = false;
                    i--;
                }
            } while (i >= 0 && points.size() == 0);

            //Caso não haja pontos ao redor, gera um ponto aleatório.
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
            /* Verifica se a embarcação foi completamente destruída.
             * Se lastPosition != null, significa que mais de um
             * ataque a uma embarcação já se deu.
             * ([Todas as embarcações tem mais de uma posição).
             */
            boolean destroyed = (this.oponentBoard[p.y][p.x].isDestroyed());

            if (destroyed)
            {
                this.fireShipDestroyedEvent(
                    new ShipDestroyedEvent(this, this.oponentBoard[p.y][p.x].getShip()));

                //Verifica se o jogo acabou.
                if (++this.numDestroyed == this.quantity) return;

                //Remove pontos já destruídos da pilha de últimos ataques.
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
     * Obtém pontos disponíveis para ataque ao redor de um
     * determinado ponto atacado que contenha uma embarcação.
     *
     * @param p Ponto que contém uma embarcação.
     * @return  Lista de pontos passíveis de ataque.
     * @see     Core.Point
     */
    private List<Point> getSurroundedPoints(PointInAxis p) {
        /* Primeiramente, verifica se o ponto atual já foi destruído.
         * Sendo destruído, retorna uma lista vazia. */
        if (this.oponentBoard[p.y][p.x].isDestroyed()) return new LinkedList<Point>();

        boolean analyseXAxis = (p.axis == null || p.axis == Axis.X)
            , analyseYAxis = (p.axis == null || p.axis == Axis.Y);
        List<Point> points = new LinkedList<Point>();

        /* Primeiramente verifica se já há algum navio atingido em algum eixo
         * para efeito de anulação da análise do outro eixo.
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
            //Analisa pontos à esquerda.
            int x = p.x - 1;
            while (x >= 0 && this.oponentBoard[p.y][x].isShipAttackedNotDestroyed()) {
                x -= 1;
            }
            if (x >= 0 && !this.oponentBoard[p.y][x].isAttacked()) {
                points.add(new Point(x, p.y));
            }

            //Analisa pontos à direita.
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
     * Obtém um ponto aleatório ainda não atacado.
     *
     * @return Objeto Point representando o ponto.
     * @see Core.Point
     */
    private Point getRandomPoint() {
        Point p;

        /* Realiza geração de ponto enquanto este não tiver sido atacado.
         * Um ponto é gerado enquanto este for atacado e ao redor do ponto
         * não houver pelo menos um dos eixos com a quantidade de casas
         * não atacadas igual ao tamanho da menor embarcação ainda não 
         * destruída (contando com o ponto atual).
         */ 
        do {
            p = Util.GenerateRandomPosition(this.sizeX, this.sizeY);
        } while (this.oponentBoard[p.y][p.x].isAttacked() ||
        		!this.checkSurroundedSize(p, this.getSizeOfSmallShip()));

        return p;
    }
    
    /**
     * Verifica se ao redor de um ponto há pelo menos a quantidade
     * size -1 de espaços disponíveis.
     * 
     * @param p		Ponto a ser analisado.
     * @param size	Tamanho disponível necessário.
     * @return		Boolean indicando se a há a quantidade disponível.
     */
    //TODO: transformar em PRIVATE após término de DEBUG.
    //TODO: melhorar procedimento de análise dos pontos.
    public boolean checkSurroundedSize(Point p, int size)
    {
    	boolean hasSize = true;    	
    	
    	//Verifica pontos à esquerda.
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
    
        //Analisa pontos à direita.
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
     * Obtém o tamanho da menor embarcação ainda
     * não destruída.
     * 
     * @return Tamanho da embarcação.
     */
    private int getSizeOfSmallShip()
    {
    	int size = 5; //Inicia com o tamanho da maior embarcação.
    	
    	for (Ship ship : this.oponentShips)
    		if (!ship.isDestroyed() && ship.size() < size) size = ship.size();
    	
    	return size;
    }
}