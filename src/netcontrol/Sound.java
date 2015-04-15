/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 *
 * @author Crtx
 */
public class Sound {

    private static final Logger log = Logger.getLogger(Sound.class.getName());

    private static boolean isMP3;                                     // Указывает какой формат играет (Ебануто в корне. Надо к интерфейсам привести)
    private static boolean isLoaded = false;                          // Успешно ли открыт поток
    private static boolean isPlaying = false;                         // Играет ли сейчас что-либо
    public static boolean IsPlaying() {
        return isPlaying;
    }
    private static Clip clip;
    private static PlayerEx player;
    
    private static long maxPosition;                                  // Устанавливается при проигрывании локальный файлов (сказок)
    public static long GetMaxPosition() {                             // и отсылается клиенту, чтобы была возможность изминять позицию
        return maxPosition;
    }                                                                 

    private static final int          ERROR_PLAY = -1;
    

    /*
    *   Ради инициализации FloatControl - это бредятина!!!!!
    */
   /* public static void Sound(String path, boolean reload) {    
        
        try {
            if (isPlaying && reload) {
                synchronized (clip) {
                    clip.notify();                      // Соответственно, целесообразность этого уведомдения тоже под вопросом
                    stop();
                }
            }
            if (!isPlaying) {
                File file = new File(path);
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(stream);
                clip.addLineListener(new Listener());
                gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                isLoaded = true;
                clip.setFramePosition(0);
                //maxPosition = clip.getFrameLength();
                maxPosition = clip.getMicrosecondLength();
                clip.start();
                isPlaying = true;
                log.info("Sound played");
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            maxPosition = ERROR_PLAY;                                       // Устанавливается в -1 при ошибке
            log.log(Level.SEVERE, null, ex);
        }
    }
*/
    /*
    *   Эта конструкция не работает с wav файлами, ну и ладно.
    */
    public static void playMP3file(String filename, boolean reload) {     
        try {
            File file = new File(filename);
            AudioFileFormat baseFileFormat = null;
            baseFileFormat = AudioSystem.getAudioFileFormat(file);
            Map properties = baseFileFormat.properties();
            maxPosition = (long) properties.get("duration");
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        
        try
        {
            InputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new PlayerEx(bis);
            
        }
        catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }
        
        // Необходимо узнать закончился-ли потоко для вызова stop!
        new Thread()
        {
            public void run()
            {
                try { player.play();
                
                }
                catch (Exception e) {log.log(Level.SEVERE, null, e);}
            }
        }.start();
        
        isPlaying = true;
        isMP3 = true;
    }
    
    /*
    *   Функция хоть и статичная, но только для mp3
    */
    public static void pause(boolean pause) {
        if(isMP3 && isPlaying)
            player.pause(pause);
    }
    
    public static void playStream(InputStream strm, boolean reload) {
        try {
            if (isPlaying && reload) {
                synchronized (clip) {
                    clip.notify();                      // Соответственно, целесообразность этого уведомдения тоже под вопросом
                    stop();
                }
            }
            if (!isPlaying) {
                BufferedInputStream bufferedIn = new BufferedInputStream(strm);
                AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);

                clip = AudioSystem.getClip();
                clip.open(stream);
                clip.addLineListener(new Listener());
                //gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                //gainControl.setValue(volume);
                isLoaded = true;
                clip.setFramePosition(0);
                clip.start();
                isPlaying = true;
                isMP3 = false;
                log.info("Sound played");
            }

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }

    public static long getPosition(){
        //return clip.getFramePosition();
        if(!isMP3)
            return clip.getMicrosecondPosition();
        else
            return player.getPosition() * 1000;
    }
    
    public static void setPosition(long position){
        //clip.setFramePosition(position);
        clip.setMicrosecondPosition(position);
    }
    
    public static void setVolume(float volume) {       // Про увеличение громкости 
        if (volume < 0.0) {
            volume = 0.0F;                 // http://www.java2s.com/Tutorial/Java/0120__Development/SettingtheVolumeofaSampledAudioPlayer.htm
        } else if (volume > 1.0) {
            volume = 1.0F;
        }
        
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) 
        {
            try 
            {
                Port outline = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
                outline.open();                
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);                
                volumeControl.setValue(volume);
            } 
            catch (LineUnavailableException ex) 
            {
                System.err.println("source not supported");
                ex.printStackTrace();
            }            
        }
    }

    public static float getVolume() {
        //float dB = gainControl.getValue();
        float volume=0;
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) 
        {
            try 
            {
                Port outline = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
                outline.open();                
                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);                
                volume = volumeControl.getValue();
            } 
            catch (LineUnavailableException ex) 
            {
                System.err.println("source not supported");
                ex.printStackTrace();
            }            
        }
        
        return volume;
    }

    public static void stop() {
        if (!isMP3) {
            if (isPlaying) {    clip.stop();    }
            clip.close();
        }
        else if (isMP3) {
            if(player != null) player.close();
        }
        isPlaying = false;
    }

    //Дожидается окончания проигрывания звука
    public void join() {                                // Пока еще не понятна применимоасть этого join'a
        if (!isLoaded) {                               // т.к. процесс запуска и останова возлагается 
            return;                                     // на сам класс Sound
        }
        synchronized (clip) {
            try {
                while (isPlaying) {
                    clip.wait();
                }
            } catch (InterruptedException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class Listener implements LineListener {     // Да вообще это какая-то хрень

        @Override
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                synchronized (clip) {
                    clip.notify();                      // Соответственно, целесообразность этого уведомдения тоже под вопросом
                    stop();
                }
            }
        }
    }
}
