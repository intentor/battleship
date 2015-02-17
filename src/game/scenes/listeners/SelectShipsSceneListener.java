package game.scenes.listeners;

import game.scenes.events.ShipsDeployedEvent;

/**
 * Listener dos eventos da cena de sele��o de tabuleiros.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface SelectShipsSceneListener {

    /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void shipsDeployedReceived(ShipsDeployedEvent e);
}