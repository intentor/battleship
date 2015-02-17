package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de in�cio de jogo.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class StartGameEvent extends EventObject {
	
	public StartGameEvent(Object source) {
        super(source);
    }
}
