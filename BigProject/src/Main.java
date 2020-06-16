import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("Welcome, the game will last about 5 minutes!");
        Game game = new Game();
        game.run();
        System.out.println("Game is end!");
    }
}
