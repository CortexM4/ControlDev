/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netcontrol;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Crtx
 */
public class Sound {

    private boolean stat_load = false;

    public Sound(File file) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);       // Тут может быть не файл, а поток. Это надо проработать.
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            clip.setFramePosition(0);
            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.stop(); //Останавливаем
            clip.close(); //Закрываем
            
            stat_load = true;

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException ex) {
            stat_load = false;
            System.out.println(ex.getMessage());
        }
    }

}
