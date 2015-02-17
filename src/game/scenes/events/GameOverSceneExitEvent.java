package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de colocação de todas as embarcações no tabuleiro.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class GameOverSceneExitEvent extends EventObject {

	public GameOverSceneExitEvent(Object source) {
        super(source);
    }
}