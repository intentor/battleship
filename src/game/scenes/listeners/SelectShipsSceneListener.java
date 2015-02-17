package game.scenes.listeners;

import game.scenes.events.ShipsDeployedEvent;

/**
 * Listener dos eventos da cena de seleção de tabuleiros.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface SelectShipsSceneListener {

    /**
     * Listener do evento de destruição de embarcação.
     */
    void shipsDeployedReceived(ShipsDeployedEvent e);
}