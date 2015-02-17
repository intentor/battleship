package game;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import java.io.InputStream;

/**
 * Player de áudio.
 * 
 * @author André Martins (amartins@ymail.com)
 */
public class AudioPlayer {
	
	/**
	 * Toca arquivos WAV.
	 * 
	 * @param fileName Nome do arquivo.
	 * @return Objeto Clip representando o arquivo sendo tocado.
	 */
	public Clip play(String fileName)
	{
		return this.play(fileName, false);
	}
	
	/**
	 * Toca arquivos WAV.
	 * 
	 * @param fileName 	Nome do arquivo.
	 * @param lopp		Indica se se deve tocar em loop infinito.
	 * @return Objeto Clip representando o arquivo sendo tocado.
	 */
	public Clip play(String fileName, boolean loop) {
		try 
		{
		    AudioInputStream stream =
		    	AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(fileName));
		 
		    DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
	        Clip clip = (Clip) AudioSystem.getLine(info);
		 
	        clip.open(stream);
	        
	        if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
	        else clip.start();	
	        
	        return clip;
		}
		catch (Exception e) 
		{
			return null;
		}
	}	
	
	/**
	 * Toca arquivos MIDI.
	 * 
	 * @param fileName 	Nome do arquivo.
	 * @return Objeto Sequencer representando o arquivo sendo tocado.
	 */
	public Sequencer playMIDI(String fileName)
	{
		return this.playMIDI(fileName, false);
	}
	
	/**
	 * Toca arquivos MIDI.
	 * 
	 * @param fileName 	Nome do arquivo.
	 * @param lopp		Indica se se deve tocar em loop infinito.
	 * @return Objeto Sequencer representando o arquivo sendo tocado.
	 */
	public Sequencer playMIDI(String fileName, boolean loop)
	{
		try 
		{
			InputStream stream = this.getClass().getResourceAsStream(fileName);
		 
		    Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(stream));
            sequencer.open();
            
	        if (loop) sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

        	sequencer.start();   
	        	        
        	return sequencer;
		}
		catch (Exception e) 
		{
			return null;
		}
	}
}