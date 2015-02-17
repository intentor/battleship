package core.players;

import java.awt.Point;
import java.util.List;

import core.Ship;
import core.events.*;

/**
 * Representa um jogador.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface IPlayer {
	/**
     * Executa um turno do jogador.
     * 
     * @see Core.BoardPoint
     */
    void doTurn();
    
    /**
     * Define o ponto atacado em um turno.
     * 
     * @param p Ponto atacado.
     */
    void setAttackPoint(Point p);

    /**
     * Obt�m o n�mero de embarca��es abatidas pelo jogador.
     *
     * @return Valor inteiro representando o n�mero de embarca��es abatidas.
     */
    int getNumDestroyed();

    /**
     * Obt�m o n�mero de disparos realizados pelo jogador.
     *
     * @return Valor inteiro representando o n�mero de disparos.
     */
    int getNumShots();
    
    /**
     * Obt�m a lista de embarca��es do jogador.
     * 
     * @return Lista de embarca��es.
     */
    List<Ship> getShips();

    /**
     * Adiciona um listener ao evento de destrui��o de embarca��o.
     *
     * @param l Listener do evento.
     */
    void addShipDestroyedListener(PlayerListener l);

    /**
     * Remove um listener do evento de destrui��o de embarca��o.
     *
     * @param l Listener do evento.
     */
    void removeShipDestroyedListener(PlayerListener l);
}
