package game.scenes.events;

import java.util.EventObject;

/**
 * Representa evento de coloca��o de todas as embarca��es no tabuleiro.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class GameOverEvent extends EventObject {

	private boolean isPlayerWinner;
	
	public GameOverEvent(Object source, boolean isPlayerWinner) {
        super(source);
        
        this.isPlayerWinner = isPlayerWinner;
    }
	
	/**
	 * Indica se o jogador foi vencedor.
	 * @return Valor booleano indicando se o jogador foi vencedor.
	 */
	public boolean isPlayerWinner()
	{
		return this.isPlayerWinner;
	}
}