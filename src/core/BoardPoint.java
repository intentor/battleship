package core;

/**
 * Representa um ponto no tabuleiro.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class BoardPoint {

    private Ship ship;
    private boolean isAttacked;

    /**
     * Ponto vazio (sem embarcação).
     */
    public BoardPoint() {
        this.ship = null;
    }

    /**
     * embarcação presente no ponto.
     *
     * @return  Objeto Ship.
     * @see     Core.Ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Define uma embarcação que passa pelo ponto.
     *
     * @param ship  embarcação que passa pelo ponto.
     * @see         Core.Ship
     */
    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    /**
     * Indica se o ponto já foi atacado.
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
     * Indica se o ponto é uma embarcação.
     *
     * @return Valor booleano indicando o tipo do ponto.
     */
    public boolean isShip() {
        return (this.getShip() != null);
    }

    /**
     * Indica se o ponto Ã© uma embarcação que já foi atacada.
     *
     * @return Valor booleano indicando o status do ponto.
     */
    public boolean isShipAttacked()
    {
        return (this.isShip() && this.isAttacked());
    }

    /**
     * Indica se o ponto é uma embarcação que já foi atacada
     * e ainda não foi destruída.
     *
     * @return Valor booleano indicando o status do ponto.
     */
    public boolean isShipAttackedNotDestroyed()
    {
        return (this.isShip() && this.isAttacked() && !this.isDestroyed());
    }

    /**
     * Indica se a embarcação foi completamente destruída.
     * Tal método leva em consideração todos os pontos os quais
     * forma a embarcação atual.
     *
     * @return Valor booleano indicando o status do embarcação.
     */
    public boolean isDestroyed() {
        Ship s = this.getShip();

        return (s != null && s.isDestroyed());
    }
}

