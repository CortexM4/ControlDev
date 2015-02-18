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
public class Sound {

    private static final Logger log = Logger.getLogger(Sound.class.getName());

    private boolean isLoaded = false;                          // Успешно ли открыт поток
    private boolean isPlaying = false;                          // Играет ли сейчас что-либо

    private static FloatControl gainControl;
    private static float volume;                                     // Хранение значения усиления (Как-то криво)
    private Clip clip;

    public static void InitGain(float gain){
        if(gain > 1.0F) gain = 1.0F;
        else if(gain < 0.0F) gain = 0.0F;
        volume = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
    }
    
    public static void Sound(String path) {
        File file = new File(path);
        Sound snd = new Sound(file);
        snd.play();
    }
    
    /*  Такие конструкторы это вообще-то бред, надо потом исправить  */
    public Sound(File file) {
        
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            isLoaded = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }

    public Sound(InputStream strm) {

        try {
            BufferedInputStream bufferedIn = new BufferedInputStream(strm);
            AudioInputStream stream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            isLoaded = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            isLoaded = false;
            log.log(Level.SEVERE, null, ex);
        }
    }

    public void play() {
        play(true);
        log.info("Sound played");
    }

    public static void setVolume(float gain) {       // Про увеличение громкости 
        if (gain < 0.0) gain = 0.0F;                 // http://www.java2s.com/Tutorial/Java/0120__Development/SettingtheVolumeofaSampledAudioPlayer.htm
        else if (gain > 1.0) gain = 1.0F;

        volume = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(volume);
    }

    public static float getVolume() {
        float dB = gainControl.getValue();
        float gain = (float) (Math.exp(dB / 20.0 * Math.log(10.0)));
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
