package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de início de jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class StartGameEvent extends EventObject {
	
	public StartGameEvent(Object source) {
        super(source);
    }
}
