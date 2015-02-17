package core.players;

import java.awt.Point;
import java.util.List;

import core.Ship;
import core.events.*;

/**
 * Representa um jogador.
 * 
 * @author André Martins (amartins@ymail.com)
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
     * Obtém o número de embarcações abatidas pelo jogador.
     *
     * @return Valor inteiro representando o número de embarcações abatidas.
     */
    int getNumDestroyed();

    /**
     * Obtém o número de disparos realizados pelo jogador.
     *
     * @return Valor inteiro representando o número de disparos.
     */
    int getNumShots();
    
    /**
     * Obtém a lista de embarcações do jogador.
     * 
     * @return Lista de embarcações.
     */
    List<Ship> getShips();

    /**
     * Adiciona um listener ao evento de destruição de embarcação.
     *
     * @param l Listener do evento.
     */
    void addShipDestroyedListener(PlayerListener l);

    /**
     * Remove um listener do evento de destruição de embarcação.
     *
     * @param l Listener do evento.
     */
    void removeShipDestroyedListener(PlayerListener l);
}
