package no.uib.inf101.doubleDash.wave;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//this class has taken inspiration from
//https://stackoverflow.com/questions/10645594/what-audio-format-should-i-use-for-java
public class PlayWave {

    private Clip clip;

    /**
     * Call to create a MakeSound object that loads in an audio file from a given
     * filepath. The class is able to play the audio file
     * 
     * @param filename name the sound that should be made
     */
    public PlayWave(String filename) {
        if (!filename.startsWith("/wave/")) {
            filename = "/wave/" + filename;
        }
        InputStream stream = new BufferedInputStream(PlayWave.class.getResourceAsStream(filename));
        AudioInputStream audioInputStream;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(stream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Call to play the sound once. If the sound is already playing it will be
     * restarted
     * 
     * @param shouldLoop true if the sound should continously loop, false otherwise
     */
    public void play(boolean shouldLoop) {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
        if (shouldLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Call to play the sound once. If the sound is already playing it will be
     * restarted
     * 
     * @param shouldLoop true if the sound should continously loop, false otherwise
     * @param gain       the volume the sound should be played at
     */
    public void play(boolean shouldLoop, int gain) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-gain);
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
        if (shouldLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

}
