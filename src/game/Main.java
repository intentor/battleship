package game;

import javax.swing.JFrame;

/**
 * Inicialização do jogo.
 * 
 * @author André Martins (amartins@ymail.com)
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

    public Main() {
        add(new Board());
        setTitle("Batalha Naval");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(690, 430);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
    	//new TestBoard().Start();
        new Main();
    }

}
