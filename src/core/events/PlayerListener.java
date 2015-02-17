package core.events;

/**
 * Listener dos eventos de jogador.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public interface PlayerListener {

    /**
     * Listener do evento de destrui��o de embarca��o.
     */
    void shipDestroyedReceived(ShipDestroyedEvent e);
    
    /**
     * Listener do evento de ataque a um ponto do tabuleiro.
     */
    void boardPointHitReceived(BoardPointHitEvent e);
}
