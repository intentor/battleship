package core;

/**
 * Representa um ponto no tabuleiro.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public class BoardPoint {

    private Ship ship;
    private boolean isAttacked;

    /**
     * Ponto vazio (sem embarca��o).
     */
    public BoardPoint() {
        this.ship = null;
    }

    /**
     * embarca��o presente no ponto.
     *
     * @return  Objeto Ship.
     * @see     Core.Ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Define uma embarca��o que passa pelo ponto.
     *
     * @param ship  embarca��o que passa pelo ponto.
     * @see         Core.Ship
     */
    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Indica se o ponto j� foi atacado.
     *
     * @return Valor booleano indicando se o ponto foi atacado.
     */
    public boolean isAttacked() {
        return this.isAttacked;
    }

    /**
     * Define o ponto como atacado.
     */
    public void setAttacked() {
        this.isAttacked = true;
        if (this.getShip() != null) this.getShip().attack();
    }

    /**
     * Indica se o ponto � uma embarca��o.
     *
     * @return Valor booleano indicando o tipo do ponto.
     */
    public boolean isShip() {
        return (this.getShip() != null);
    }

    /**
     * Indica se o ponto é uma embarca��o que j� foi atacada.
     *
     * @return Valor booleano indicando o status do ponto.
     */
    public boolean isShipAttacked()
    {
        return (this.isShip() && this.isAttacked());
    }

    /**
     * Indica se o ponto � uma embarca��o que j� foi atacada
     * e ainda n�o foi destru�da.
     *
     * @return Valor booleano indicando o status do ponto.
     */
    public boolean isShipAttackedNotDestroyed()
    {
        return (this.isShip() && this.isAttacked() && !this.isDestroyed());
    }

    /**
     * Indica se a embarca��o foi completamente destru�da.
     * Tal m�todo leva em considera��o todos os pontos os quais
     * forma a embarca��o atual.
     *
     * @return Valor booleano indicando o status do embarca��o.
     */
    public boolean isDestroyed() {
        Ship s = this.getShip();

        return (s != null && s.isDestroyed());
    }
}

