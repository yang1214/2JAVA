import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.applet.Applet;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.swing.JFrame;
import javax.sound.sampled.*;
import java.io.File;

public class Game extends JFrame implements GameInterface{
    String DuckUrl = ".\\Picture\\duck.jpg";
    String BackGroundUrl = ".\\Picture\\bg.jpg";
    String FoodUrl = ".\\Picture\\food.jpg";
    String White = ".\\Picture\\white.jpg";
    String BigDuckURL = ".\\Picture\\bigduck.jpg";
    Vector<Duck> ducks = new Vector<>();
    Vector<Food> foods = new Vector<>();
    Queue<JLabel> now = new LinkedList<JLabel>();
    Queue<JLabel> next = new LinkedList<JLabel>();
    Duck bigduck;
    private void init_window(){
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("2JAVA-Project by:288135");
        this.setVisible(true);
    }
    private void init_feather(){
        Random r = new Random();
        for(int i = 0; i < 4; ++i){
            ducks.add(new Duck(Math.abs(r.nextInt()) % 740, Math.abs(r.nextInt()) % 740));
            ducks.elementAt(i).changeDir();
        }
        for(int i = 0; i < 20; ++i){
            foods.add(new Food(Math.abs(r.nextInt()) % 740, Math.abs(r.nextInt()) % 740));
        }
    }
    public void run() throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        init_window();
        init_feather();
        int Time = 0;
        while(true){
            // begin
            Thread.sleep(20);
            Time = Time + 1;
            Time = Time % 1000000000;
            // food is eaten
            for(int i = 0; i < ducks.size(); ++i){
                for(int j = 0; j < foods.size(); ++j){
                    if((ducks.elementAt(i).getX() - foods.elementAt(j).x < 30 &&
                            ducks.elementAt(i).getX() - foods.elementAt(j).x > -30 * ducks.elementAt(i).getSize()) &&
                            (ducks.elementAt(i).getY() - foods.elementAt(j).y < 30 &&
                            ducks.elementAt(i).getY() - foods.elementAt(j).y > -30 * ducks.elementAt(i).getSize())){
                        foods.remove(j);
                        j--;
                        ducks.elementAt(i).eat();
                    }
                }
            }
            // turn when meet
            for(int i = 0; i < ducks.size(); ++i){
                for(int j = 0; j < ducks.size(); ++j){
                    if(i == j){
                        continue;
                    }
                    if((ducks.elementAt(i).getX() - ducks.elementAt(j).getX() < 30 * ducks.elementAt(j).getSize() &&
                            ducks.elementAt(i).getX() - ducks.elementAt(j).getX() > -30 * ducks.elementAt(i).getSize()) &&
                            (ducks.elementAt(i).getY() - ducks.elementAt(j).getY() < 30 * ducks.elementAt(j).getSize() &&
                                    ducks.elementAt(i).getY() - ducks.elementAt(j).getY() > -30 * ducks.elementAt(i).getSize())){
                        ducks.elementAt(i).changeDir();
                        ducks.elementAt(j).changeDir();
                        ducks.elementAt(i).setDx(ducks.elementAt(i).getDx() *
                                ((Math.abs(ducks.elementAt(i).getX() - ducks.elementAt(j).getX())) / ((ducks.elementAt(i).getX() - ducks.elementAt(j).getX()))));
                        ducks.elementAt(i).setDy(ducks.elementAt(i).getDy() *
                                ((Math.abs(ducks.elementAt(i).getY() - ducks.elementAt(j).getY())) / ((ducks.elementAt(i).getY() - ducks.elementAt(j).getY()))));
                        ducks.elementAt(j).setDx(ducks.elementAt(j).getDx() *
                                ((Math.abs(ducks.elementAt(j).getX() - ducks.elementAt(i).getX())) / ((ducks.elementAt(j).getX() - ducks.elementAt(i).getX()))));
                        ducks.elementAt(j).setDy(ducks.elementAt(j).getDy() *
                                ((Math.abs(ducks.elementAt(j).getY() - ducks.elementAt(i).getY())) / ((ducks.elementAt(j).getY() - ducks.elementAt(i).getY()))));
                        ducks.elementAt(i).move();
                        ducks.elementAt(j).move();
                    }
                }
            }
            // don't move out
            for(int i = 0; i < ducks.size(); ++i){
                if(ducks.elementAt(i).getX() <= 0 ||
                        ducks.elementAt(i).getX() >= 785 - ducks.elementAt(i).getSize() * 30.0){
                    ducks.elementAt(i).changeDir();
                    if(ducks.elementAt(i).getX() <= 0){
                        ducks.elementAt(i).setDx(Math.abs(ducks.elementAt(i).getDx()));
                    }
                    if(ducks.elementAt(i).getX() >= 785 - ducks.elementAt(i).getSize() * 30.0){
                        ducks.elementAt(i).setDx(-Math.abs(ducks.elementAt(i).getDx()));
                    }
                    ducks.elementAt(i).move();
                }
                if(ducks.elementAt(i).getY() <= 0 ||
                        ducks.elementAt(i).getY() >= 770 - ducks.elementAt(i).getSize() * 30.0){
                    ducks.elementAt(i).changeDir();
                    if(ducks.elementAt(i).getY() <= 0){
                        ducks.elementAt(i).setDy(Math.abs(ducks.elementAt(i).getDy()));
                    }
                    if(ducks.elementAt(i).getY() >= 770 - ducks.elementAt(i).getSize() * 30.0){
                        ducks.elementAt(i).setDy(-Math.abs(ducks.elementAt(i).getDy()));
                    }
                    ducks.elementAt(i).move();
                }
            }
            // check who is dead
            for(int i = 0; i < ducks.size(); ++i){
                if(ducks.elementAt(i).isDead()){
                    if(ducks.size() == 1){
                        return;
                    }
                    ducks.remove(i);
                    i--;
                }
            }
            // let every ducks move
            for(int i = 0; i < ducks.size(); ++i){
                ducks.elementAt(i).move();
            }
            while(now.size() > 0){
                this.remove(now.poll());
            }
            // show ducks
            for(int i = 0; i < ducks.size(); ++i){
                JLabel _jLabel = new JLabel(new ImageIcon(DuckUrl));
                this.getContentPane().setLayout(null);
                _jLabel.setBounds((int)ducks.elementAt(i).getX(),
                        (int)ducks.elementAt(i).getY(),
                        (int)(30 * ducks.elementAt(i).getSize()),
                        (int)(30 * ducks.elementAt(i).getSize()));
                this.getContentPane().add(_jLabel);
                now.offer(_jLabel);
            }
            if(bigduck != null){
                for(int j = 0; j < foods.size(); ++j){
                    if((bigduck.getX() - foods.elementAt(j).x < 30 &&
                            bigduck.getX() - foods.elementAt(j).x > -30 * bigduck.getSize()) &&
                            (bigduck.getY() - foods.elementAt(j).y < 30 &&
                                    bigduck.getY() - foods.elementAt(j).y > -30 * bigduck.getSize())){
                        foods.remove(j);
                        j--;
                        bigduck.eat();
                    }
                }
                if(bigduck.getX() <= 0 ||
                        bigduck.getX() >= 785 - bigduck.getSize() * 30.0){
                    bigduck.changeDir();
                    if(bigduck.getX() <= 0){
                        bigduck.setDx(Math.abs(bigduck.getDx()));
                    }
                    if(bigduck.getX() >= 785 - bigduck.getSize() * 30.0){
                        bigduck.setDx(-Math.abs(bigduck.getDx()));
                    }
                    bigduck.move();
                }
                if(bigduck.getY() <= 0 ||
                        bigduck.getY() >= 770 - bigduck.getSize() * 30.0){
                    bigduck.changeDir();
                    if(bigduck.getY() <= 0){
                        bigduck.setDy(Math.abs(bigduck.getDy()));
                    }
                    if(bigduck.getY() >= 770 - bigduck.getSize() * 30.0){
                        bigduck.setDy(-Math.abs(bigduck.getDy()));
                    }
                    bigduck.move();
                }
                for(int j = 0; j < ducks.size(); ++j){
                    if((bigduck.getX() - ducks.elementAt(j).getX() < 30 * ducks.elementAt(j).getSize() &&
                            bigduck.getX() - ducks.elementAt(j).getX() > -30 * bigduck.getSize()) &&
                            (bigduck.getY() - ducks.elementAt(j).getY() < 30 * ducks.elementAt(j).getSize() &&
                                    bigduck.getY() - ducks.elementAt(j).getY() > -30 * bigduck.getSize())){
                        bigduck.changeDir();
                        ducks.elementAt(j).changeDir();
                        ducks.elementAt(j).move();
                        bigduck.move();
                    }
                }
                for(int i = 0; i < ducks.size(); ++i){
                    ducks.elementAt(i).chase(bigduck);
                }
                bigduck.move();
                if(bigduck.isDead()){
                    bigduck = null;
                }
                JLabel _jLabel = new JLabel(new ImageIcon(BigDuckURL));
                this.getContentPane().setLayout(null);
                _jLabel.setBounds((int)bigduck.getX(),
                        (int)bigduck.getY(),
                        50,
                        50);
                this.getContentPane().add(_jLabel);
                now.offer(_jLabel);
            }
            // show foods
            for(int i = 0; i < foods.size(); ++i){
                JLabel _jLabel = new JLabel(new ImageIcon(FoodUrl));
                this.getContentPane().setLayout(null);
                _jLabel.setBounds((int)foods.elementAt(i).x,
                        (int)foods.elementAt(i).y,
                        30,
                        30);
                this.getContentPane().add(_jLabel);
                now.offer(_jLabel);
            }
            this.repaint();
            // new duck
            if(Time % 500 == 0){
                Random r = new Random();
                ducks.add(new Duck(r.nextInt() % 755, r.nextInt() % 740));
            }
            // new food
            if(Time % 100 == 0){
                Random r = new Random();
                foods.add(new Food(r.nextInt() % 755, r.nextInt() % 740));
            }
            // set big duck
            if(Time == 300 && !ducks.isEmpty()){
                File wavFile = new File(".\\Music\\sound.wav");
                AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                bigduck = ducks.elementAt(0);
                ducks.remove(0);
                bigduck.setSize(50.0 / 30.0);
                bigduck.changeDir();
                for(int i = 0; i < ducks.size(); ++i){
                    ducks.elementAt(i).chase(bigduck);
                }
            }
            //System.out.println(ducks.size());
            // when 10 minutes up or no ducks, ends
            if(Time == 12000 || ducks.isEmpty()){
                return;
            }
        }
    }
}
