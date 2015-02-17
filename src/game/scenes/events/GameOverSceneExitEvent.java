package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de coloca��o de todas as embarca��es no tabuleiro.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class GameOverSceneExitEvent extends EventObject {

	public GameOverSceneExitEvent(Object source) {
        super(source);
    }
}