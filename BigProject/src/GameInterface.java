import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface GameInterface {
    private void init_window() {

    }

    private void init_feather() {

    }

    public void run() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException;
}
