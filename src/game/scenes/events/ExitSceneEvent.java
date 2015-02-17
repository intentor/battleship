package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de sa�da de uma cena.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ExitSceneEvent extends EventObject {
	
	public ExitSceneEvent(Object source) {
        super(source);
    }
}
