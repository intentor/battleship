package core;

import java.awt.Point;

/**
 * Representa o �ltimo ponto atacado que possuia
 * uma embarca��o ainda n�o destru�da.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class PointInAxis extends Point {

    /**
     * Eixo no qual o �ltimo ataque ocorreu.
     * Somente � utilizado caso o �ltimo ponto represente um
     * ataque ocorrido ap�s outro ataque.
     */
    public Axis axis;

    /**
     * Cria um �ltimo ponto atacado.
     * @param p Ponto no qual o �ltimo ataque ocorreu.
     */
    public PointInAxis(Point p)
    {
        super(p.x, p.y);
        this.axis = null;
    }

    /**
     * Cria um �ltimo ponto atacado.
     * @param p     Ponto no qual o �ltimo ataque ocorreu.
     * @param axis  Eixo no qual o �ltimo ataque ocorreu.
     */
    public PointInAxis(Point p
        , Axis axis)
    {
        super(p.x, p.y);
        this.axis = axis;
    }
}
