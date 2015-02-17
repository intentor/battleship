package game.scenes.listeners;

import game.scenes.events.ExitSceneEvent;
import game.scenes.events.StartGameEvent;

/**
 * Listener dos eventos da cena de menu.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface MenuSceneListener {
	 /**
     * Listener do evento de destruição de embarcação.
     */
    void startGameReceived(StartGameEvent e);
    
	 /**
     * Listener do evento de exibição de créditos.
     */
    void showCreditsReceived(ExitSceneEvent e);
}
