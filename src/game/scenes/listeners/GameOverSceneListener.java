package game.scenes.listeners;

import game.scenes.events.GameOverSceneExitEvent;

/**
 * Listener dos eventos da cena de término de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface GameOverSceneListener {

    /**
     * Listener do evento de destruição de embarcação.
     */
    void gameOverSceneExitReceived(GameOverSceneExitEvent e);
}