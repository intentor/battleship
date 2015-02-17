package game.scenes.listeners;

import game.scenes.events.GameOverEvent;

/**
 * Listener dos eventos da cena de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface GameSceneListener {

    /**
     * Listener do evento de destruição de embarcação.
     */
    void gameOverReceived(GameOverEvent e);
}