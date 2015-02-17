package core.events;

/**
 * Listener dos eventos de jogador.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public interface PlayerListener {

    /**
     * Listener do evento de destruição de embarcação.
     */
    void shipDestroyedReceived(ShipDestroyedEvent e);
    
    /**
     * Listener do evento de ataque a um ponto do tabuleiro.
     */
    void boardPointHitReceived(BoardPointHitEvent e);
}
