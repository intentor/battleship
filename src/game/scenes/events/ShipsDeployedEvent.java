package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de colocação de todas as embarcações no tabuleiro.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ShipsDeployedEvent extends EventObject {

	public ShipsDeployedEvent(Object source) {
        super(source);
    }
}