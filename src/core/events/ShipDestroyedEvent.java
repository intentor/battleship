package core.events;

import core.Ship;
import java.util.EventObject;

/**
 * Representa evento de destrui��o de uma embarca��o.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class ShipDestroyedEvent extends EventObject {

	public ShipDestroyedEvent(Object source, Ship ship) {
        super(source);
        _ship = ship;
    }

    private Ship _ship;

    /**
     * Obt�m a embarca��o relacionada ao evento.
     *
     * @return  Objeto Ship.
     * @see     Core.Ship
     */
    public Ship getShip() {
        return _ship;
    }
}
