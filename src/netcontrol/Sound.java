/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Crtx
 */
public class Sound implements Runnable {

    private static final Logger log = Logger.getLogger(Sound.class.getName());

    private boolean isLoaded = false;                          // Успешно ли открыт поток
    private boolean isPlaying = false;                          // Играет ли сейчас что-либо

    private final Thread soundThread;
    static FloatControl volume;
    Clip clip;

    public Sound(File file) {

        soundThread = new Thread(this, "SoundThread");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);       // Тут может быть не файл, а поток. Это надо проработать.
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            isLoaded = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    public Sound(InputStream strm) {

        soundThread = new Thread(this, "SoundThread");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(strm);       // Тут может быть не файл, а поток. Это надо проработать.
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            isLoaded = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        soundThread.start(); 
        log.info("SoundThread started");
    }
    public void setVolume(float vol) {              // Про увеличение громкости 
        if(vol<0.0)                                 // http://www.java2s.com/Tutorial/Java/0120__Development/SettingtheVolumeofaSampledAudioPlayer.htm
            vol=(float) 0.0;
        else if(vol>1.0)
            vol=(float) 1.0;
        
        float maxVol = volume.getMaximum();
        float minVol = volume.getMinimum();
        volume.setValue(minVol + (maxVol - minVol) * vol);
    }

    public static float getVolume() {
        float v = volume.getValue();
        float min = volume.getMinimum();
        float max = volume.getMaximum();
        return (v - min) / (max - min);
    }

    /* reload указывает играть ли сначало */
    private void play(boolean reload) {
        if (isLoaded) {
            if (reload) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                isPlaying = true;
            }
            if (!isPlaying) {
                clip.setFramePosition(0);
                clip.start();
                isPlaying = true;
            }
        }
    }

    public void stop() {
        if (isPlaying) {
            clip.stop();
        }
        clip.close();
    }

    @Override
    public void run() {
       play(true); 
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

    private class Listener implements LineListener {

        @Override
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                isPlaying = false;
                synchronized (clip) {
                    clip.notify();                      // Соответственно, целесообразность этого уведомдения тоже под вопросом
                    stop();
                }
            }
        }
    }
}
