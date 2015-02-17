package game.scenes.drawers;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Objeto de desenho base, contendo caracter�sticas comuns a todos os
 * objetos de desenho.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public abstract class BaseDrawer {
	
	/**
	 * Painel no qual os desenhos ocorrem.
	 */
	protected JPanel panel;
	
	/**
	 * Cria um objeto desenhista.
	 * 
	 * @param panel Painel no qual os desenhos ocorrer�o.
	 */
	public BaseDrawer(JPanel panel)
	{
		this.panel = panel;
	}
	
    /**
     * Obt�m uma imagem a partir de seu nome.
     *
     * @param name Nome da imagem.
     */
    public Image getImage(String name)
    {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(name));
        return ii.getImage();
    }
}