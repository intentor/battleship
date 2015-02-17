package core;

import java.awt.Point;
import java.util.Random;
import java.util.Calendar;

/**
 * Métodos de apoio.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class Util {

    static Random rnd;

    /**
     * Gera um ponto cartesiano aleatório.
     *
     * @param maxX  Número máximo de colunas.
     * @param maxY  Número máximo de linhas.
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
     * Gera um valor inteiro aleatório.
     *
     * @param maximum   Valor máximo a ser gerado.
     * @return          Valor inteiro gerado.
     */
    public static int GetRandomNumber(int maximum)
    {
        return GetRandomObject().nextInt(maximum);
    }

    /**
     * Obtém o objeto <code>Random</code>.
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
