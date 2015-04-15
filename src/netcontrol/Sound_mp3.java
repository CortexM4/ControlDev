/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.player.Player;

/**
 *
 * @author Crtx
 */
public class Sound_mp3 {
    
    private static final Logger log = Logger.getLogger(Sound_mp3.class.getName());
    
    private String filename;
    private Player player;
    
    public Sound_mp3(String filename) throws UnsupportedAudioFileException{
        this.filename = filename;
        try {
            File file = new File(filename);
            AudioFileFormat baseFileFormat = null;
            AudioFormat baseFormat = null;
            baseFileFormat = AudioSystem.getAudioFileFormat(file);
            baseFormat = baseFileFormat.getFormat();
            Map properties = baseFileFormat.properties();
            long duration = (long) properties.get("duration");
            duration++;
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void close() { if(player != null) player.close(); }
    
    public void play()
    {
        try
        {
            InputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }
        
        new Thread()
        {
            public void run()
            {
                try { player.play(); }
                catch (Exception e) {log.log(Level.SEVERE, null, e);}
            }
        }.start();
    }
            
}
