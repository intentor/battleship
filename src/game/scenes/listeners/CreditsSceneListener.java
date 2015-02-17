package game.scenes.listeners;

import game.scenes.events.ExitSceneEvent;

/**
 * Listener dos eventos da cena de créditos.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface CreditsSceneListener {
	 /**
     * Listener do evento de destruição de embarcação.
     */
    void exitCreditsEventReceived(ExitSceneEvent e);

}
