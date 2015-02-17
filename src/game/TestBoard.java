package game;

import java.awt.Point;
import java.io.*;
import java.util.*;
import core.*;
import core.events.*;
import core.players.CPUPlayer;

/**
 * Tabuleiro de teste.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class TestBoard implements PlayerListener {
	
    public void Start()
    {
    	List<Ship> ships = BoardFactory.getShips(qtd);
        BoardPoint enemyBoard[][] = BoardFactory.GenerateRandomBoard(x, y, ships);

        System.out.println("TABULEIRO");
        printBoard(enemyBoard, true);

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(reader);

        CPUPlayer cpu = new CPUPlayer(ships, enemyBoard, ships);
        cpu.addShipDestroyedListener(this);

        try
        {
        	String val;
	        do {
	        	System.out.println("Menor embarcação: " + this.getSizeOfSmallShip(ships));
	        	
	        	val = in.readLine();	        	
	        	Point p = null;
	        	
	        	if (val.indexOf(",") > 0) {
		        	String[] point = val.split(","); 
		        	p = new Point(Integer.valueOf(point[0]), Integer.valueOf(point[1])); 
		        	System.out.println("Ponto: (" + p.x + "," + p.y + ")");
		        	System.out.println("Pode disparar? " + 
		        		cpu.checkSurroundedSize(p, this.getSizeOfSmallShip(ships)));
	        	}
	        	else
	        	{
		            cpu.doTurn();
		            System.out.println(cpu.getNumShots() + " disparos realizados.");
	        	}
	
	            printBoard(enemyBoard, false);
	            if (cpu.getNumDestroyed() == qtd) {
	                System.out.println("CPU venceu o jogo!");
	                break;
	            }
	        } while(!val.equals("-1"));
	
	        System.out.println("Game over.");
        }
        catch (IOException e)
        {
        	System.out.println("\nException caught: " + e.getMessage());
        }
    }

    private final int x = 9;
    private final int y = 9;
    private final int qtd = 5;

    @Override
    public void shipDestroyedReceived(ShipDestroyedEvent e)
    {
        System.out.println("[Embarcação " + e.getShip().getShipType() + " DESTRUÍDA]");
    }
    
    @Override
    public void boardPointHitReceived(BoardPointHitEvent e)
    {
    }
    
    private int getSizeOfSmallShip(List<Ship> ships)
    {
    	int size = 5; //Inicia com o tamanho da maior embarcação.
    	
    	for (Ship ship : ships)
    		if (!ship.isDestroyed() && ship.size() < size) size = ship.size();
    	
    	return size;
    }

    private void printBoard(BoardPoint[][] enemyBoard, boolean showShips)
    {
        for (int i = 0; i < y; i++)
        {
            for (int j = 0; j < x; j++)
            {
                String valToPrint = "-";

                if (showShips)
                {
                    if (enemyBoard[i][j].isShip()) valToPrint = String.valueOf(enemyBoard[i][j].getShip().size());
                }
                else
                {
                    if (enemyBoard[i][j].isAttacked())
                    {
                        if (enemyBoard[i][j].isShip()) valToPrint = "X";
                        else valToPrint = "*";
                    }
                }

                System.out.print(valToPrint + " ");
            }

            System.out.print("\n");
        }
    }
}
