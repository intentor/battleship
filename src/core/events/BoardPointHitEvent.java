package core.events;

import java.util.EventObject;

/**
 * Evento de ataque a um ponto no tabuleiro.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class BoardPointHitEvent extends EventObject {

	public BoardPointHitEvent(Object source, boolean isShip) {
        super(source);
        _isShip = isShip;
    }

    private boolean _isShip;

    /**
     * Indica se o ponto atingido era uma embarcação.
     *
     * @return Valor booleano indicando se o ponto era uma embarcação.
     */
    public boolean isShip() {
        return _isShip;
    }
}
