import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
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
    int velocityY = 0;
    int gravity = 1;

//    TIMER LOOP
    Timer gameLoop;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

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
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
