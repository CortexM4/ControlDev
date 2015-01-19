/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.File;
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

    private boolean stat_load = false;                          // Успешно ли открыт поток
    private boolean stat_play = false;                          // Играет ли сейчас что-либо

    FloatControl volume;
    Clip clip;

    public Sound(File file) {

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);       // Тут может быть не файл, а поток. Это надо проработать.
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            stat_load = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            stat_load = false;
            log.log(Level.SEVERE, null, ex);
        }
    }

    public void setVolume(float vol) {
        float maxVol = volume.getMaximum();
        float minVol = volume.getMinimum();
        volume.setValue(minVol + (maxVol - minVol) * vol);
    }

    public float getVolume() {
        float v = volume.getValue();
        float min = volume.getMinimum();
        float max = volume.getMaximum();
        return (v - min) / (max - min);
    }

    /* reload указывает играть ли сначало */
    private void play(boolean reload) {
        if (stat_load) {
            if (reload) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                stat_play = true;
            }
            if (!stat_play) {
                clip.setFramePosition(0);
                clip.start();
                stat_play = true;
            }
        }
    }

    public void stop() {
        if (stat_play) {
            clip.stop();
        }
    }

    public void play() {
        play(true);
    }

    //Дожидается окончания проигрывания звука
    public void join() {
        if (!stat_load) {
            return;
        }
        synchronized (clip) {
            try {
                while (stat_play) {
                    clip.wait();
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    private class Listener implements LineListener {

        @Override
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                stat_play = false;
                synchronized (clip) {
                    clip.notify();
                }
            }
        }
    }
}
