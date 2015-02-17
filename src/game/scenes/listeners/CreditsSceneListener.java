package game.scenes.listeners;

import game.scenes.events.ExitSceneEvent;

/**
 * Listener dos eventos da cena de cr�ditos.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface CreditsSceneListener {
	 /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void exitCreditsEventReceived(ExitSceneEvent e);

}
