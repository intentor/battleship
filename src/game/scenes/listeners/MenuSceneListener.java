package game.scenes.listeners;

import game.scenes.events.ExitSceneEvent;
import game.scenes.events.StartGameEvent;

/**
 * Listener dos eventos da cena de menu.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface MenuSceneListener {
	 /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void startGameReceived(StartGameEvent e);
    
	 /**
     * Listener do evento de exibi��o de cr�ditos.
     */
    void showCreditsReceived(ExitSceneEvent e);
}
