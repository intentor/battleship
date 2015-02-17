package game.scenes;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.Clip;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import game.AudioPlayer;

/**
 * Cena base, contendo caracter�sticas comuns a todas as cenas.
 * 
 * @author Andr� Martins (amartins@ymail.com)
 */
public abstract class BaseScene<BaseDrawer, T> {

	/**
	 * Painel no qual a cena � desenhada.
	 */
	protected JPanel panel;
	
	/**
	 * M�sica de background.
	 */
    protected Sequencer bgMusic;
    
    /**
     * Desenhista do cena.
     */
    protected BaseDrawer drawer;
    
    /**
     * Listeners de eventos.
     */
    protected List<T> listeners = new LinkedList<T>();
	
	/**
	 * Tocador de �udio.
	 */
	private AudioPlayer audioPlayer;
    
    /**
     * Cria uma cena.
     * 
     * @param panel 	Painel no qual a cena � desenhada.
     */
    public BaseScene(JPanel panel)
    {
    	this.panel = panel;
        this.audioPlayer = new AudioPlayer();  
    }
	
    /**
     * Adiciona listener de eventos.
     *
     * @param l Listener do evento.
     */
    public void addListener(T l)
    {
        this.listeners.add(l);
    }
    
    /**
     * Remove listener de eventos.
     *
     * @param l Listener do evento.
     */
    public void removeListener(T l)
    {
        this.listeners.remove(l);
    }
    
    /**
     * Toca um arquivo de �udio.
     * 
     * @param file	Arquivo a ser tocado.
     * @return Objeto Clip representando o arquivo sendo tocado.
     */
    protected Clip play(String file)
    {
    	return this.audioPlayer.play(file);
    }
    
    /**
	 * Toca arquivos MIDI.
	 * 
	 * @param fileName 	Nome do arquivo.
	 * @return Objeto Sequencer representando o arquivo sendo tocado.
	 */
	protected Sequencer playMIDI(String fileName)
    {
		return this.audioPlayer.playMIDI(fileName);
    }
    
    /**
     * Inicia m�sica de fundo.
     * 
     * @param file	Arquivo a ser tocado.
     */
    protected void setBackgroundMusic(String file)
    {
    	this.bgMusic = this.audioPlayer.playMIDI(file, true);
    }
    
    /**
     * Exibe caixa de mensagem.
     * 
     * @param message Mensagem a ser exibida.
     */
    protected void showDialog(String message)
    {
    	JOptionPane.showMessageDialog(this.panel, message);
    }
    
    /**
     * Realiza desenho da interface.
     * 
     * @param g Objeto Graphics2D no qual o desenho ocorrer�.
     */
    public abstract void Draw(Graphics2D g);
    
    /**
     * Descarta o objeto.
     */
    public void Dispose()
    {
    	if (this.bgMusic != null) this.bgMusic.stop();
    }
}
