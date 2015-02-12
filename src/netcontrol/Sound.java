/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.BufferedInputStream;
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
    static FloatControl gainControl;
    static Clip clip;

    public static void Sound(String path){
        File file = new File(path);
        Sound snd = new Sound(file);
        snd.play();
    }
    public Sound(File file) {

        soundThread = new Thread(this, "SoundThread");
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);      
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            isLoaded = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }
    
    public Sound(InputStream strm) {

        soundThread = new Thread(this, "SoundThread");
        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(strm); 
            AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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
    public static void setVolume(float gain) {       // Про увеличение громкости 
        if(gain<0.0)                                 // http://www.java2s.com/Tutorial/Java/0120__Development/SettingtheVolumeofaSampledAudioPlayer.htm
            gain=0.0F;
        else if(gain>1.0)
            gain=1.0F;
        
        float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
   //     float maxVol = gainControl.getMaximum();
   //     float minVol = gainControl.getMinimum();
   //     gainControl.setValue(minVol + (maxVol - minVol) * gain);
    }

    public static float getVolume() {
        float dB = gainControl.getValue();
        float gain = (float) (Math.exp(dB / 20.0 * Math.log(10.0)));
//       float gain = (float) (dB * 20.0 * Math.log(10.0));
 //       float min = gainControl.getMinimum();
 //       float max = gainControl.getMaximum();
 //       return (v - min) / (max - min);
        return gain;
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
