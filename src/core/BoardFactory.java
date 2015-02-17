package core;

import java.awt.Point;
import java.util.*;

/**
 * Factory para cria��o de tabuleiros.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public final class BoardFactory {

    /**
     * Cria um tabuleiro vazio.
     *
     * @param x n�mero de colunas do tabuleiro.
     * @param y n�mero de linhas do tabuleiro.
     * @return  Tabuleiro criado.
     */
    public static BoardPoint[][] CreateEmptyBoard(int x, int y)
    {
        BoardPoint boardPoint[][] = new BoardPoint[y][x];

        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                boardPoint[i][j] = new BoardPoint();
        
        return boardPoint;
    }

    /**
     * Cria um tabuleiro colocando as embarca��es em posi��es
     * aleat�rias.
     *
     * @param x 	N�mero de colunas do tabuleiro.
     * @param y 	N�mero de linhas do tabuleiro.
     * @param ships	Embarca��es a serem inseridas no tabuleiro.
     * @return  	Tabuleiro criado.
     */
    public static BoardPoint[][] GenerateRandomBoard(int x
        , int y
        , List<Ship> ships)
    {
        BoardPoint board[][] = CreateEmptyBoard(x, y);

        //Cria cada uma das embarca��es.
        for (Ship ship : ships)
        {
            Point p;
            Axis axis;

            do {
                //Obt�m uma posi��o aleat�ria.
                p = Util.GenerateRandomPosition(x, y);

                //Escolhe um eixo aleat�rio.
                axis = (Util.GetRandomNumber(2) == 1 ? Axis.X : Axis.Y);
            } while (!canFit(board, p, axis, ship.size()));

            ship.setStartPoint(p);
            ship.setShipAxis(axis);
            fitShip(board, ship);
        }

        return board;
    }

    /**
     * Verifica se uma embarca��o pode ser encaixada em um determinado ponto.
     * Estar no eixo X implica em variar o eixo Y.
     * Estar no eixo Y implica em variar o eixo X.
     *
     * @param board         Tabuleiro no qual se deseja encaixar a embarca��o.
     * @param startPoint    Ponto de in�cio da embarca��o.
     * @param axis          Eixo no qual a embarca��o estar�.
     * @param size          Tamanho da embarca��o.
     */
    public static boolean canFit(BoardPoint board[][]
        , Point startPoint
        , Axis axis
        , int size)
    {
        boolean canFit = true;
        int posToStart = (axis == Axis.X ? startPoint.y : startPoint.x);

        //Primeiramente, verifica se a embarca��o extravasa o limite do eixo.
        if ((axis == Axis.X && (posToStart + size) > board.length) ||
            (axis == Axis.Y && (posToStart + size) > board[0].length))
        {
            canFit = false;
        }
        else
        {
            for (int i = posToStart; i < (size + posToStart); i++)
            {
                int x = (axis == Axis.Y ? i : startPoint.x)
                    , y = (axis == Axis.X ? i : startPoint.y);

                if (board[y][x].isShip()) {
                    canFit = false;
                    break;
                }
            }
        }

        return canFit;
    }

    /**
     * Encaixa um barco no tabuleiro a partir de sua posi��o inicial
     * e de seu eixo.
     *
     * @param board         Tabuleiro no qual se deseja encaixar a embarca��o.
     * @param ship          Embarca��o a ser inserida no ponto.
     * @param axis          Eixo no qual a embarca��o estar�.
     */
    public static void fitShip(BoardPoint board[][]
        , Ship ship)
    {
        Point startPoint = ship.getStartPoint();
        Axis axis = ship.getShipAxis();
        int posToStart = (axis == Axis.X ? startPoint.y : startPoint.x);

        for (int i = posToStart; i < (ship.size() + posToStart); i++)
        {
            int x = (axis == Axis.Y ? i : startPoint.x)
                , y = (axis == Axis.X ? i : startPoint.y);

            board[y][x].setShip(ship);
        }
    }
    
    /**
     * Obt�m embarca��es de acordo com a quantidade definida.
     * 
     * @param quantity 	Quantidade de embarca��es.
     * @return			Lista contendo as embarca��es definidas.
     */
    public static List<Ship> getShips(int quantity)
    {    	
        List<Ship> ships = new LinkedList<Ship>();
        ships.add(new Ship(ShipType.PortaAvioes));
        ships.add(new Ship(ShipType.Encouracado));
        ships.add(new Ship(ShipType.Cruzador));
        ships.add(new Ship(ShipType.Cruzador));
        ships.add(new Ship(ShipType.Destroier));
        
        if (quantity == 8) {
	        ships.add(new Ship(ShipType.Destroier));
	        ships.add(new Ship(ShipType.Submarino));
	        ships.add(new Ship(ShipType.Submarino));
        }
        
        return ships;
    }
}
