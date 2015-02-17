package game.scenes.listeners;

import game.scenes.events.GameOverEvent;

/**
 * Listener dos eventos da cena de jogo.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface GameSceneListener {

    /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void gameOverReceived(GameOverEvent e);
}