package game.scenes.listeners;

import game.scenes.events.GameOverSceneExitEvent;

/**
 * Listener dos eventos da cena de t�rmino de jogo.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface GameOverSceneListener {

    /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void gameOverSceneExitReceived(GameOverSceneExitEvent e);
}