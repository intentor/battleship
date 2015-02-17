package core.events;

import core.Ship;
import java.util.EventObject;

/**
 * Representa evento de destruição de uma embarcação.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ShipDestroyedEvent extends EventObject {

	public ShipDestroyedEvent(Object source, Ship ship) {
        super(source);
        _ship = ship;
    }

    private Ship _ship;

    /**
     * Obtém a embarcação relacionada ao evento.
     *
     * @return  Objeto Ship.
     * @see     Core.Ship
     */
    public Ship getShip() {
        return _ship;
    }
}
