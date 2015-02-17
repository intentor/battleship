package core;

import java.awt.Point;

/**
 * Representa o último ponto atacado que possuia
 * uma embarcação ainda não destruída.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class PointInAxis extends Point {

    /**
     * Eixo no qual o último ataque ocorreu.
     * Somente é utilizado caso o último ponto represente um
     * ataque ocorrido após outro ataque.
     */
    public Axis axis;

    /**
     * Cria um último ponto atacado.
     * @param p Ponto no qual o último ataque ocorreu.
     */
    public PointInAxis(Point p)
    {
        super(p.x, p.y);
        this.axis = null;
    }

    /**
     * Cria um último ponto atacado.
     * @param p     Ponto no qual o último ataque ocorreu.
     * @param axis  Eixo no qual o último ataque ocorreu.
     */
    public PointInAxis(Point p
        , Axis axis)
    {
        super(p.x, p.y);
        this.axis = axis;
    }
}
