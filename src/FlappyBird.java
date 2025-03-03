import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener{
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird
    int BirdX = boardWidth/8;
    int BirdY = boardHeight/2;
    int BirdWidth = 34;
    int BirdHeight = 24;


    class Bird{
        int x = BirdX;
        int y = BirdY;
        int width = BirdWidth;
        int height = BirdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }
    }

//    GAME LOGIC
    Bird bird;
    int velocityY = -6;

//    TIMER LOOP
    Timer gameLoop;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
//        setBackground(Color.blue);

        // Load Images
        backgroundImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/flappybirdbg.png"))).getImage();
        birdImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/flappybird.png"))).getImage();
        topPipeImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/toppipe.png"))).getImage();
        bottomPipeImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("images/bottompipe.png"))).getImage();

//        Bird Object Initialization
        bird = new Bird(birdImg);

//        Game Timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // Background
        g.drawImage(backgroundImg, 0,0, boardWidth, boardHeight, null);

//        Drawing Bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move(){
        // Bird
        bird.y += velocityY;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

}
