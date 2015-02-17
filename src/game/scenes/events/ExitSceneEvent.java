package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de saída de uma cena.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ExitSceneEvent extends EventObject {
	
	public ExitSceneEvent(Object source) {
        super(source);
    }
}
