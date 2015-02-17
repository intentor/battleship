package core;

import java.awt.Point;
import java.util.Random;
import java.util.Calendar;

/**
 * M�todos de apoio.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public class Util {

    static Random rnd;

    /**
     * Gera um ponto cartesiano aleat�rio.
     *
     * @param maxX  N�mero m�ximo de colunas.
     * @param maxY  N�mero m�ximo de linhas.
     * @return      Objeto <code>Point</code> gerado.
     * @see         Core.Point
     */
    public static Point GenerateRandomPosition(int maxX, int maxY)
    {
        Random r = GetRandomObject();

        int x, y;

        x = r.nextInt(maxX);
        y = r.nextInt(maxY);

        return new Point(x, y);
    }

    /**
     * Gera um valor inteiro aleat�rio.
     *
     * @param maximum   Valor m�ximo a ser gerado.
     * @return          Valor inteiro gerado.
     */
    public static int GetRandomNumber(int maximum)
    {
        return GetRandomObject().nextInt(maximum);
    }

    /**
     * Obt�m o objeto <code>Random</code>.
     * 
     * @return Objeto <code>Random</code>.
     * @see Random
     */
    private static Random GetRandomObject()
    {
        if (rnd == null)
        {
            Calendar cal = Calendar.getInstance();
            int seed = cal.get(Calendar.SECOND) + cal.get(Calendar.MINUTE) + cal.get(Calendar.HOUR);
            rnd = new Random(seed);
        }

        return rnd;
    }
}
