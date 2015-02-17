package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de coloca��o de todas as embarca��es no tabuleiro.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ShipsDeployedEvent extends EventObject {

	public ShipsDeployedEvent(Object source) {
        super(source);
    }
}