package music;

import javax.sound.sampled.*;
import java.net.URL;

public class MusicThread implements Runnable {
    private URL musicPath;
    private FloatControl gainControl;
    public MusicThread(URL musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public void run() {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicPath);
            clip.open(ais);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(6.02f);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
